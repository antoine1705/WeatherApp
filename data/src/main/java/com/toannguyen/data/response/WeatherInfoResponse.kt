package com.toannguyen.data.response

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse(
    @SerializedName("dt")
    val date: Long? = null,
    @SerializedName("temp")
    val tempAvg: TempResponse? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("weather")
    val weatherDesc: List<DescriptionResponse>
)
