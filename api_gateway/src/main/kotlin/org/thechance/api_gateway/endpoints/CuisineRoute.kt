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
import org.thechance.api_gateway.endpoints.utils.ImageValidator
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.receiveMultipart
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role


fun Route.cuisineRoute() {

    val restaurantService: RestaurantService by inject()
    val imageValidator: ImageValidator by inject()
    val imageService: ImageService by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()


    route("/cuisine") {
        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            post {
                val language = extractLocalizationHeader()
                val multipartDto = receiveMultipart<CuisineDto>(imageValidator)
                val image = multipartDto.image?.let { image -> imageService.uploadImage(image) }
                val cuisineDto = multipartDto.data.copy(image = image?.data?.link)
                val cuisine =  restaurantService.addCuisine(cuisineDto, language)
                respondWithResult(HttpStatusCode.Created, cuisine)
            }

            delete("/{id}") {
                val language = extractLocalizationHeader()
                val cuisineId =call.parameters["id"]?.trim().toString()
                val result= restaurantService.deleteCuisine(cuisineId = cuisineId, languageCode = language)
                val successMessage =
                    localizedMessagesFactory.createLocalizedMessages(language).deletedSuccessfully
                respondWithResult(HttpStatusCode.OK,result, message=successMessage)
            }
        }

        get("/{id}/meals") {
            val language = extractLocalizationHeader()
            val cuisineId = call.parameters["id"]?.trim().toString()
            val meals = restaurantService.getMealsByCuisineId(
                cuisineId = cuisineId,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, meals)
        }

    }

    get("/cuisines") {
        val language = extractLocalizationHeader()
        val cuisines = restaurantService.getCuisines(languageCode = language)
        respondWithResult(HttpStatusCode.OK, cuisines)
    }
}

