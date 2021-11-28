package com.toannguyen.domain.models


data class WeatherModel(
    val date: Long,
    val tempAvg: Int,
    val pressure: Int,
    val humidity: Int,
    val description: String
)
