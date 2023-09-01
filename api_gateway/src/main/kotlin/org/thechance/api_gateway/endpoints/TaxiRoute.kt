package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.taxi.TaxiDto
import org.thechance.api_gateway.data.service.TaxiService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.taxiRoutes() {
    val taxiService: TaxiService by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    route("/taxi") {

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get {
                val (language, countryCode) = extractLocalizationHeader()
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 20
                val local = Locale(language, countryCode)
                val result = taxiService.getAllTaxi(local, page, limit)

                respondWithResult(HttpStatusCode.OK, result,)
            }
            post {
                val taxiDto = call.receive<TaxiDto>()

                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)
                val result = taxiService.createTaxi(taxiDto, locale)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiCreatedSuccessfully

                respondWithResult(HttpStatusCode.Created, result, successMessage)
            }

            put("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val taxiDto = call.receive<TaxiDto>()

                val (language, countryCode) = extractLocalizationHeader()
                val locale = Locale(language, countryCode)
                val result = taxiService.editTaxi(id, taxiDto, locale)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiUpdateSuccessfully
                respondWithResult(HttpStatusCode.OK, result, successMessage)
            }

            get("/{taxiId}") {
                val id = call.parameters["taxiId"] ?: ""
                val (language, countryCode) = extractLocalizationHeader()
                val local = Locale(language, countryCode)
                val result = taxiService.getTaxiById(id, local)

                respondWithResult(HttpStatusCode.OK, result)
            }


            delete("/{taxiId}") {
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val local = Locale(language, countryCode)
                val id = params["taxiId"] ?: ""
                val result = taxiService.deleteTaxi(id, local)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(local).taxiDeleteSuccessfully
                respondWithResult(HttpStatusCode.OK, result,successMessage)
            }
        }
    }
}