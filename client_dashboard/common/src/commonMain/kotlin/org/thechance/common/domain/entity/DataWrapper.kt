package org.thechance.common.domain.entity

data class DataWrapper<T>(
    val totalPages:Int,
    val numberOfResult:Int,
    val result:List<T>
)