package com.toannguyen.data.repository

import com.toannguyen.data.local.LocalService
import com.toannguyen.data.local.entity.WeatherEntity
import com.toannguyen.data.mapper.WeatherRepositoryMapper
import com.toannguyen.data.service.WeatherForeCastService
import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.repository.WeatherRepository
import com.toannguyen.domain.usecase.base.Error
import com.toannguyen.domain.usecase.base.Result

class WeatherRepositoryImpl(
    private val weatherForeCastService: WeatherForeCastService,
    private val localService: LocalService,
    private val weatherRepositoryMapper: WeatherRepositoryMapper,
) : WeatherRepository {
    companion object {
        private const val periodDate = 7
    }

    override suspend fun getWeatherInfo(cityName: String): Result<List<WeatherModel>, Error> {
        var localItems: List<WeatherEntity>? = null
        return try {
            val response = weatherForeCastService.getWeatherInformation(cityName)
            val weatherResponse = response.body()
            val items = weatherResponse?.items
            localItems = localService.getWeatherByCity(cityName, periodDate)
            when {
                response.isSuccessful && !items.isNullOrEmpty() -> {
                    localService.saveWeatherAndCity(cityName, weatherResponse)
                    Result.Success(weatherRepositoryMapper.toWeatherModel(items))
                }
                !localItems.isNullOrEmpty() -> {
                    Result.Success(weatherRepositoryMapper.toWeatherModelFromEntity(localItems))
                }
                else -> {
                    weatherRepositoryMapper.toErrorModel(response.errorBody())?.let {
                        Result.Failure(Error.ResponseError(it))
                    } ?: Result.Failure(Error.GenericError)
                }
            }
        } catch (error: Exception) {
            if (localItems.isNullOrEmpty()) {
                Result.Failure(Error.NetworkError)
            } else {
                Result.Success(weatherRepositoryMapper.toWeatherModelFromEntity(localItems))
            }
        }
    }
}