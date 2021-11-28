package com.toannguyen.weatherapp.fragment

import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.usecase.GetWeatherInfoUseCase
import com.toannguyen.domain.usecase.base.Error
import com.toannguyen.domain.usecase.base.Result
import com.toannguyen.domain.usecase.base.SimpleResult
import com.toannguyen.weatherapp.fragment.adapter.WeatherAdapter
import com.toannguyen.weatherapp.mapper.WeatherMapper
import com.toannguyen.weatherapp.tools.ViewModelTest
import com.toannguyen.weatherapp.tools.captureValues
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest : ViewModelTest() {

    private val getWeatherInfoUseCase: GetWeatherInfoUseCase = mockk()

    private val weatherMapper: WeatherMapper = mockk()

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        viewModel = WeatherViewModel(getWeatherInfoUseCase, weatherMapper)
    }

    @Test
    fun `test getWeatherForecast() with loading`() {
        // Given
        val cityName = "saigon"
        every { getWeatherInfoUseCase.invoke(any(), any()) } answers {
            arg<(SimpleResult) -> Unit>(1).invoke(
                Result.State.Loading
            )
        }

        val capturesLoading = viewModel.loadingObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesLoading.size, `is`(1))
        MatcherAssert.assertThat(capturesLoading[0], `is`(true))
    }

    @Test
    fun `test getWeatherForecast() with response error`() {
        // Given
        val cityName = "saigon"
        every { getWeatherInfoUseCase.invoke(any(), any()) } answers {
            arg<(SimpleResult) -> Unit>(1).invoke(
                Result.Failure(Error.GenericError)
            )
        }

        val capturesError = viewModel.errorObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesError.size, `is`(1))
        MatcherAssert.assertThat(capturesError[0], `is`(Error.GenericError))
    }

    @Test
    fun `test getWeatherForecast() with network error`() {
        // Given
        val cityName = "saigon"
        every { getWeatherInfoUseCase.invoke(any(), any()) } answers {
            arg<(SimpleResult) -> Unit>(1).invoke(
                Result.Failure(Error.NetworkError)
            )
        }

        val capturesError = viewModel.errorObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesError.size, `is`(1))
        MatcherAssert.assertThat(capturesError[0], `is`(Error.NetworkError))
    }

    @Test
    fun `test getWeatherForecast() with empty items`() {
        // Given
        val cityName = "saigon"
        every { getWeatherInfoUseCase.invoke(any(), any()) } answers {
            arg<(SimpleResult) -> Unit>(1).invoke(
                Result.Success(mockItems)
            )
        }

        every { weatherMapper.toWeatherUI(mockItems) } returns
                listOf(WeatherAdapter.AdapterItem.Empty)

        val capturesItems = viewModel.itemsObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesItems.size, `is`(1))
        MatcherAssert.assertThat(capturesItems[0]!![0], `is`(WeatherAdapter.AdapterItem.Empty))
    }

    @Test
    fun `test getWeatherForecast() with items`() {
        // Given
        val cityName = "saigon"
        every { getWeatherInfoUseCase.invoke(any(), any()) } answers {
            arg<(SimpleResult) -> Unit>(1).invoke(
                Result.Success(mockItems)
            )
        }

        val adapterItems = listOf(
            WeatherAdapter.AdapterItem.WeatherUI(
                date = "Fri, 26 Nov 2021",
                tempAvg = "20",
                pressure = "40",
                humidity = "20",
                description = "description 1"
            ),
            WeatherAdapter.AdapterItem.WeatherUI(
                date = "Fri, 28 Dec 2021",
                tempAvg = "20",
                pressure = "40",
                humidity = "20",
                description = "description 1"
            ),
        )
        every { weatherMapper.toWeatherUI(mockItems) } returns adapterItems

        val capturesItems = viewModel.itemsObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesItems.size, `is`(1))
        MatcherAssert.assertThat(capturesItems[0]!![0], `is`(adapterItems[0]))
        MatcherAssert.assertThat(capturesItems[0]!![1], `is`(adapterItems[1]))
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