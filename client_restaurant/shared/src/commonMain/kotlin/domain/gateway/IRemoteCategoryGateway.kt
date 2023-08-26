package domain.gateway

import domain.entity.Category


interface IRemoteCategoryGateway {

    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category

}