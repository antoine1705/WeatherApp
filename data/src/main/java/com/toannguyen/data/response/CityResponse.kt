package com.toannguyen.data.response

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String
)
