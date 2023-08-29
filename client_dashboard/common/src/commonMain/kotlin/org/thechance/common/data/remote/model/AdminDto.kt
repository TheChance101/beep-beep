package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AdminDto(
    @SerializedName("full_name")
    val fullName: String? = null
)

fun AdminDto.toEntity(): String = fullName ?: ""