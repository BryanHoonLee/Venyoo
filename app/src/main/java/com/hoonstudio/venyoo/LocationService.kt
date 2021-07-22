package com.hoonstudio.venyoo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationService @Inject constructor(
    private val activity: AppCompatActivity,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val client: SettingsClient,
    private val locationRequest: LocationRequest,
    private val builder: LocationSettingsRequest.Builder
) {

    private lateinit var task: Task<LocationSettingsResponse>

    companion object {
        const val REQUEST_CHECK_SETTINGS = 1
    }

    sealed class Result {
        data class Success(val latitude: Double, val longitude: Double) : Result()
        object Failure : Result()
    }

    suspend fun getLocation(): Result {
        return suspendCoroutine { cont ->
            if (checkLocationPermission()) {
                fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
                    if (lastLocation != null) {
                        cont.resume(Result.Success(lastLocation.latitude, lastLocation.longitude))
                    } else {
                        val locationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                fusedLocationClient.removeLocationUpdates(this)
                                if (locationResult.lastLocation != null) {
                                    cont.resume(
                                        Result.Success(
                                            locationResult.lastLocation.latitude,
                                            locationResult.lastLocation.longitude
                                        )
                                    )
                                } else {
                                    cont.resume(Result.Failure)
                                }
                            }
                        }
                        if (checkLocationPermission()) {
                            fusedLocationClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper()
                            )
                        }
                    }
                }
            }
        }
    }

fun checkLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) + ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * https://developer.android.com/training/location/change-location-settings
 */
fun checkLocationSettings(): Task<LocationSettingsResponse> {
    return client.checkLocationSettings(builder.build())
}
}