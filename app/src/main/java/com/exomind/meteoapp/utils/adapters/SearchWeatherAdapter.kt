package com.exomind.meteoapp.utils.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exomind.meteoapp.common.Constant
import com.exomind.meteoapp.databinding.WeatherItemBinding
import com.exomind.meteoapp.domain.models.Weather

class SearchWeatherAdapter(
    private val weathers: List<Weather>
) : RecyclerView.Adapter<SearchWeatherAdapter.MyViewHolderWeather>() {
    class MyViewHolderWeather(var itemBinding: WeatherItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(weather: Weather) {
            itemBinding.degreCelcious.text = "${(weather.main.temp - Constant.VALUE_SOUS_KELVIN).toInt()}°"
            itemBinding.minMax.text = "Min: ${(weather.main.temp_min -Constant.VALUE_SOUS_KELVIN).toInt()}°   Max: ${(weather.main.temp_max -Constant.VALUE_SOUS_KELVIN).toInt()}°"
            itemBinding.city.text = weather.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWeather {
        val view = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolderWeather(view)
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    override fun onBindViewHolder(holder: MyViewHolderWeather, position: Int) {
        val weather = weathers[position]
        holder.bindItem(weather = weather)
    }
}