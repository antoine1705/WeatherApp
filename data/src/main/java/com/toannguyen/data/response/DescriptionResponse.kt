package com.toannguyen.data.response

import com.google.gson.annotations.SerializedName

data class DescriptionResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("description")
    val description: String? = null
)
