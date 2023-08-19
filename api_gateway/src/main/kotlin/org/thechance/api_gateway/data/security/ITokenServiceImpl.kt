package org.thechance.api_gateway.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.gateway.ITokenService
import org.thechance.api_gateway.data.model.TokenClaim
import org.thechance.api_gateway.data.model.TokenConfiguration
import java.util.*

@Single(binds = [ITokenService::class])
class ITokenServiceImpl : ITokenService {

    override fun generateRefreshToken(tokenConfig: TokenConfiguration): String {
        return JWT.create()
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.accessTokenExpirationTimestamp))
            .sign(Algorithm.HMAC256(tokenConfig.secret))
    }

    override fun generateAccessToken(
        tokenConfig: TokenConfiguration,
        vararg tokenClaim: TokenClaim
    ): String {
        val accessToken = JWT.create()
                .withIssuer(tokenConfig.issuer)
                .withAudience(tokenConfig.audience)
                .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.accessTokenExpirationTimestamp))

        tokenClaim.forEach {
            accessToken.withClaim(it.name, it.value)
        }

        return accessToken.sign(Algorithm.HMAC256(tokenConfig.secret))
    }

}