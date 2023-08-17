package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toDto
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.domain.usecase.IUserAccountManagementUseCase
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import java.util.*

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
fun Route.userRoutes(tokenConfiguration: TokenConfiguration) {

    val userAccountManagementUseCase: IUserAccountManagementUseCase by inject()

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
        call.respond(HttpStatusCode.Created, result)
    }

    post("/login") {
        val params = call.receiveParameters()
        val email = params["username"]?.trim().toString()
        val password = params["password"]?.trim().toString()

        val (language, countryCode) = extractLocalizationHeader()

        val token = userAccountManagementUseCase.loginUser(
            email,
            password,
            tokenConfiguration,
            locale = Locale(language, countryCode)
        )

        call.respond(HttpStatusCode.Created, token.toDto())
    }


    post("/refresh-access-token") {
        val params = call.receiveParameters()
        val refreshToken = params["refreshToken"]?.trim().toString()
        val token = userAccountManagementUseCase.refreshAccessToken(refreshToken, tokenConfiguration)
        call.respond(HttpStatusCode.Created, token)
    }

    authenticate("auth-jwt") {
        get("/me") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val id = tokenClaim?.payload?.getClaim("userId").toString()
            call.respond(HttpStatusCode.OK, id)
        }
    }

//        get("/{id}") {
//            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
//            val user = userAccountManagementUseCase.getUser(id)
//            call.respond(HttpStatusCode.OK, user)
//        }


}