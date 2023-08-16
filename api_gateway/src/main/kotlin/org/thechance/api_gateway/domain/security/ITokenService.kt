package org.thechance.api_gateway.domain.security

import org.thechance.api_gateway.domain.entity.TokenClaim
import org.thechance.api_gateway.domain.entity.TokenConfiguration

interface ITokenService {

    fun generateRefreshToken(tokenConfig: TokenConfiguration): String

    fun generateAccessToken(
        tokenConfig: TokenConfiguration,
        vararg tokenClaim: TokenClaim
    ): String

}