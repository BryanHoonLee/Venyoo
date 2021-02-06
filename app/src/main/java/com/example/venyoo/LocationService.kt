package com.example.venyoo

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import javax.inject.Inject

class LocationService @Inject constructor(private val activity: AppCompatActivity): Service() {

    sealed class Result{
        data class Success(val latitude: Float, val longitude: Float): Result()
        object Failure: Result()
    }

    fun getLocation(): Result{
        if(checkLocationPermission()){
            return getLatitudeLongitude()
        } else{
            return Result.Failure
        }
    }

    private fun getLatitudeLongitude(): Result{
        Log.d("TEST", "WORKS")
        return Result.Success(1.011f, 2.022f)
    }

    private fun checkLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}