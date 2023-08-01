package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.usecases.user.UserUseCaseContainer

fun Route.userRoutes() {

    val userUseCaseContainer: UserUseCaseContainer by inject()

    route("/user"){

        get("/{id}") {
            val id = call.parameters["id"]!!
            call.respond(HttpStatusCode.OK, userUseCaseContainer.getUserByIdUseCase.invoke(id))
        }

        post{
            try {
                val user = call.receive<User>()
                val result = userUseCaseContainer.createUserUseCase.invoke(user)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]!!
            val userDto = call.receive<UserDto>()
            call.respond(HttpStatusCode.Created, userUseCaseContainer.updateUserUseCase.invoke(id, userDto.toUser()))
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!
            call.respond(HttpStatusCode.OK, userUseCaseContainer.deleteUserUseCase.invoke(id))

        }
    }

    get("/users") {
        val users = userUseCaseContainer.getUsersUseCase.invoke()
        call.respond(HttpStatusCode.OK, users.map { it.toUserDto() })
    }

}