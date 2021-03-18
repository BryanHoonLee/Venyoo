package com.example.venyoo.dependencyinjection.activity

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
        val activity: AppCompatActivity
) {

    @Provides
    fun activity() = activity

    @Provides
    fun locationRequest(): LocationRequest = LocationRequest.create().apply {
        interval = 100000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @Provides
    fun locationRequestBuilder(locationRequest: LocationRequest): LocationSettingsRequest.Builder =
            LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)

    @Provides
    fun settingsClient(activity: AppCompatActivity): SettingsClient = LocationServices.getSettingsClient(activity)

//    @Provides
//    fun locationSettingResponseTask(settingsClient: SettingsClient, builder: LocationSettingsRequest.Builder): Task<LocationSettingsResponse> =
//            settingsClient.checkLocationSettings(builder?.build())

    @Provides
    fun fusedLocationClient(activity: AppCompatActivity): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
}