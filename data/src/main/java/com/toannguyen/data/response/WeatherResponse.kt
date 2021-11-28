package com.toannguyen.data.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("city")
    val city: CityResponse? = null,
    @SerializedName("list")
    val items: List<WeatherInfoResponse>? = null
)