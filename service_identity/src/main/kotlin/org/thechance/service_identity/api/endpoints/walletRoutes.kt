package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.ServerResponse
import org.thechance.service_identity.domain.usecases.wallet.WalletUseCaseContainer
import org.thechance.service_identity.domain.entity.Wallet

fun Route.walletRoute(){

    val walletUseCaseContainer : WalletUseCaseContainer by inject()


    route("/wallet"){
        get("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = walletUseCaseContainer.getWalletUseCase.invoke(id).toWalletDto()
                val response = ServerResponse.success(result=wallet,code= HttpStatusCode.OK.value)
                call.respond(response)
            }catch (e: Exception){
                val errorMessage = e.message ?: "Id Not Found"
                val errorResponse = ServerResponse.error(
                    errorMessage=errorMessage,
                    code= HttpStatusCode.NotFound.value)
                call.respond(HttpStatusCode.NotFound, errorResponse)
            }
        }
        get("/user/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = walletUseCaseContainer.getUserWalletUseCase.invoke(id).toWalletDto()
                val response = ServerResponse.success(result=wallet,code= HttpStatusCode.OK.value)
                call.respond(response)
            }catch (e: Exception){
                val errorMessage = e.message ?: "Id Not Found"
                val errorResponse = ServerResponse.error(
                    errorMessage=errorMessage,
                    code= HttpStatusCode.NotFound.value)
                call.respond(HttpStatusCode.NotFound, errorResponse)
            }
        }
        post{
            try {
                val wallet = call.receive<Wallet>()
                val result = walletUseCaseContainer.addWalletUseCase.invoke(wallet)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = call.receive<Wallet>()
                val result = walletUseCaseContainer.updateWalletUseCase.invoke(id,wallet)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
        delete("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val result = walletUseCaseContainer.deleteWalletUseCase.invoke(id)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}