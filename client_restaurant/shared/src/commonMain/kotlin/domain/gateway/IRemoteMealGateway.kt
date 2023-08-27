package domain.gateway

import domain.entity.Meal


interface IRemoteMealGateway {

    suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal?
    suspend fun addMeal(meal: Meal): Meal?
    suspend fun updateMeal(meal: Meal): Meal?

}