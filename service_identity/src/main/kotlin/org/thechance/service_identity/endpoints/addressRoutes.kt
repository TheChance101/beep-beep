package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.collection.CreateAddressDocument
import org.thechance.service_identity.data.collection.UpdateAddressDocument
import org.thechance.service_identity.data.mappers.toCreateRequest
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toUpdateRequest
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserAddressManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER

fun Route.addressRoutes() {

    val manageUserAddress: IUserAddressManagementUseCase by inject()

    route("users/address") {
        post("{userId}") {
            val address = call.receive<CreateAddressDocument>()
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            call.respond(HttpStatusCode.Created, manageUserAddress.addAddress(userId, address.toCreateRequest()))
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
            val address = call.receive<UpdateAddressDocument>()
            call.respond(HttpStatusCode.OK, manageUserAddress.updateAddress(id, address.toUpdateRequest()))
        }

        get("/all/{userId}") {
            val id = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userAddresses = manageUserAddress.getUserAddresses(id)
            call.respond(HttpStatusCode.OK, userAddresses.map { it.toDto() })
        }
    }

}