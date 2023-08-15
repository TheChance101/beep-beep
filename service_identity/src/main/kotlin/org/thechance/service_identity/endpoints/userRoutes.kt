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
import org.thechance.service_identity.endpoints.model.UserDto
import org.thechance.service_identity.endpoints.model.UserTokenDto

fun Route.userRoutes() {


    val manageUserAccount: IUserAccountManagementUseCase by inject()

    route("/users") {

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val userDto = call.receive<UserDto>()
            val result = manageUserAccount.createUser(
                userDto.fullName ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER),
                userDto.username  ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER),
                userDto.password  ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER),
                userDto.email ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            )
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userDto = call.receive<UserDto>()
            val result = manageUserAccount.updateUser(id, userDto.fullName, userDto.username, userDto.password, userDto.email)
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

        post("/save-token") {
            val userTokenDto = call.receive<UserTokenDto>()
            val isSavedTokens = manageUserAccount.saveUserTokens(
                userTokenDto.userId,
                userTokenDto.accessToken,
                userTokenDto.refreshToken,
                userTokenDto.expiresIn
            )
            call.respond(HttpStatusCode.OK, isSavedTokens)
        }
    }
}