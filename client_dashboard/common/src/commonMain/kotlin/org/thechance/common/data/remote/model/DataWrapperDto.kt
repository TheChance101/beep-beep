package org.thechance.common.data.remote.model

import org.thechance.common.domain.entity.DataWrapper

data class DataWrapperDto<T>(
    val totalPages: Int,
    val totalResult: Int,
    val result: List<T>
)

fun <T> DataWrapperDto<T>.toEntity() =
    DataWrapper(
        totalPages = totalPages,
        numberOfResult = totalResult,
        result = result
    )