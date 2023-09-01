package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.common.domain.entity.DataWrapper

@Serializable
data class DataWrapperDto<T>(
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_result")
    val totalResult: Int?,
    @SerialName("result")
    val result: List<T>?
)

fun <T> DataWrapperDto<T>.toEntity():DataWrapper<T> =
    DataWrapper(
        totalPages = totalPages ?: 0,
        numberOfResult =  totalResult ?: 0,
        result = result ?: emptyList()
    )