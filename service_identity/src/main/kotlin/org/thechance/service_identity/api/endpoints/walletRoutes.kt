package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.domain.usecases.wallet.AddWalletUseCaseImpl
import org.thechance.service_identity.domain.usecases.wallet.DeleteWalletUseCaseImpl
import org.thechance.service_identity.domain.usecases.wallet.GetWalletUseCaseImpl
import org.thechance.service_identity.domain.usecases.wallet.UpdateWalletUseCaseImpl
import org.thechance.service_identity.entity.Wallet

fun Route.walletRoute(){

    val addWallet : AddWalletUseCaseImpl by inject()
    val getWallet : GetWalletUseCaseImpl by inject()
    val deleteWallet : DeleteWalletUseCaseImpl by inject()
    val updateWallet : UpdateWalletUseCaseImpl by inject()
    val getUserWallet : GetWalletUseCaseImpl by inject()

    route("/wallet"){
        get("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = getWallet.invoke(id)
                call.respond(wallet)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get("/user/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = getUserWallet.invoke(id)
                call.respond(wallet)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post{
            try {
                val wallet = call.receive<Wallet>()
                val result = addWallet.invoke(wallet)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val wallet = call.receive<Wallet>()
                val result = updateWallet.invoke(id,wallet)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
        delete("/{id}") {
            try {
                val id = call.parameters["id"]!!
                val result = deleteWallet.invoke(id)
                call.respond(result)
            }catch (e: Exception){
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}