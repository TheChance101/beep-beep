package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.restaurant.CuisineDto
import org.thechance.api_gateway.data.service.ImageService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Role

fun Route.offerRoute() {

    val restaurantService: RestaurantService by inject()

    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    get("/offers") {
        val language = extractLocalizationHeader()
//        val cuisine = restaurantService.addCuisine(cuisineDto, language)
//        respondWithResult(HttpStatusCode.Created, cuisine)
    }

    route("/offer") {
        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            post {
                val language = extractLocalizationHeader()
                val params = call.receiveParameters()
                val offerTitle = params["offerTitle"]?.trim().toString()
                val offer = restaurantService.addOffer(offerTitle, language)
                respondWithResult(HttpStatusCode.Created, offer)
            }

            delete("/{id}") {
//                val language = extractLocalizationHeader()
//                val cuisineId = call.parameters["id"]?.trim().toString()
//                val result = restaurantService.deleteCuisine(cuisineId = cuisineId, languageCode = language)
//                val successMessage =
//                    localizedMessagesFactory.createLocalizedMessages(language).deletedSuccessfully
//                respondWithResult(HttpStatusCode.OK, result, message = successMessage)
            }
        }
    }

}
