package com.nadhif.hayazee.common.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.nadhif.hayazee.common.R
import java.util.*


object LocationUtil {

    fun getFullAddressName(lat: Double, lon: Double, context: Context): String? {
        var addressName: String? = null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                addressName = list[0].getAddressLine(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return addressName
    }

    fun getDistrictSubDistrictName(lat: Double, lon: Double, context: Context): String? {
        var addressName: String? = null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                addressName = list[0].locality + ", " + list[0].adminArea
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return addressName
    }

    fun getLastLocation(activity: Activity, callback: LastLocationCallback) {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, activity) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, activity)
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                if (location != null) {
                    callback.onGetLastLocationSuccess(location)
                } else {
                    callback.onGetLastLocationFailed()
                }


            }

        } else {
            callback.onPermissionNotGranted()
        }
    }

    private fun checkPermission(permission: String, activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestEnablingLocationService(activity: Activity?) {
        activity?.let {

            val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            var gpsEnabled = false
            try {
                gpsEnabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            if (!gpsEnabled) {
                AlertDialog.Builder(activity)
                    .setMessage(activity.resources.getString(R.string.enabling_gps))
                    .setPositiveButton(activity.resources.getString(R.string.open_setting),
                        DialogInterface.OnClickListener { _, _ ->
                            activity.startActivity(
                                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            )
                        })
                    .setNegativeButton(activity.resources.getString(R.string.cancel), null)
                    .show()
            }
        }
    }

    interface LastLocationCallback {
        fun onGetLastLocationSuccess(location: Location)
        fun onGetLastLocationFailed()
        fun onPermissionNotGranted()
    }
}