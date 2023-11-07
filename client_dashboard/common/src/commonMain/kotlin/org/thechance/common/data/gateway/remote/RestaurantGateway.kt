package org.thechance.common.data.gateway.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import kotlinx.serialization.json.Json
import org.thechance.common.data.gateway.remote.mapper.getPriceLevelOrNull
import org.thechance.common.data.gateway.remote.mapper.getRatingOrNull
import org.thechance.common.data.gateway.remote.mapper.toDto
import org.thechance.common.data.gateway.remote.mapper.toEntity
import org.thechance.common.data.gateway.remote.model.CuisineDto
import org.thechance.common.data.gateway.remote.model.LocationDto
import org.thechance.common.data.gateway.remote.model.OfferDto
import org.thechance.common.data.gateway.remote.model.RestaurantDto
import org.thechance.common.data.gateway.remote.model.RestaurantResponse
import org.thechance.common.data.gateway.remote.model.ServerResponse
import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Offer
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.RestaurantInformation
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway(private val client: HttpClient) : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: RestaurantInformation): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            post(urlString = "/restaurant") {
                setBody(restaurant.toDto())
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun deleteRestaurant(id: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>>(client) {
            delete(urlString = "/restaurant") { url { appendPathSegments(id) } }
        }.isSuccess ?: false
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>>(client) {
            get(urlString = "/cuisines")
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun getOffers(): List<Offer> {
        return tryToExecute<ServerResponse<List<OfferDto>>>(client) {
            get(urlString = "/offers")
        }.value?.toEntity() ?: throw UnknownError()    }

    override suspend fun createCuisine(cuisineName: String, image: ByteArray): Cuisine {
        val cuisineDto = Cuisine(name = cuisineName, image = "", id = "")
        return tryToExecute<ServerResponse<CuisineDto>>(client) {
            post(urlString = "/cuisine") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("data", Json.encodeToString(CuisineDto.serializer(), cuisineDto.toDto()))
                        append("image", image, Headers.build {
                                append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                append(HttpHeaders.ContentDisposition, "form-data; name=image; filename=image.png")
                            }
                        )
                    }
                ))
            }
        }.value?.toEntity() ?: throw UnknownError()
    }
    override suspend fun createOffer(offerName: String, image: ByteArray): Offer {
        val offerDto = Offer(name = offerName, image = "", id = "")
        return tryToExecute<ServerResponse<OfferDto>>(client) {
            post(urlString = "/offer") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("data", Json.encodeToString(OfferDto.serializer(), offerDto.toDto()))
                        append("image", image, Headers.build {
                                append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                append(HttpHeaders.ContentDisposition, "form-data; name=image; filename=image.png")
                            }
                        )
                    }
                ))
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun deleteCuisine(cuisineId: String) {
        tryToExecute<ServerResponse<Boolean>>(client) {
            delete(urlString = "/cuisine") { url { appendPathSegments(cuisineId) } }
        }.isSuccess ?: false
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
    ): DataWrapper<Restaurant> {
        val filteredRating = getRatingOrNull(rating)
        val filteredPriceLevel = getPriceLevelOrNull(priceLevel)

        val result = tryToExecute<ServerResponse<RestaurantResponse>>(client) {
            get(urlString = "/restaurants") {
                parameter("page", pageNumber)
                parameter("limit", numberOfRestaurantsInPage)
                parameter("query", restaurantName)
                parameter("rating", filteredRating)
                parameter("priceLevel", filteredPriceLevel)
            }
        }.value

        return paginateData(
            result?.restaurants?.toEntity() ?: emptyList(),
            numberOfRestaurantsInPage,
            result?.total ?: 0
        )
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            get(urlString = "/restaurant") { url { appendPathSegments(id) } }
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun updateRestaurant(
        restaurantId: String,
        ownerId: String,
        restaurant: RestaurantInformation
    ): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            put(urlString = "/restaurant") {
                setBody(
                    RestaurantDto(
                        id = restaurantId,
                        name = restaurant.name,
                        ownerId = ownerId,
                        ownerUserName = restaurant.ownerUsername,
                        openingTime = restaurant.openingTime,
                        closingTime = restaurant.closingTime,
                        phone = restaurant.phoneNumber,
                        location = LocationDto(
                            latitude = restaurant.location.split(",")[0].toDouble(),
                            longitude = restaurant.location.split(",")[1].toDouble()
                        )
                    )
                )
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

}