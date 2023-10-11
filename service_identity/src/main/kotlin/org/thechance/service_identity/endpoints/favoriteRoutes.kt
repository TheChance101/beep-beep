package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.domain.usecases.IUserFavoriteUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.domain.util.MissingParameterException


fun Route.favoriteRoutes() {
    val favorite: IUserFavoriteUseCase by inject()

    route("user/{userId}/favorite") {
        get {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val restaurants = favorite.getFavoriteRestaurants(userId)
            call.respond(HttpStatusCode.OK, restaurants)
        }

        post {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val restaurantId = call.parameters["restaurantId"].orEmpty().trim()
            val restaurant = favorite.addRestaurantsToFavorite(userId = userId, restaurantId = restaurantId)
            call.respond(HttpStatusCode.OK, restaurant)
        }

        delete {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val restaurantId = call.parameters["restaurantId"].orEmpty().trim()
            val restaurant = favorite.removeRestaurantsFromFavorite(userId = userId, restaurantId = restaurantId)
            call.respond(HttpStatusCode.OK, restaurant)
        }
    }
}
