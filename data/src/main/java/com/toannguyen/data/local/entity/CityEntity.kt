package com.toannguyen.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "City",
    indices = [
        Index(value = ["short_name", "country"], unique = true)
    ]
)
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "short_name")
    var shortName: String,
    @ColumnInfo(name = "country")
    var country: String
)