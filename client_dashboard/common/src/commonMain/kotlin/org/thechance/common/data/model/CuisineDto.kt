package org.thechance.common.data.model

import com.google.gson.annotations.SerializedName

data class CuisineDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
