package com.toannguyen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toannguyen.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.toannguyen.data.local.dao.WeatherDao
import com.toannguyen.data.local.entity.CityEntity
import com.toannguyen.data.local.entity.WeatherEntity

@Database(
    entities = [CityEntity::class, WeatherEntity::class],
    version = DATABASE_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
    }

    abstract fun weatherDao(): WeatherDao
}