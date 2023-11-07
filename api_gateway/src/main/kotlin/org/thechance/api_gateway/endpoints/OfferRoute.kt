package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.offer.OfferDto
import org.thechance.api_gateway.data.model.restaurant.RestaurantDto
import org.thechance.api_gateway.data.service.ImageService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Role

fun Route.offerRoute() {

    val restaurantService: RestaurantService by inject()
    val imageValidator: ImageValidator by inject()
    val imageService: ImageService by inject()

    route("/offers") {
        get {
            val language = extractLocalizationHeader()
            val offers = restaurantService.getOffers(language)
            respondWithResult(HttpStatusCode.Created, offers)
        }


        get("/restaurants") {
            val language = extractLocalizationHeader()
            val offers = restaurantService.getOffersWithRestaurants(language)
            respondWithResult(HttpStatusCode.Created, offers)
        }
    }

    route("/offer") {
        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            post {
                val language = extractLocalizationHeader()
                val multipartDto = receiveMultipart<OfferDto>(imageValidator)
                val imageUrl =
                    multipartDto.image?.let { image ->
                        imageService.uploadImage(image, multipartDto.data.name)
                    }
                val offerDto = multipartDto.data.copy(image = imageUrl)
                val offer = restaurantService.addOffer(
                    offerDto, language
                )
                respondWithResult(HttpStatusCode.Created, offer)
            }

            post("/{offerId}/restaurants") {
                val language = extractLocalizationHeader()
                val offerId = call.parameters["offerId"]?.trim().toString()
                val restaurants = call.receive<List<String>>()
                val offer = restaurantService.addRestaurantsToOffer(restaurants, offerId, language)
                respondWithResult(HttpStatusCode.Created, offer)
            }

        }
    }

}
