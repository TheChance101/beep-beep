package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.endpoints.model.mapper.toDto
import org.thechance.service_identity.domain.util.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserAccountManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.util.extractInt

fun Route.userRoutes() {

    val manageUserAccount: IUserAccountManagementUseCase by inject()

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
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        post("/{id}/wallet/add") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.addToWallet(id, amount).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        post("/{id}/wallet/subtract") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val amount = call.receiveParameters()["amount"]?.toDouble()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.subtractFromWallet(id, amount).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val username = parameters["username"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val password = parameters["password"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val isLoggedIn = manageUserAccount.login(username, password)
            call.respond(HttpStatusCode.OK, isLoggedIn)
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

        delete("/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.deleteUser(id)
            call.respond(HttpStatusCode.OK, result)
        }

    }

}