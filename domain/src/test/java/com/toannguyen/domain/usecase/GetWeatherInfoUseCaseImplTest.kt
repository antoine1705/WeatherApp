package com.toannguyen.domain.usecase

import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.repository.WeatherRepository
import com.toannguyen.domain.tools.InstantCoroutineDispatchers
import com.toannguyen.domain.usecase.base.Error
import com.toannguyen.domain.usecase.base.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWeatherInfoUseCaseImplTest {

    private val repository: WeatherRepository = mockk()

    private lateinit var useCase: GetWeatherInfoUseCase

    @Before
    fun setup() {
        useCase = GetWeatherInfoUseCaseImpl(
            weatherRepository = repository,
            dispatcherProvider = InstantCoroutineDispatchers()
        )
    }

    @Test
    fun `get items from repository success`() = runBlockingTest {
        val city = "saigon"
        val response = Result.Success(mockItems)
        coEvery {
            repository.getWeatherInfo(city)
        } returns response

        var position = 0
        useCase(this, city) {
            val expectation = listOf(
                Result.State.Loading,
                response,
                Result.State.Loaded
            )
            MatcherAssert.assertThat(it, `is`(expectation[position]))
            position++
        }
    }

    @Test
    fun `get items from repository return network error`() = runBlockingTest {
        val city = "saigon"
        val response = Result.Failure(Error.NetworkError)
        coEvery {
            repository.getWeatherInfo(city)
        } returns response

        var position = 0
        useCase.invoke(this, city) {
            val expectation = listOf(
                Result.State.Loading,
                response,
                Result.State.Loaded
            )
            MatcherAssert.assertThat(it, `is`(expectation[position]))
            position++
        }
    }

    @Test
    fun `get items from repository return response error`() = runBlockingTest {
        val city = "saigon"
        val response = Result.Failure(Error.GenericError)
        coEvery {
            repository.getWeatherInfo(city)
        } returns response

        var position = 0
        useCase.invoke(this, city) {
            val expectation = listOf(
                Result.State.Loading,
                response,
                Result.State.Loaded
            )
            MatcherAssert.assertThat(it, `is`(expectation[position]))
            position++
        }
    }

    private val mockItems = listOf(
        WeatherModel(
            date = 1637899200L,
            tempAvg = 20,
            pressure = 80,
            humidity = 80,
            description = "description 1"
        ),
        WeatherModel(
            date = 1637402887L,
            tempAvg = 10,
            pressure = 20,
            humidity = 30,
            description = "description 2"
        ),
        WeatherModel(
            date = 1635847687L,
            tempAvg = 30,
            pressure = 40,
            humidity = 50,
            description = "description 3"
        )
    )
}