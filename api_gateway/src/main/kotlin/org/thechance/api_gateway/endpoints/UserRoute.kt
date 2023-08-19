package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.gateway.IResourcesGateway
import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
fun Route.userRoutes(tokenConfiguration: TokenConfiguration) {

    val userAccountManagementUseCase: IApiGateway by inject()
    val resourcesGateway: IResourcesGateway by inject()

    post("/signup") {
        val params = call.receiveParameters()
        val fullName = params["fullName"]?.trim()
        val username = params["username"]?.trim()
        val password = params["password"]?.trim()
        val email = params["email"]?.trim()

        val (language, countryCode) = extractLocalizationHeader()

        val result = userAccountManagementUseCase.createUser(
            fullName = fullName.toString(),
            username = username.toString(),
            password = password.toString(),
            email = email.toString(),
            locale = Locale(language, countryCode)
        )
        val locale = Locale(language, countryCode)
        val message = resourcesGateway.getLocalizedResponseMessage(code = 1076, locale = locale)

        respondWithResult(HttpStatusCode.Created, result,message)
    }

    post("/login") {
        val params = call.receiveParameters()
        val userName = params["username"]?.trim().toString()
        val password = params["password"]?.trim().toString()

        val (language, countryCode) = extractLocalizationHeader()

        val token = userAccountManagementUseCase.loginUser(
            userName,
            password,
            tokenConfiguration,
            Locale(language, countryCode)
        )
        respondWithResult(HttpStatusCode.Created, token)
    }

    post("/refresh-access-token") {
        val params = call.receiveParameters()
        val refreshToken = params["refreshToken"]?.trim().toString()

        val (language, countryCode) = extractLocalizationHeader()
        val locale = Locale(language, countryCode)
        val token = userAccountManagementUseCase.refreshAccessToken(refreshToken, tokenConfiguration, locale)

        respondWithResult(HttpStatusCode.Created, token)
    }

    authenticate("auth-jwt") {
        get("/me") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val id = tokenClaim?.payload?.getClaim("userId").toString()
            respondWithResult(HttpStatusCode.OK, id)
        }
    }

}

