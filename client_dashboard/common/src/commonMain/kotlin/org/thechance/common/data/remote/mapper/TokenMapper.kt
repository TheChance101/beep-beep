package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.UserTokens


fun UserTokensRemoteDto.toEntity() = UserTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)