package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IDashboardGetaway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Constants.USER_ID
import java.util.*


fun Route.cuisineRoute() {

    val dashboardGetaway: IDashboardGetaway by inject()

    route("/cuisine") {
//        authenticate("auth-jwt") {
            post {
//                val tokenClaim = call.principal<JWTPrincipal>()
//                val userId = tokenClaim?.payload?.getClaim(USER_ID).toString()
//                val permissions = tokenClaim?.payload?.getClaim(USER_ID).toString()

                val params = call.receiveParameters()
                val name = params["name"]?.trim().toString()
                val (language, countryCode) = extractLocalizationHeader()

               val cuisine=  dashboardGetaway.addCuisine(
                    name = name, id = "userId", permissions = listOf(""), locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.Created, cuisine, "Sccess")

            }
//        }

    }
}

