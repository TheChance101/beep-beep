package data.gateway.remote

import data.remote.model.ServerResponse
import domain.gateway.IFavoriteRestaurantGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

class FavoriteRestaurantGateway(client: HttpClient) :
    BaseGateway(client = client), IFavoriteRestaurantGateway {

    override suspend fun getFavoriteRestaurants(): List<domain.entity.Restaurant> {
        TODO()
    }

    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>> {
            submitForm(
                url = ("/user/favorite"),
                formParameters = Parameters.build {
                    append("restaurantId", restaurantId)
                }
            ) {
                method = HttpMethod.Post
            }
        }.value ?: throw GeneralException.NotFoundException
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>> {
            submitForm(
                url = ("/user/favorite"),
                formParameters = Parameters.build {
                    append("restaurantId", restaurantId)
                }
            ) {
                method = HttpMethod.Delete
            }
        }.value ?: throw GeneralException.NotFoundException
    }
}