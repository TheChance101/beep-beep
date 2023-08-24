package org.thechance.api_gateway.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.thechance.api_gateway.data.model.TokenType
import org.thechance.api_gateway.plugins.utils.respondUnauthorized

fun Application.configureJWTAuthentication() {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtDomain = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("tokenType", TokenType.ACCESS_TOKEN.name)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else
                    null
            }
            respondUnauthorized()
        }

        jwt("refresh-jwt") {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("tokenType", TokenType.REFRESH_TOKEN.name)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else
                    null
            }

            respondUnauthorized()
        }
    }
}