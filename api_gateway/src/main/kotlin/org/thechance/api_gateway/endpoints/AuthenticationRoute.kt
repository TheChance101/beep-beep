package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.security.TokenConfiguration
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role
import java.util.*


fun Route.authenticationRoutes(tokenConfiguration: TokenConfiguration) {
    val identityGateway: IIdentityGateway by inject()

    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    post("/signup") {
        val params = call.receiveParameters()
        val fullName = params["fullName"]?.trim()
        val username = params["username"]?.trim()
        val password = params["password"]?.trim()
        val email = params["email"]?.trim()

        val (language, countryCode) = extractLocalizationHeader()

        val result = identityGateway.createUser(
            fullName = fullName.toString(),
            username = username.toString(),
            password = password.toString(),
            email = email.toString(),
            locale = Locale(language, countryCode)
        )
        val locale = Locale(language, countryCode)
        val successMessage = localizedMessagesFactory.createLocalizedMessages(locale).userCreatedSuccessfully

        respondWithResult(HttpStatusCode.Created, result, successMessage)
    }

    post("/login") {
        val params = call.receiveParameters()
        val userName = params["username"]?.trim().toString()
        val password = params["password"]?.trim().toString()

        val (language, countryCode) = extractLocalizationHeader()

        val token = identityGateway.loginUser(
            userName,
            password,
            tokenConfiguration,
            Locale(language, countryCode)
        )
        respondWithResult(HttpStatusCode.Created, token)
    }

    authenticateWithRole(Role.END_USER) {
        get("/me") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val id = tokenClaim?.payload?.getClaim(Claim.USER_ID).toString()
            respondWithResult(HttpStatusCode.OK, id)
        }

        post("/refresh-access-token") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val userId = tokenClaim?.payload?.getClaim(Claim.USER_ID).toString()
            val username = tokenClaim?.payload?.getClaim(Claim.USERNAME).toString()
            val userPermission = tokenClaim?.payload?.getClaim(Claim.PERMISSION)?.asString()?.toInt() ?: 1
            val token = identityGateway.generateUserTokens(userId, username, userPermission, tokenConfiguration)
            respondWithResult(HttpStatusCode.Created, token)
        }
    }
}