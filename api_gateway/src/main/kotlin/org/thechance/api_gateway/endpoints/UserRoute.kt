package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.domain.usecase.IUserAccountManagementUseCase
import org.thechance.api_gateway.util.INVALID_REQUEST_PARAMETER
import org.thechance.api_gateway.util.MissingParameterException

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
fun Route.userRoutes() {

    val userAccountManagementUseCase: IUserAccountManagementUseCase by inject()

    route("/user") {

        post("/signup") {
            val params = call.receiveParameters()
            val fullName = params["fullName"]?.trim()
            val username = params["username"]?.trim()
            val password = params["password"]?.trim()
            val email = params["email"]?.trim()

            val result = userAccountManagementUseCase.createUser(
                fullName = fullName.toString(),
                username = username.toString(),
                password = password.toString(),
                email = email.toString()
            )
            call.respond(HttpStatusCode.Created, result)
        }

        post("/login") {
            val params = call.receiveParameters()
            val email = params["username"]?.trim().toString()
            val password = params["password"]?.trim().toString()

            val token = userAccountManagementUseCase.loginUser(email, password)
            call.respond(HttpStatusCode.Created, token)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = userAccountManagementUseCase.getUser(id)
            call.respond(HttpStatusCode.OK, user)
        }


    }
}