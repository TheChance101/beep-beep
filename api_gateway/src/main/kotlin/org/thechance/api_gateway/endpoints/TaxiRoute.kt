package org.thechance.api_gateway.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.Taxi
import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.Locale

fun Route.taxiRoutes() {
    val taxiGateway: ITaxiGateway by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    route("/taxi") {

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get {
                val (language, countryCode) = extractLocalizationHeader()
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 20
                val local = Locale(language, countryCode)
                val result = taxiGateway.getAllTaxi(local, page, limit)

                respondWithResult(HttpStatusCode.OK, result,)
            }
            post {
                val taxi = call.receive<Taxi>()

                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)
                val result = taxiGateway.createTaxi(taxi, locale)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiCreatedSuccessfully

                respondWithResult(HttpStatusCode.Created, result, successMessage)
            }

            put("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val taxi = call.receive<Taxi>()

                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)
                val result = taxiGateway.editTaxi(id, taxi, locale)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiUpdateSuccessfully
                respondWithResult(HttpStatusCode.OK, result, successMessage)
            }

            get("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val (language, countryCode) = extractLocalizationHeader()
                val local = Locale(language, countryCode)
                val result = taxiGateway.getTaxiById(id, local)

                respondWithResult(HttpStatusCode.OK, result)
            }


            delete("/{taxiId}") {
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val local = Locale(language, countryCode)
                val id = params["taxiId"] ?: ""
                val result = taxiGateway.deleteTaxi(id, local)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(local).taxiDeleteSuccessfully
                respondWithResult(HttpStatusCode.OK, result,successMessage)
            }
        }
    }
}