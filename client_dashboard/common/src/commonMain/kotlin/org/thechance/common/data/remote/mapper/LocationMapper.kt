package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.LocationInfoDto
import org.thechance.common.domain.entity.LocationInfo

fun LocationInfoDto.toEntity() = LocationInfo(
    lat ?: 0.0,
    lon ?: 0.0,
    countryCode ?: "IQ"
)