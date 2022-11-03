package com.nadhif.hayazee.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.util.DateUtil
import com.nadhif.hayazee.maps.databinding.FragmentStoryMapsBinding
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class StoryMapsFragment :
    BaseFragment<FragmentStoryMapsBinding>(FragmentStoryMapsBinding::inflate),
    OnMarkerClickListener {

    private var mMap: GoogleMap? = null

    @Inject
    lateinit var vmFactory: StoryMapsViewModel.Factory

    private val mapsStoryMapsViewModel by viewModels<StoryMapsViewModel> { vmFactory }

    private var storyList: List<Story>? = null

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        getMyLocation()
        mapsStoryMapsViewModel.getStories()
        setMapsStyle()

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setMapsStyle() {
        try {

            val success =
                mMap?.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireActivity(),
                        if (DateUtil.checkIsNight()) R.raw.map_style_night else R.raw.map_style_day
                    )
                )
            if (success == false) {
                Log.e("maps style", "Style parsing failed.")
            }
        } catch (exception: Exception) {
            Log.e("maps style", "Can't find style. Error: ", exception)
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mapsStoryMapsViewModel.state.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {

                    }

                    is ResponseState.Success -> {
                        storyList = it.data
                        generateMarker(it.data)
                    }

                    is ResponseState.Error -> {

                    }
                }
            }
        }
    }

    private fun generateMarker(data: List<Story>?) {
        if (data?.isNotEmpty() == true) {
            val boundsBuilder = LatLngBounds.Builder()
            data.forEach {
                val latLng = LatLng(it.lat ?: 0.0, it.lon ?: 0.0)
                mMap?.addMarker(
                    MarkerOptions().position(latLng).title(it.name).icon(
                        vectorToBitmap(
                            com.nadhif.hayazee.common.R.drawable.ic_location_marker,
                            Color.parseColor("#F24E1E")
                        )
                    )
                )?.tag = it.id
                boundsBuilder.include(latLng)
            }

            mMap?.setOnMarkerClickListener(this)

            val bounds: LatLngBounds = boundsBuilder.build()
            mMap?.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
        }
    }

    private fun getMyLocation() {
        activity?.applicationContext?.let { ctx ->
            if ((ContextCompat.checkSelfPermission(
                    ctx,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    ctx,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
            ) {
                mMap?.isMyLocationEnabled = true
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val story = storyList?.find { marker.tag == it.id }
        story?.let {

            val bottomSheet = StoryMapsDetailBottomSheetFragment.newInstance(story)
            bottomSheet.show(childFragmentManager, "")
        }
        return false
    }
}