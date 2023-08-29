package org.thechance.api_gateway.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.mappers.toTaxi

import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.Locale

fun Route.taxiRoutes() {
    val taxiGateway: ITaxiGateway by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()
    route("/taxi") {

        authenticate("auth-jwt") {
            get {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions =
                    tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 20
                val local = Locale(language, countryCode)
                val result = taxiGateway.getAllTaxi(permissions, local, page, limit)

                respondWithResult(HttpStatusCode.OK, result.map { it.toTaxi() },)
            }
            post {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions =
                    tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                val params = call.receiveParameters()
                val plateNumber = params["plateNumber"] ?: ""
                val color = params["color"]?.toInt() ?: 0
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
                    isAvailable ,
                    permissions,
                    locale
                )
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiCreatedSuccessfully

                respondWithResult(HttpStatusCode.Created, result.toTaxi(), successMessage)
            }

            put("/{taxiId}") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions =
                    tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                val id = call.parameters["taxiId"] ?: ""
                val params = call.receiveParameters()

                val plateNumber = params["plateNumber"] ?: ""
                val color = params["color"]?.toInt() ?: 0
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
                    permissions,
                    locale
                )
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(locale).taxiUpdateSuccessfully
                respondWithResult(HttpStatusCode.OK, result.toTaxi(), successMessage)
            }

            get("/{taxiId}"){
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions =
                    tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                val id = call.parameters["taxiId"] ?: ""
                val (language, countryCode) = extractLocalizationHeader()
                val local = Locale(language, countryCode)
                val result = taxiGateway.getTaxiById(id, permissions, local)

                respondWithResult(HttpStatusCode.OK, result.toTaxi())
            }


            delete("/{taxiId}"){
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions =
                    tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val local = Locale(language, countryCode)
                val id = params["taxiId"] ?: ""
                val result = taxiGateway.deleteTaxi(id, permissions, Locale(language, countryCode))
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(local).taxiDeleteSuccessfully
                respondWithResult(HttpStatusCode.OK, result.toTaxi(),successMessage)
            }
        }
    }
}