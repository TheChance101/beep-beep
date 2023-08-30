package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.mappers.toTaxi
import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.taxiRoutes() {
    val taxiGateway: ITaxiGateway by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    route("/taxi") {

        authenticateWithRole(Role.TAXI_DRIVER) {
            get {
                val (language, countryCode) = extractLocalizationHeader()
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 20
                val local = Locale(language, countryCode)
                val result = taxiGateway.getAllTaxi(local, page, limit)

                respondWithResult(HttpStatusCode.OK, result.map { it.toTaxi() })
            }
            post {
                val params = call.receiveParameters()
                val plateNumber = params["plateNumber"] ?: ""
                val color = params["color"]?.toLong() ?: 0
                val type = params["type"] ?: ""
                val driverId = params["driverId"] ?: ""
                val seats = params["seats"]?.toInt() ?: 0
                val isAvailable = params["isAvailable"]?.toBoolean() ?: true


                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)

                val result = taxiGateway.createTaxi(
                    plateNumber,
                    color,
                    type,
                    driverId,
                    seats,
                    isAvailable,
                    locale
                )
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiCreatedSuccessfully

                respondWithResult(HttpStatusCode.Created, result.toTaxi(), successMessage)
            }

            put("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val params = call.receiveParameters()

                val plateNumber = params["plateNumber"] ?: ""
                val color = params["color"]?.toLong() ?: 0
                val type = params["type"] ?: ""
                val driverId = params["driverId"] ?: ""
                val seats = params["seats"]?.toInt() ?: 0
                val isAvailable = params["isAvailable"]?.toBoolean() ?: true

                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)
                val result = taxiGateway.editTaxi(
                    id,
                    plateNumber,
                    color,
                    type,
                    driverId,
                    seats,
                    isAvailable,
                    locale
                )
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiUpdateSuccessfully
                respondWithResult(HttpStatusCode.OK, result.toTaxi(), successMessage)
            }

            get("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val (language, countryCode) = extractLocalizationHeader()
                val local = Locale(language, countryCode)
                val result = taxiGateway.getTaxiById(id, local)

                respondWithResult(HttpStatusCode.OK, result.toTaxi())
            }


            delete("/{taxiId}") {
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val local = Locale(language, countryCode)
                val id = params["taxiId"] ?: ""
                val result = taxiGateway.deleteTaxi(id, Locale(language, countryCode))
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(local).taxiDeleteSuccessfully
                respondWithResult(HttpStatusCode.OK, result.toTaxi(), successMessage)
            }
        }
    }
}