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
import org.thechance.service_identity.domain.usecases.user_address.IManageUserAddressUseCase
import org.thechance.service_identity.domain.usecases.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.model.CreateAddressRequest
import org.thechance.service_identity.endpoints.model.UpdateAddressRequest

fun Route.addressRoutes() {

    val manageUserAddress: IManageUserAddressUseCase by inject()

    route("users/address") {
        post("{userId}") {
            val address = call.receive<CreateAddressRequest>()
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.Created, manageUserAddress.addAddress(userId, address.toEntity()))
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.OK, manageUserAddress.getAddress(id).toDto())
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.OK, manageUserAddress.deleteAddress(id))
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val address = call.receive<UpdateAddressRequest>()
            call.respond(HttpStatusCode.OK, manageUserAddress.updateAddress(id, address.toEntity()))
        }

        get("/all/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userAddresses = manageUserAddress.getUserAddresses(id)
            call.respond(HttpStatusCode.OK, userAddresses.map { it.toDto() })
        }
    }

}