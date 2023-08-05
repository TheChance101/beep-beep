package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.WalletDto
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.useraccount.WalletManagementUseCase
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.walletRoute() {

    val walletManagement: WalletManagementUseCase by inject()


    route("/wallet") {
        get("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = walletManagement.getWallet(id).toDto()
                call.respond(wallet)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post {
            try {
                val wallet = call.receive<WalletDto>()
                val result = walletManagement.createWallet(wallet.toEntity())
                call.respond(result)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/{id}") {
            try {
                val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
                val wallet = call.receive<WalletDto>()
                val result = walletManagement.updateWallet(id, wallet.toEntity())
                call.respond(result)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

    }

}