package com.toannguyen.data.mapper

import com.toannguyen.data.response.DescriptionResponse
import com.toannguyen.data.response.TempResponse
import com.toannguyen.data.response.WeatherInfoResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryMapperImplTest {

    private lateinit var mapper: WeatherRepositoryMapperImpl

    @Before
    fun setup() {
        mapper = WeatherRepositoryMapperImpl()
    }

    @Test
    fun `verify toWeatherModel()`() {
        // Given

        // When
        val result = mapper.toWeatherModel(mockWeatherInfoResponse)

        // Then
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat(result[0].date, CoreMatchers.`is`(1637899200L))
        MatcherAssert.assertThat(result[0].tempAvg, CoreMatchers.`is`(30))
        MatcherAssert.assertThat(result[0].pressure, CoreMatchers.`is`(100))
        MatcherAssert.assertThat(result[0].humidity, CoreMatchers.`is`(50))
        MatcherAssert.assertThat(result[0].description, CoreMatchers.`is`("description 1"))

        MatcherAssert.assertThat(result[1].date, CoreMatchers.`is`(1237899200L))
        MatcherAssert.assertThat(result[1].tempAvg, CoreMatchers.`is`(26))
        MatcherAssert.assertThat(result[1].pressure, CoreMatchers.`is`(120))
        MatcherAssert.assertThat(result[1].humidity, CoreMatchers.`is`(40))
        MatcherAssert.assertThat(result[1].description, CoreMatchers.`is`("description 2"))

        MatcherAssert.assertThat(result[2].date, CoreMatchers.`is`(1612349200L))
        MatcherAssert.assertThat(result[2].tempAvg, CoreMatchers.`is`(37))
        MatcherAssert.assertThat(result[2].pressure, CoreMatchers.`is`(10))
        MatcherAssert.assertThat(result[2].humidity, CoreMatchers.`is`(60))
        MatcherAssert.assertThat(result[2].description, CoreMatchers.`is`(""))
    }

    @Test
    fun `verify toWeatherModel() with description is empty`() {
        // Given
        val response = WeatherInfoResponse(
            date = 1637899200L,
            tempAvg = TempResponse(min = 10f, max = 53f),
            pressure = 60,
            humidity = 10,
            weatherDesc = listOf()
        )
        // When
        val result = mapper.toWeatherModel(listOf(response))

        // Then
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result[0].date, CoreMatchers.`is`(1637899200L))
        MatcherAssert.assertThat(result[0].tempAvg, CoreMatchers.`is`(31))
        MatcherAssert.assertThat(result[0].pressure, CoreMatchers.`is`(60))
        MatcherAssert.assertThat(result[0].humidity, CoreMatchers.`is`(10))
        MatcherAssert.assertThat(result[0].description, CoreMatchers.`is`(""))
    }

    @Test
    fun `verify toWeatherModel() with some data is null`() {
        // Given
        val response = WeatherInfoResponse(
            date = 1637899200L,
            tempAvg = null,
            pressure = null,
            humidity = 10,
            weatherDesc = listOf()
        )
        // When
        val result = mapper.toWeatherModel(listOf(response))

        // Then
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result[0].date, CoreMatchers.`is`(1637899200L))
        MatcherAssert.assertThat(result[0].tempAvg, CoreMatchers.`is`(0))
        MatcherAssert.assertThat(result[0].pressure, CoreMatchers.`is`(0))
        MatcherAssert.assertThat(result[0].humidity, CoreMatchers.`is`(10))
        MatcherAssert.assertThat(result[0].description, CoreMatchers.`is`(""))
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
}