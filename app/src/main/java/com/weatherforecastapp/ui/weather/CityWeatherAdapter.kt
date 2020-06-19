package com.weatherforecastapp.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.weatherforecastapp.R
import com.weatherforecastapp.data.models.CurrentWeather
import com.weatherforecastapp.utils.AutoUpdatableAdapter
import kotlinx.android.synthetic.main.item_current_weather.view.*
import kotlin.properties.Delegates

class CityWeatherAdapter(private val listener: (CurrentWeather) -> Unit) :
    RecyclerView.Adapter<CityWeatherAdapter.CityWeatherHolder>(),
    AutoUpdatableAdapter {

    private var list: List<CurrentWeather> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        autoNotify(oldValue, newValue, PAYLOAD) { o, n -> o.city.id == n.city.id }
    }

    fun setData(data: List<CurrentWeather>) {
        list = data
    }

    private companion object {
        const val VIEW_WEATHER = R.layout.item_current_weather
        const val PAYLOAD = "PAYLOAD"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(VIEW_WEATHER, parent, false)
        return CityWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: CityWeatherHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: CityWeatherHolder,
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

    inner class CityWeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon = view.icon
        private val name = view.name
        private val degree = view.degree

        init {
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                listener.invoke(list[adapterPosition])
            }
        }

        fun bind(item: CurrentWeather) {
            icon.load(item.forecast.icon)
            name.text = item.city.name
            degree.text = item.forecast.tempMinMax
        }
    }
}