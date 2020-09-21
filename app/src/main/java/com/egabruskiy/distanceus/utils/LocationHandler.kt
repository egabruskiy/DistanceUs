package com.egabruskiy.distanceus.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import javax.inject.Inject

class LocationHandler : LifecycleObserver {

    private val TAG = javaClass.simpleName

    @Inject
   lateinit var mFusedClient: FusedLocationProviderClient

    private var locationRequest: LocationRequest? = null

    private  var mLocationCallback: LocationCallback? = null

     var mContext:Context

    constructor (
        lifecycleOwner: LifecycleOwner,
        callback: LocationCallback ,
    context:Context
    ) {
        mContext = context
        lifecycleOwner.lifecycle.addObserver(this)
        mLocationCallback = callback
        mFusedClient= LocationServices.getFusedLocationProviderClient(context)
    }



    private fun getLocationRequest(): LocationRequest? {
        try {
             locationRequest = LocationRequest.create()?.apply {
                interval = 10000
                fastestInterval = 7000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return locationRequest
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    @SuppressLint("MissingPermission")
    fun requestLocation() {
        try {
            if (mFusedClient != null) {
                getLocationRequest()

                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                mFusedClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopLocationUpdates() {
        try {
            if (mFusedClient != null) {
                mFusedClient.removeLocationUpdates(mLocationCallback)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
