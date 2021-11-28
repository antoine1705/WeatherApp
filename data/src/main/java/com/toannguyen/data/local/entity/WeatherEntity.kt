package com.toannguyen.data.local.entity

import androidx.room.*
import com.toannguyen.data.response.TempResponse

@Entity(
    tableName = "Weather",
    indices = [Index(value = ["date", "city_id"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["id"],
        childColumns = ["city_id"]
    )]
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "date")
    var date: Long? = null,
    @ColumnInfo(name = "temp")
    var tempAvg: Int? = null,
    @ColumnInfo(name = "pressure")
    var pressure: Int? = null,
    @ColumnInfo(name = "humidity")
    var humidity: Int? = null,
    @ColumnInfo(name = "weather")
    var weatherDesc: String? = null,
    @ColumnInfo(name = "city_id", index = true)
    var cityId: Long? = null
)