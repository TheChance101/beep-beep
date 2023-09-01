package org.thechance.api_gateway.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import java.util.*

interface ITokenService {
    fun generateToken(tokenConfig: TokenConfiguration, vararg tokenClaim: TokenClaim): String
}

@Single(binds = [ITokenService::class])
class TokenService : ITokenService {

    override fun generateToken(
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