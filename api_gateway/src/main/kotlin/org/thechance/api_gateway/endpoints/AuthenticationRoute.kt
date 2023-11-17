package org.thechance.api_gateway.endpoints

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.authenticate.TokenConfiguration
import org.thechance.api_gateway.data.model.identity.UserRegistrationDto
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.NotificationService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractApplicationIdHeader
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.authenticationRoutes(tokenConfiguration: TokenConfiguration) {
    val identityService: IdentityService by inject()
    val notificationService: NotificationService by inject()

    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    post("/signup") {
        val newUser = call.receive<UserRegistrationDto>()
        val language = extractLocalizationHeader()

        val result = identityService.createUser(newUser = newUser, languageCode = language)
        val successMessage = localizedMessagesFactory.createLocalizedMessages(language).userCreatedSuccessfully
        respondWithResult(HttpStatusCode.Created, result, successMessage)
    }

    post("/login") {
        val params = call.receiveParameters()
        val userName = params["username"]?.trim().toString()
        val password = params["password"]?.trim().toString()
        val deviceToken = params["token"]?.trim().orEmpty()

        val language = extractLocalizationHeader()
        val appId = extractApplicationIdHeader()
        val token = identityService.loginUser(userName, password, tokenConfiguration, language, appId)
        try {
            if (deviceToken.isNotEmpty()) {
                val jwt: DecodedJWT = JWT.decode(token.accessToken)
                val userId = jwt.getClaim(Claim.USER_ID).asString()
                notificationService.saveToken(userId, deviceToken, language)
            }
        } catch (e: Exception) {
        }

        respondWithResult(HttpStatusCode.OK, token)
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
            val token = identityService.generateUserTokens(userId, username, userPermission, tokenConfiguration)
            respondWithResult(HttpStatusCode.Created, token)
        }
    }
}