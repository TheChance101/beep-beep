package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserAccountManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.model.UserTokenDto

fun Route.userRoutes() {


    val manageUserAccount: IUserAccountManagementUseCase by inject()

    route("/user") {

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val params = call.receiveParameters()
            val fullName = params["fullName"]?.trim()
            val username = params["username"]?.trim()
            val password = params["password"]?.trim()
            val email = params["email"]?.trim()

            val result = manageUserAccount.createUser(
                fullName = fullName.toString(),
                username = username.toString(),
                password = password.toString(),
                email = email.toString()
            )
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val params = call.receiveParameters()
            val fullName = params["fullName"]?.trim()
            val username = params["username"]?.trim()
            val password = params["password"]?.trim()
            val email = params["email"]?.trim()

            val result = manageUserAccount.updateUser(
                id = id,
                fullName = fullName.toString(),
                username = username.toString(),
                password = password.toString(),
                email = email.toString()
            )
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.deleteUser(id)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/{id}/wallet/add") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.addToWallet(id, amount)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/{id}/wallet/subtract") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.subtractFromWallet(id, amount)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val username = parameters["username"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val password = parameters["password"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val isLoggedIn = manageUserAccount.login(username, password)
            call.respond(HttpStatusCode.OK, isLoggedIn)
        }

        post("/update-refresh-token") {
            val formParameters = call.receiveParameters()
            val userId = formParameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val refreshToken = formParameters["refreshToken"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val expiresIn = formParameters["expirationDate"]?.toLong() ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val isSavedTokens = manageUserAccount.updateRefreshToken(userId, refreshToken, expiresIn)
            call.respond(HttpStatusCode.OK, isSavedTokens)
        }

        post("/get-user-id-by-refresh-token") {
            val formParameters = call.receiveParameters()
            val refreshToken = formParameters["refreshToken"]
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userId = manageUserAccount.getUserIdByRefreshToken(refreshToken)
            call.respond(HttpStatusCode.OK, userId)
        }

        post("/validate-refresh-token") {
            val formParameters = call.receiveParameters()
            val refreshToken = formParameters["refreshToken"]
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val isValid = manageUserAccount.validateRefreshToken(refreshToken)
            call.respond(HttpStatusCode.OK, isValid)
        }

        get("/get-user") {
            val username = call.parameters["username"]
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUserByUsername(username)
            call.respond(HttpStatusCode.OK, user.toDto())
        }
    }
}