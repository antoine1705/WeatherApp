package com.toannguyen.domain.repository

import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.usecase.base.Result
import com.toannguyen.domain.usecase.base.Error

/**
 * Interface that represents a Repository for getting [WeatherModel] related data.
 */
interface WeatherRepository {
    /**
     * Get a List of [WeatherModel].
     */
    suspend fun getWeatherInfo(cityName: String): Result<List<WeatherModel>, Error>

}