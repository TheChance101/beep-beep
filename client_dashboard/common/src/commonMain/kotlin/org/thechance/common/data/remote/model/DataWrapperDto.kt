package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import org.thechance.common.domain.entity.DataWrapper

@Serializable
data class DataWrapperDto<T>(
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_result")
    val totalResult: Int?,
    @SerializedName("result")
    val result: List<T>?
)

fun <T> DataWrapperDto<T>.toEntity():DataWrapper<T> =
    DataWrapper(
        totalPages = totalPages ?: 0,
        numberOfResult =  totalResult ?: 0,
        result = result ?: emptyList()
    )