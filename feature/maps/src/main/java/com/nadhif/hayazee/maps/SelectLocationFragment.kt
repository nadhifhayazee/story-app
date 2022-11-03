package com.nadhif.hayazee.maps

import android.location.Location
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.Const
import com.nadhif.hayazee.common.extension.setBackStackData
import com.nadhif.hayazee.common.util.LocationUtil
import com.nadhif.hayazee.maps.databinding.FragmentSelectLocationBinding

class SelectLocationFragment :
    BaseFragment<FragmentSelectLocationBinding>(FragmentSelectLocationBinding::inflate) {

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private var latLng: LatLng? = null
    private var latitude: String? = null
    private var longitude: String? = null

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap


        mMap?.setOnCameraIdleListener {
            val center = mMap?.cameraPosition?.target
            if (center != null) {
                val marker = MarkerOptions()
                marker.position(center)
                if (mMap != null) {
                    currentMarker = mMap?.addMarker(marker)
                    currentMarker?.remove()
                    latLng = currentMarker?.position
                    setPointedLocationView(
                        latitude = latLng?.latitude ?: 0.0,
                        longitude = latLng?.longitude ?: 0.0
                    )
                }
            }
        }

        if (latitude == null || latitude == "null") {
            getLastLocation()
        } else {
            mMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        latitude?.toDouble() ?: 0.0,
                        longitude?.toDouble() ?: 0.0
                    ), 18f
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getString("latitude")
            longitude = it.getString("longitude")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setViewListener()
    }

    private fun setViewListener() {
        binding.apply {
            btnSelectLocation.setOnClickListener {
                latLng?.let {
                    setBackStackData(Const.SELECTED_LOCATION, it)
                }
            }
        }
    }

    private fun getLastLocation() {
        activity?.let { actvty ->
            LocationUtil.getLastLocation(actvty, object : LocationUtil.LastLocationCallback {
                override fun onGetLastLocationSuccess(location: Location) {
                    setPointedLocationView(location.latitude, location.longitude)
                    mMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ), 18f
                        )
                    )
                }

                override fun onGetLastLocationFailed() {
                }

                override fun onPermissionNotGranted() {
                }
            })
        }
    }

    private fun setPointedLocationView(latitude: Double, longitude: Double) {
        binding.apply {
            tvAddressMaps.text =
                LocationUtil.getFullAddressName(latitude, longitude, requireContext())
        }
    }
}