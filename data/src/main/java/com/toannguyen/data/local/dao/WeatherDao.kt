package com.toannguyen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toannguyen.data.local.entity.CityEntity
import com.toannguyen.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM City WHERE City.name LIKE :cityName")
    suspend fun getCity(cityName: String): List<CityEntity>

    @Query("SELECT * FROM Weather")
    suspend fun getWeather(): List<WeatherEntity>

    @Query(
        "SELECT Weather.* FROM Weather INNER JOIN City " +
                "ON Weather.city_id = City.id " +
                "WHERE City.short_name = :cityName " +
                "AND Weather.date >= :startDate " +
                "AND Weather.date <= :endDate"
    )
    suspend fun findWeatherByCityName(
        cityName: String,
        startDate: Long,
        endDate: Long
    ): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeather(weathers: List<WeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity): Long
}