package org.thechance.api_gateway.domain.security

import org.thechance.api_gateway.domain.entity.TokenClaim
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.domain.entity.UserTokens

interface ITokenService {

    fun generateTokens(tokenConfig: TokenConfiguration, vararg tokenClaim: TokenClaim): UserTokens

}