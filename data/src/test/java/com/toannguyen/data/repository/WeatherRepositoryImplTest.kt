package com.toannguyen.data.repository

import com.toannguyen.data.local.LocalService
import com.toannguyen.data.local.entity.WeatherEntity
import com.toannguyen.data.mapper.WeatherRepositoryMapper
import com.toannguyen.data.response.*
import com.toannguyen.data.service.WeatherForeCastService
import com.toannguyen.domain.models.ErrorModel
import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.usecase.base.Error
import com.toannguyen.domain.usecase.base.Result
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {

    private val service: WeatherForeCastService = mockk()

    private val localService: LocalService = mockk()

    private val mapper: WeatherRepositoryMapper = mockk()

    private val response: Response<WeatherResponse> = mockk()

    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(service, localService, mapper)
    }

    @Test
    fun `get weather response success`() = runBlockingTest {
        // Given
        val cityName = "saigon"
        coEvery { service.getWeatherInformation(cityName) } returns response

        coEvery { localService.getWeatherByCity(cityName, 7) } returns listOf()

        every { response.isSuccessful } returns true

        every { response.body() } returns mockWeatherResponse

        coEvery { localService.saveWeatherAndCity(cityName, mockWeatherResponse) } just runs

        val captureItems = slot<List<WeatherInfoResponse>>()
        every { mapper.toWeatherModel(capture(captureItems)) } returns weatherModels

        // When
        val result = repository.getWeatherInfo(cityName)

        // Then
        MatcherAssert.assertThat(result, `is`(instanceOf(Result.Success::class.java)))
        result.handleResult<List<WeatherModel>>(successBlock = {
            MatcherAssert.assertThat(it.size, `is`(3))
            MatcherAssert.assertThat(it, `is`(weatherModels))
        })
        MatcherAssert.assertThat(captureItems.captured, `is`(mockWeatherResponse.items))

        coVerify(exactly = 1) {
            localService.saveWeatherAndCity(cityName, mockWeatherResponse)
        }
    }

    @Test
    fun `get weather response success with empty items`() = runBlockingTest {
        // Given
        val cityName = "saigon"
        coEvery { service.getWeatherInformation(cityName) } returns response

        coEvery { localService.getWeatherByCity(cityName, 7) } returns listOf()

        every { response.isSuccessful } returns true

        every { response.body() } returns null

        every { response.errorBody() } returns null

        every { response.body() } returns WeatherResponse(
            city = CityResponse(id = 1L, name = "name", country = "country"),
            items = listOf()
        )

        every { mapper.toErrorModel(any()) } returns null

        // When
        val result = repository.getWeatherInfo(cityName)

        // Then
        MatcherAssert.assertThat(result, `is`(instanceOf(Result.Failure::class.java)))
        result.handleResult<List<WeatherModel>>(failureBlock = {
            MatcherAssert.assertThat(it, `is`(Error.GenericError))
        })
    }

    @Test
    fun `get weather response success with response body is null and load data is empty`() =
        runBlockingTest {
            // Given
            val cityName = "saigon"
            coEvery { service.getWeatherInformation(cityName) } returns response

            coEvery { localService.getWeatherByCity(cityName, 7) } returns listOf()

            every { response.isSuccessful } returns true

            every { response.body() } returns null

            every { response.errorBody() } returns null

            every { mapper.toErrorModel(any()) } returns null

            // When
            val result = repository.getWeatherInfo(cityName)

            // Then
            MatcherAssert.assertThat(result, `is`(instanceOf(Result.Failure::class.java)))
            result.handleResult<List<WeatherModel>>(failureBlock = {
                MatcherAssert.assertThat(it, `is`(Error.GenericError))
            })
        }

    @Test
    fun `get weather response success with response body is null and load data from local`() =
        runBlockingTest {
            // Given
            val cityName = "saigon"
            coEvery { service.getWeatherInformation(cityName) } returns response

            coEvery { localService.getWeatherByCity(cityName, 7) } returns mockWeatherEntities

            every { response.isSuccessful } returns true

            every { response.body() } returns null

            every { response.errorBody() } returns null

            every { mapper.toWeatherModelFromEntity(mockWeatherEntities) } returns weatherModels

            // When
            val result = repository.getWeatherInfo(cityName)

            // Then
            MatcherAssert.assertThat(result, `is`(instanceOf(Result.Success::class.java)))
            result.handleResult<List<WeatherModel>>(successBlock = {
                MatcherAssert.assertThat(it.size, `is`(3))
                MatcherAssert.assertThat(it, `is`(weatherModels))
            })
        }

    @Test
    fun `get weather response fail with response status is unSuccessful`() = runBlockingTest {
        // Given
        val cityName = "saigon"
        coEvery { service.getWeatherInformation(cityName) } returns response

        coEvery { localService.getWeatherByCity(cityName, 7) } returns listOf()

        every { response.isSuccessful } returns false

        every { response.body() } returns null

        every { response.errorBody() } returns null

        val errorModel = ErrorModel(cod = "404", "city not found")
        every { mapper.toErrorModel(any()) } returns errorModel

        // When
        val result = repository.getWeatherInfo(cityName)

        // Then
        MatcherAssert.assertThat(result, `is`(instanceOf(Result.Failure::class.java)))
        result.handleResult<List<WeatherModel>>(failureBlock = {
            MatcherAssert.assertThat(it, `is`(instanceOf(Error.ResponseError::class.java)))
        })
    }

    @Test
    fun `get weather response fail with network error`() = runBlockingTest {
        // Given
        val cityName = "saigon"
        coEvery { service.getWeatherInformation(cityName) } throws Exception()

        // When
        val result = repository.getWeatherInfo(cityName)

        // Then
        MatcherAssert.assertThat(result, `is`(instanceOf(Result.Failure::class.java)))
        result.handleResult<List<WeatherModel>>(failureBlock = {
            MatcherAssert.assertThat(it, `is`(Error.NetworkError))
        })
    }

    private val mockWeatherResponse by lazy {
        WeatherResponse(
            city = CityResponse(id = 1L, name = "name", country = "country"),
            items = mockWeatherInfoResponse
        )
    }

    private val mockWeatherInfoResponse = listOf(
        WeatherInfoResponse(
            date = 1637899200L,
            tempAvg = TempResponse(min = 10f, max = 50f),
            pressure = 100,
            humidity = 50,
            weatherDesc = listOf(DescriptionResponse(id = 1, description = "description 1"))
        ),
        WeatherInfoResponse(
            date = 1237899200L,
            tempAvg = TempResponse(min = 23f, max = 30f),
            pressure = 120,
            humidity = 40,
            weatherDesc = listOf(DescriptionResponse(id = 2, description = "description 2"))
        ),
        WeatherInfoResponse(
            date = 1612349200L,
            tempAvg = TempResponse(min = 30f, max = 45f),
            pressure = 10,
            humidity = 60,
            weatherDesc = listOf()
        )
    )

    private val mockWeatherEntities = listOf(
        WeatherEntity(
            date = 1637899200L,
            tempAvg = 25,
            pressure = 100,
            humidity = 50,
            weatherDesc = "description 1"
        ),
        WeatherEntity(
            date = 1237899200L,
            tempAvg = 30,
            pressure = 120,
            humidity = 40,
            weatherDesc = "description 2"
        ),
        WeatherEntity(
            date = 1612349200L,
            tempAvg = 15,
            pressure = 10,
            humidity = 60,
            weatherDesc = ""
        )
    )

    private val weatherModels = listOf(
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