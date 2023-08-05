package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.endpoints.model.AddressDto
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.usecases.useraccount.UserAccountUseCase
import org.thechance.service_identity.endpoints.validation.InvalidIDException
import org.thechance.service_identity.endpoints.validation.InvalidUserIDException

fun Route.addressRoutes() {

    val userAccountUseCase: UserAccountUseCase by inject()

    route("/address") {

        post {
            val address = call.receive<AddressDto>()
            call.respond(HttpStatusCode.Created, userAccountUseCase.addAddress(address.toEntity()))
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw InvalidIDException()
            call.respond(HttpStatusCode.OK, userAccountUseCase.getAddress(id).toDto())
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw InvalidIDException()
            call.respond(HttpStatusCode.OK, userAccountUseCase.deleteAddress(id))
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw InvalidIDException()
            val address = call.receive<AddressDto>()
            call.respond(HttpStatusCode.Created, userAccountUseCase.updateAddress(id, address.toEntity()))
        }

    }

    get("/addresses/{userId}") {
        val id = call.parameters["userId"] ?: throw InvalidUserIDException()
        val userAddresses = userAccountUseCase.getUserAddresses(id)
        call.respond(HttpStatusCode.OK, userAddresses.map { it.toDto() })
    }

}