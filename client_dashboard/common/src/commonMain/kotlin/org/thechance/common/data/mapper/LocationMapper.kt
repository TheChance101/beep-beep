package org.thechance.common.data.mapper

import org.thechance.common.data.model.LocationInfoDto
import org.thechance.common.domain.entity.LocationInfo

fun LocationInfoDto.toEntity() = LocationInfo(
    lat ?: 0.0,
    lon ?: 0.0,
    countryCode ?: "IQ"
)