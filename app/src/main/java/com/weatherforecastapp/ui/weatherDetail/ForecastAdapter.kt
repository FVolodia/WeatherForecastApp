package com.weatherforecastapp.ui.weatherDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherforecastapp.R
import com.weatherforecastapp.data.models.WeatherForecast
import com.weatherforecastapp.utils.AutoUpdatableAdapter
import kotlinx.android.synthetic.main.item_current_weather.view.*
import kotlin.properties.Delegates

class ForecastAdapter :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>(),
    AutoUpdatableAdapter {

    private var list: List<WeatherForecast> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        autoNotify(oldValue, newValue, PAYLOAD) { o, n -> o.city.id == n.city.id }
    }

    fun setData(data: List<WeatherForecast>) {
        list = data
    }

    private companion object {
        const val VIEW_WEATHER = R.layout.item_forecast
        const val PAYLOAD = "PAYLOAD"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(VIEW_WEATHER, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: ForecastViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains(PAYLOAD)) {
            onBindViewHolder(holder, position)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount() = list.size

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon = view.icon
        private val name = view.name
        private val degree = view.degree


        fun bind(item: WeatherForecast) {
//            icon.load(item.forecast.icon)
//            name.text = item.city.name
//            degree.text = item.forecast.tempMinMax
        }
    }
}