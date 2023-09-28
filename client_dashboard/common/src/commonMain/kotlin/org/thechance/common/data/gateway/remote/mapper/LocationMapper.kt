package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.LocationInfoDto
import org.thechance.common.domain.entity.LocationInfo

fun LocationInfoDto.toEntity() = LocationInfo(
    lat ?: 0.0,
    lon ?: 0.0,
    countryCode ?: "IQ"
)