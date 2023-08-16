package org.thechance.api_gateway.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import org.thechance.api_gateway.domain.entity.TokenClaim
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.domain.entity.UserTokens
import org.thechance.api_gateway.domain.security.ITokenService
import java.util.*

@Single(binds = [ITokenService::class])
class ITokenServiceImpl : ITokenService {

    override fun generateTokens(tokenConfig: TokenConfiguration, vararg tokenClaim: TokenClaim): UserTokens {

        val expirationDate = Date(System.currentTimeMillis() + tokenConfig.expiresAt)

        val accessTokenValue = generateAccessToken(tokenConfig, expirationDate, tokenClaim)

        val refreshTokenValue = generateRefreshToken()

        return UserTokens(expirationDate.time, expirationDate.time, accessTokenValue, refreshTokenValue)
    }

    private fun generateRefreshToken(): String {
        return UUID.randomUUID().toString()
    }

    private fun generateAccessToken(
        tokenConfig: TokenConfiguration,
        expirationDate: Date,
        tokenClaim: Array<out TokenClaim>
    ): String {
        val accessToken = JWT.create()
            .withIssuer(tokenConfig.issuer)
            .withAudience(tokenConfig.audience)
            .withExpiresAt(expirationDate)
            .withClaim("tokenType", "accessToken")

        tokenClaim.forEach {
            accessToken.withClaim(it.name, it.value)
        }

        return accessToken.sign(Algorithm.HMAC256(tokenConfig.secret))
    }

//    override fun verify(token: String): Boolean {
//        return try {
//            JWT.require(Algorithm.HMAC256("secret"))
//                .build()
//                .verify(token)
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }

}