package com.weatherforecastapp.ui.weather

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.weatherforecastapp.R
import com.weatherforecastapp.data.ResultData
import com.weatherforecastapp.data.models.City
import com.weatherforecastapp.data.models.CurrentWeather
import com.weatherforecastapp.extensions.observeX
import com.weatherforecastapp.utils.LocationManager
import com.weatherforecastapp.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private companion object {
        const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        const val LOCATION_REQUEST_CODE = 1001
        const val AUTOCOMPLETE_REQUEST_CODE = 1002
        private val fields = listOf(
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )
    }

    private val viewModel: WeatherViewModel by viewModel()
    private val navController by lazy { findNavController() }

    private val locationManager by lazy {
        LocationManager(this) {
            viewModel.initLocation(it)
        }
    }

    private val permissionHelper by lazy {
        PermissionHelper(
            this,
            LOCATION_REQUEST_CODE,
            doFunction = { locationManager.receiveLocation() },
            permissionDenied = {
                Toast.makeText(requireContext(), "DENIED", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private val cityAdapter by lazy {
        CityWeatherAdapter {
            openDetail(it.city)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Places.initialize(requireContext(), getString(R.string.place_api_key))
        checkLocationPermission()
        subscribeToViewModel()
        setUpListeners()
        citiesRv.setup()
    }

    private fun RecyclerView.setup() {
        adapter = cityAdapter
    }

    private fun subscribeToViewModel() {
        viewModel.currentCityWeatherLiveData.observeX(this) {
            when (it) {
                is ResultData.Success -> fill(it.data)
                is ResultData.Error -> showError(it.error)
            }
        }
        viewModel.citiesLiveData.observeX(this) {
            cityAdapter.setData(it)
        }
    }

    private fun setUpListeners() {
        findLocation.setOnClickListener { startSearchPlace() }
    }

    private fun checkLocationPermission() {
        permissionHelper.checkPermission(LOCATION_PERMISSION)
    }

    private fun startSearchPlace() {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        )
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    private fun fill(weather: CurrentWeather) {
        icon.load(weather.forecast.icon)
        name.text = weather.city.name
        degree.text = weather.forecast.tempMinMax
    }

    private fun openDetail(city: City) {
        navController.navigate(WeatherFragmentDirections.actionGlobalWeatherDetailFragment(city))
    }

    private fun showError(t: Throwable) {
        Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionHelper.checkPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {

                    val place = Autocomplete.getPlaceFromIntent(it)

                    val lat = place.latLng?.latitude
                    val lon = place.latLng?.longitude

                    if (lat != null && lon != null) {

                        viewModel.initLocation(
                            LocationManager.LocationData(lat, lon)
                        )
                    }
                }
            }
        }
    }
}