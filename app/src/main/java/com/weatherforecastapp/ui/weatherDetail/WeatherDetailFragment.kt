package com.weatherforecastapp.ui.weatherDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.weatherforecastapp.R
import com.weatherforecastapp.data.models.City
import com.weatherforecastapp.data.models.Forecast
import com.weatherforecastapp.extensions.observeX
import com.weatherforecastapp.utils.toCelsius
import com.weatherforecastapp.utils.toMmHg
import com.weatherforecastapp.utils.toPercent
import com.weatherforecastapp.utils.toSpeed
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {

    private val viewModel: WeatherDetailViewModel by viewModel()
    private val args by navArgs<WeatherDetailFragmentArgs>()
    private val navController by lazy { findNavController() }

    private val city: City
        get() = args.city

    private val forecastAdapter by lazy { ForecastAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.configure()
        forecastsRv.setUp()
        subscribeToViewModle()
    }

    private fun Toolbar.configure() {
        title = city.name
        setNavigationOnClickListener { findNavController().popBackStack() }
        inflateMenu(R.menu.city_details)
        setOnMenuItemClickListener {
            viewModel.deleteCity(city)
            navController.navigateUp()
            true
        }
    }

    private fun RecyclerView.setUp() {
        adapter = forecastAdapter
    }

    private fun subscribeToViewModle() {
        viewModel.initCity(city)
        viewModel.cityLiveData.observeX(this) {
            fillForecast(it.forecast)
        }

        viewModel.forecastLiveData.observeX(this) {
            Toast.makeText(requireContext(), "${it.size}", Toast.LENGTH_SHORT).show()
//            forecastAdapter.setData(it)
        }
    }

    private fun fillForecast(forecast: Forecast) {
        icon.load(forecast.icon)
        main.text = forecast.main
        degree.text = forecast.temp.toCelsius()
        pressureDIV.setValue(forecast.pressure.toMmHg())
        humidityDIV.setValue(forecast.humidity.toPercent())
        windDIV.setValue(forecast.windSpeed.toSpeed())
    }
}