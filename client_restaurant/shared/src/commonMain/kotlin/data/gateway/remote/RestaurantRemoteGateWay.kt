package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.RestaurantDto
import data.remote.model.UpdateRestaurantDto
import domain.entity.Restaurant
import domain.gateway.remote.IRestaurantRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import presentation.base.NotFoundedException


class RestaurantRemoteGateWay(client: HttpClient) : BaseRemoteGateway(client),
    IRestaurantRemoteGateway {

    override suspend fun getRestaurantsByOwnerId(): List<Restaurant> {
        return tryToExecute<BaseResponse<List<RestaurantDto>>> {
            get("/restaurants/mine")
        }.value?.toEntity() ?: throw NotFoundedException()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        val response = tryToExecute<BaseResponse<RestaurantDto>> {
            put("/restaurant/details") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            header("Content-Type", ContentType.MultiPart.FormData.toString())
                            append(
                                "data",
                                Json.encodeToString(
                                    UpdateRestaurantDto.serializer(),
                                    restaurant.toDto()
                                )
                            )
                            if (restaurant.image.isNotEmpty()) {
                                append("image", restaurant.image, Headers.build {
                                    append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "form-data; name=image; filename=image.png"
                                    )
                                })
                            }
                        }
                    )
                )
            }
        }
        return response.value != null
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return tryToExecute<BaseResponse<RestaurantDto>> {
            get("/restaurant/$restaurantId")
        }.value?.toEntity() ?: throw NotFoundedException()
    }

}