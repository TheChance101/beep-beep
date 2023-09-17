package org.thechance.api_gateway.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.service.NotificationService
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
fun Route.notificationRoute() {

    val notificationService: NotificationService by inject()

    route("/tokens"){
        get("/user/{id}"){
            val language = extractLocalizationHeader()
            val id = call.parameters["id"]?.trim().toString()
            val result= notificationService.getUserToken(id,language)
            respondWithResult(HttpStatusCode.OK, result)
        }
        get("/users"){
            val language = extractLocalizationHeader()
            val ids = call.parameters["ids"]?.split(",")?.map { it } ?: emptyList()
            val result= notificationService.getAllUsersTokens(ids,language)
            respondWithResult(HttpStatusCode.OK, result)
        }
    }
}