package domain.gateway.remote

import domain.entity.Category


interface ICategoryRemoteGateway {

    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category

}