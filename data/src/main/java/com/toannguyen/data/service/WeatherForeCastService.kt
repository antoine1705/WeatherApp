package com.toannguyen.data.service

import com.toannguyen.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val TYPE_CELSIUS = "Metric"
const val TYPE_FAHRENHEIT = "Imperial"
const val WEATHER_APP_ID="60c6fbeb4b93ac653c492ba806fc346d"

interface WeatherForeCastService {
    @GET("daily?")
    suspend fun getWeatherInformation(
        @Query("q") city: String,
        @Query("cnt") periodDate: Int = 7,
        @Query("units") temperatureType: String = TYPE_CELSIUS,
        @Query("appid") appId: String = WEATHER_APP_ID
    ): Response<WeatherResponse>
}