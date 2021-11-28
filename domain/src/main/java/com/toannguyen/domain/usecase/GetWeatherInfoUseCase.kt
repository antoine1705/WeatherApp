package com.toannguyen.domain.usecase

import com.toannguyen.domain.DispatcherProvider
import com.toannguyen.domain.repository.WeatherRepository
import com.toannguyen.domain.usecase.base.BaseUseCase
import com.toannguyen.domain.usecase.base.Result
import com.toannguyen.domain.usecase.base.SimpleResult
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

interface GetWeatherInfoUseCase : BaseUseCase<String>

class GetWeatherInfoUseCaseImpl(
    private val weatherRepository: WeatherRepository,
    override val dispatcherProvider: DispatcherProvider
) : GetWeatherInfoUseCase {

    override suspend fun run(params: String): ReceiveChannel<SimpleResult> = produce {
        // Started loading
        send(Result.State.Loading)
        // Get person from persistence and send it, synchronous
        send(weatherRepository.getWeatherInfo(params))
        // Ended loading
        send(Result.State.Loaded)
    }
}