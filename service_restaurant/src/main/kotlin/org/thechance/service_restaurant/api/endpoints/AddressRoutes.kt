package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.usecase.address.AddressUseCasesContainer


fun Route.addressRoutes() {
    val addressUseCasesContainer: AddressUseCasesContainer by inject()

    route("/address") {

        get {
            val addresses = addressUseCasesContainer.getAddresses().toDto()
            call.respond(HttpStatusCode.OK, addresses)
        }

        get("/{id}") {
            val addressId = call.parameters["id"] ?: ""
            val address = addressUseCasesContainer.getAddressDetails(addressId).toDto()
            call.respond(HttpStatusCode.OK, address)
        }

        post {
            val addressDto = call.receive<AddressDto>()
            val result = addressUseCasesContainer.createAddress(addressDto.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val addressId = call.parameters["id"] ?: ""
            val addressDto = call.receive<AddressDto>()
            val result = addressUseCasesContainer.updateAddress(addressDto.toEntity().copy(id = addressId))
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val addressId = call.parameters["id"] ?: ""
            val result = addressUseCasesContainer.deleteAddress(addressId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}