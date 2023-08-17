package org.thechance.api_gateway.data.gateway

import org.thechance.api_gateway.data.model.TokenClaim
import org.thechance.api_gateway.data.model.TokenConfiguration

interface ITokenService {

    fun generateRefreshToken(tokenConfig: TokenConfiguration): String

    fun generateAccessToken(
        tokenConfig: TokenConfiguration,
        vararg tokenClaim: TokenClaim
    ): String

}