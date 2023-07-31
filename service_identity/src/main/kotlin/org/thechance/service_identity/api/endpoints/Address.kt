package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.domain.usecase.address.*

fun Route.addressRoutes() {

    val addressUseCaseContainer: AddressUseCaseContainer by inject()

    get("/address/{id}") {
        val id = call.parameters["id"].orEmpty()
        call.respond(HttpStatusCode.OK, addressUseCaseContainer.getAddressUseCase.invoke(id).toAddressDto())
    }

    post("/address") {
        val address = call.receive<AddressDto>()
        call.respond(HttpStatusCode.Created, addressUseCaseContainer.createAddressUseCase(address.toAddress()))
    }

    delete("/address/{id}") {
        val id = call.parameters["id"].orEmpty()
        call.respond(HttpStatusCode.OK, addressUseCaseContainer.deleteAddressUseCase(id))
    }

    put("/address/{id}") {
        val id = call.parameters["id"].orEmpty()
        val address = call.receive<AddressDto>()
        call.respond(HttpStatusCode.Created, addressUseCaseContainer.updateAddressUseCase(id, address.toAddress()))
    }

    get("/addresses/{userId}") {
        val id = call.parameters["userId"].orEmpty()
        val userAddresses = addressUseCaseContainer.getUserAddressesUseCase.invoke(id)
        call.respond(HttpStatusCode.OK, userAddresses.map { it.toAddressDto() })
    }

}