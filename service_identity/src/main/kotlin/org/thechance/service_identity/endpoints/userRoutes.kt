package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.domain.usecases.IManageWalletUseCase
import org.thechance.service_identity.endpoints.model.mapper.toDto
import org.thechance.service_identity.domain.util.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserAccountManagementUseCase
import org.thechance.service_identity.domain.usecases.IUserFavoriteUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.model.UserRegistrationDto
import org.thechance.service_identity.endpoints.model.mapper.toEntity
import org.thechance.service_identity.endpoints.util.extractApplicationIdHeader
import org.thechance.service_identity.endpoints.util.extractInt

fun Route.userRoutes() {

    val manageUserAccount: IUserAccountManagementUseCase by inject()
    val manageWallet: IManageWalletUseCase by inject()


    route("/user") {

        get("/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }

        get("/get-user") {
            val username = call.parameters["username"]
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUserByUsername(username)
            call.respond(HttpStatusCode.OK, user.toDto())
        }

        post {
            val newUser = call.receive<UserRegistrationDto>()
            val result = manageUserAccount.createUser(password = newUser.password, user = newUser.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        put("/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val params = call.receiveParameters()
            val fullName = params["fullName"]?.trim()
            val email = params["email"]?.trim()

            val result = manageUserAccount.updateUser(
                id = id, fullName = fullName.toString(), email = email.toString()
            )
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post("/{id}/wallet/add") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageWallet.addToWallet(id, amount).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        post("/{id}/wallet/subtract") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageWallet.subtractFromWallet(id, amount).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        post("/login") {
            val username = call.parameters["username"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val password = call.parameters["password"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val applicationId = extractApplicationIdHeader()
            val isLoggedIn = manageUserAccount.login(username, password, applicationId)
            call.respond(HttpStatusCode.OK, isLoggedIn)
        }

        delete("/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.deleteUser(id)
            call.respond(HttpStatusCode.OK, result)
        }
    }

}