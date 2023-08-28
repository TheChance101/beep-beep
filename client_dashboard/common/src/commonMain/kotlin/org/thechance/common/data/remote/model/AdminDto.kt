package org.thechance.common.data.remote.model

data class AdminDto(val fullName: String)

fun AdminDto.toEntity(): String = fullName