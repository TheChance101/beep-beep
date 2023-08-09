package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.useraccount.UserAccountUseCase
import org.thechance.service_identity.endpoints.model.CreateAddressRequest
import org.thechance.service_identity.endpoints.model.UpdateAddressRequest
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.addressRoutes() {

    val userAccountUseCase: UserAccountUseCase by inject()

    route("/address") {

        post("{userId}") {
            val address = call.receive<CreateAddressRequest>()
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.Created, userAccountUseCase.addAddress(userId, address.toEntity()))
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.OK, userAccountUseCase.getAddress(id).toDto())
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.OK, userAccountUseCase.deleteAddress(id))
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val address = call.receive<UpdateAddressRequest>()
            call.respond(HttpStatusCode.OK, userAccountUseCase.updateAddress(id, address.toEntity()))
        }

    }

    get("/addresses/{userId}") {
        val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
        val userAddresses = userAccountUseCase.getUserAddresses(id)
        call.respond(HttpStatusCode.OK, userAddresses.map { it.toDto() })
    }

}