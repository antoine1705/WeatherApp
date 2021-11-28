package com.toannguyen.weatherapp.mapper

import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.weatherapp.extensions.DATE_FORMAT_PATTERN_1
import com.toannguyen.weatherapp.extensions.formatTimeByPattern
import com.toannguyen.weatherapp.fragment.adapter.WeatherAdapter
import java.util.concurrent.TimeUnit

interface WeatherMapper {
    fun toWeatherUI(items: List<WeatherModel>): List<WeatherAdapter.AdapterItem>
}

class WeatherMapperImpl : WeatherMapper {
    override fun toWeatherUI(items: List<WeatherModel>): List<WeatherAdapter.AdapterItem> {
        if (items.isNullOrEmpty()) return listOf(WeatherAdapter.AdapterItem.Empty)
        return items.map {
            val date = TimeUnit.SECONDS.toMillis(it.date).formatTimeByPattern(DATE_FORMAT_PATTERN_1)
            val tempAve = it.tempAvg.toString()
            val pressure = it.pressure.toString()
            val humidity = it.humidity.toString()
            val description = it.description
            WeatherAdapter.AdapterItem.WeatherUI(
                date = date,
                tempAvg = tempAve,
                pressure = pressure,
                humidity = humidity,
                description = description
            )
        }
    }
}