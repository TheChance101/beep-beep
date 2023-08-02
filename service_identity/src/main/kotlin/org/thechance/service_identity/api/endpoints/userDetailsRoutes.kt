package org.thechance.service_identity.api.endpoints


import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.toDto
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.toEntity
import org.thechance.service_identity.domain.usecases.user_details.UserDetailsUseCaseContainer

fun Route.userDetailsRoutes() {

    val useCaseContainer by inject<UserDetailsUseCaseContainer>()

    route("/user/details") {

        post {
            val userDetails = call.receive<UserDetailsCollection>()
            useCaseContainer.createUserDetails(userDetails.toEntity())
            call.respond("User details created successfully")
        }

        put {
            val userDetails = call.receive<UserDetailsCollection>()
            useCaseContainer.updateUserDetails(userDetails.toEntity())
            call.respond("User details updated successfully")
        }

        get("/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("Id is required")
            call.respond(useCaseContainer.getUserDetails(userId).toDto())
        }

        post("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val permissionId =
                call.receiveParameters()["permissionId"] ?: throw BadRequestException("Permission id is required")
            useCaseContainer.addPermissionToUser(userId, permissionId)
            call.respond("Permission added to user successfully")
        }

        delete("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val permissionId =
                call.receiveParameters()["permissionId"] ?: throw BadRequestException("Permission id is required")
            useCaseContainer.removePermissionFromUser(userId, permissionId)
            call.respond("Permission removed from user successfully")
        }

    }
}