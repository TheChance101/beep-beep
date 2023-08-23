package org.thechance.common.data.remote.model

import org.thechance.common.domain.entity.Admin

data class AdminDto(val fullName: String)

fun AdminDto.toEntity() = Admin(fullName = fullName)