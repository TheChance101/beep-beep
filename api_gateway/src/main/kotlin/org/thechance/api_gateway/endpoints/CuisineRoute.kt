package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toCuisine
import org.thechance.api_gateway.endpoints.gateway.IDashboardGetaway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Constants.ROLE
import java.util.*


fun Route.cuisineRoute() {

    val dashboardGetaway: IDashboardGetaway by inject()

    route("/cuisine") {
        authenticate("auth-jwt") {
            post {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim(ROLE)?.asList(Int::class.java) ?: emptyList()

                val params = call.receiveParameters()
                val name = params["name"]?.trim().toString()
                val (language, countryCode) = extractLocalizationHeader()

               val cuisine=  dashboardGetaway.addCuisine(
                    name = name, permissions = permissions, locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.Created, cuisine )
            }
        }


    }
    route("/cuisines") {
        get {

            val (language, countryCode) = extractLocalizationHeader()
            val cuisines = dashboardGetaway.getCuisines( locale = Locale(language, countryCode))

            respondWithResult(HttpStatusCode.OK, cuisines.toCuisine())
        }
    }
}

