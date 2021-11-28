package com.toannguyen.data.response

import com.google.gson.annotations.SerializedName

data class TempResponse(
    @SerializedName("min")
    val min: Float,
    @SerializedName("max")
    val max: Float
)
