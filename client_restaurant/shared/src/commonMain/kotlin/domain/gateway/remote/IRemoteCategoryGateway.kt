package domain.gateway.remote

import domain.entity.Category


interface IRemoteCategoryGateway {

    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category

}