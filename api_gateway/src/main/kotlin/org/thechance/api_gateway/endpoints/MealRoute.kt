package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.MealDto
import org.thechance.api_gateway.data.service.ImageService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Role

fun Route.mealRoute() {
    val restaurantService: RestaurantService by inject()
    val imageValidator: ImageValidator by inject()
    val imageService: ImageService by inject()

    route("/meal") {

        get("/{mealId}") {
            val language = extractLocalizationHeader()
            val mealId = call.parameters["mealId"]?.trim().toString()
            val meal = restaurantService.getMeal(mealId, language)
            respondWithResult(HttpStatusCode.OK, meal)
        }

        authenticateWithRole(Role.RESTAURANT_OWNER) {

            post {
                val language = extractLocalizationHeader()
                val multipartDto = receiveMultipart<MealDto>(imageValidator)
                val imageUrl =
                    multipartDto.image?.let { image ->
                        imageService.uploadImage(
                            image,
                            multipartDto.data.name ?: "null"
                        )
                    }
                val mealDto = multipartDto.data.copy(image = imageUrl)
                val createdMeal = mealDto.let { meal -> restaurantService.addMeal(meal, language) }
                respondWithResult(HttpStatusCode.Created, createdMeal)
            }

            put {
                val language = extractLocalizationHeader()
                val multipartDto = receiveMultipart<MealDto>(imageValidator)

                if (multipartDto.image != null) {
                    val imageUrl =
                        multipartDto.image.let { image -> imageService.uploadImage(image, multipartDto.data.name!!) }
                    val mealDto = multipartDto.data.copy(image = imageUrl)
                    val updatedMeal = mealDto.let { restaurantService.updateMeal(mealDto, language) }
                    respondWithResult(HttpStatusCode.OK, updatedMeal)
                } else {
                    val mealDto = multipartDto.data
                    val updatedMeal = mealDto.let { restaurantService.updateMeal(mealDto, language) }
                    respondWithResult(HttpStatusCode.OK, updatedMeal)
                }

            }
        }
    }

}