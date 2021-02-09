package com.example.venyoo

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationService @Inject constructor(private val activity: AppCompatActivity, private val fusedLocationClient: FusedLocationProviderClient, private val locationRequest: LocationRequest, private val task: Task<LocationSettingsResponse>) {

    companion object {
        const val REQUEST_CHECK_SETTINGS = 1
    }

    sealed class Result {
        data class Success(val latitude: Double, val longitude: Double) : Result()
        object Failure : Result()
    }

    suspend fun getLatitudeLongitude(): Result {
        return suspendCoroutine { cont ->
            if (checkLocationPermission()) {
                fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                val lastLocation = locationResult.lastLocation
                                cont.resume(Result.Success(lastLocation.latitude, lastLocation.longitude))
                            }
                        },
                        Looper.myLooper())
            }

        }
    }

    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * https://developer.android.com/training/location/change-location-settings
     */
    suspend fun checkLocationSettings(): Boolean {
        return suspendCoroutine { cont ->
            task.addOnSuccessListener { locationSettingsResponse ->
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                cont.resume(true)
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.

                    }
                }
            }
        }
    }
}