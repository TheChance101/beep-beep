package org.thechance.api_gateway.data.gateway

import org.thechance.api_gateway.data.model.TokenClaim
import org.thechance.api_gateway.data.model.TokenConfiguration

interface ITokenService {
    fun generateToken(tokenConfig: TokenConfiguration, vararg tokenClaim: TokenClaim): String

}