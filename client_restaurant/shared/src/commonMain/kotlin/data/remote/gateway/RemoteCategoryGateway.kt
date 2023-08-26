package data.remote.gateway

import domain.entity.Category
import domain.gateway.IRemoteCategoryGateway
import io.ktor.client.HttpClient


class RemoteCategoryGateway(private val client: HttpClient) : IRemoteCategoryGateway {

    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }

}