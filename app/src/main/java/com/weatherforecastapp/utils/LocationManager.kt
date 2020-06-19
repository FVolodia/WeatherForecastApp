package com.weatherforecastapp.utils

import android.annotation.SuppressLint
import android.content.IntentSender
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationManager(
    private val fragment: Fragment,
    locationChangeListener: (LocationData) -> Unit
) :
    LifecycleObserver {

    companion object {
        const val REQUEST_CHECK_SETTINGS = 1002
        private const val INTERVAL = 2000L
        private const val FAST_INTERVAL = 1000L
        private const val NUM_UPDATES = 1
    }

    init {
        fragment.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    private val context
        get() = fragment.requireContext()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult?) {
            val lastLocation = location?.lastLocation
            lastLocation?.let {
                locationChangeListener.invoke(
                    LocationData(
                        it.latitude,
                        it.longitude
                    )
                )
            }
        }
    }

    fun receiveLocation() = getLastKnownLocation()

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            //            locationChangeListener.invoke(location)
        }

        val builder = LocationSettingsRequest.Builder()

        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = INTERVAL
            fastestInterval = FAST_INTERVAL
            numUpdates = NUM_UPDATES
        }

        builder.addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    fragment.startIntentSenderForResult(
                        exception.resolution.intentSender,
                        REQUEST_CHECK_SETTINGS,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    data class LocationData(val latitude: Double, val longitude: Double)
}
