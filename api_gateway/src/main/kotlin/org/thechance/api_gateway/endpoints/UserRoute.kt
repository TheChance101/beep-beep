package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.mappers.toManagedUser
import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
fun Route.userRoutes(tokenConfiguration: TokenConfiguration) {
    val gateway: IIdentityGateway by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    post("/signup") {
        val params = call.receiveParameters()
        val fullName = params["fullName"]?.trim()
        val username = params["username"]?.trim()
        val password = params["password"]?.trim()
        val email = params["email"]?.trim()

        val (language, countryCode) = extractLocalizationHeader()

        val result = gateway.createUser(
            fullName = fullName.toString(),
            username = username.toString(),
            password = password.toString(),
            email = email.toString(),
            locale = Locale(language, countryCode)
        )
        val locale = Locale(language, countryCode)
        val successMessage = localizedMessagesFactory.createLocalizedMessages(locale).userCreatedSuccessfully

        respondWithResult(HttpStatusCode.Created, result.toManagedUser(), successMessage)
    }

    post("/login") {
        val params = call.receiveParameters()
        val userName = params["username"]?.trim().toString()
        val password = params["password"]?.trim().toString()

        val (language, countryCode) = extractLocalizationHeader()

        val token = gateway.loginUser(
            userName,
            password,
            tokenConfiguration,
            Locale(language, countryCode)
        )
        respondWithResult(HttpStatusCode.Created, token)
    }


    authenticate("auth-jwt") {
        get("/me") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val id = tokenClaim?.payload?.getClaim("userId").toString()
            respondWithResult(HttpStatusCode.OK, id)
        }
    }

    authenticate("refresh-jwt") {
        post("/refresh-access-token") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val userId = tokenClaim?.payload?.getClaim("userId").toString()
            val userPermissions = tokenClaim?.payload?.getClaim("role")?.asList(Int::class.java) ?: emptyList()
            val token = gateway.generateUserTokens(userId, userPermissions, tokenConfiguration)
            respondWithResult(HttpStatusCode.Created, token)
        }
    }
}

