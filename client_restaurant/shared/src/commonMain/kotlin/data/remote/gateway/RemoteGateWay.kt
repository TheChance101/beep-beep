package data.remote.gateway

import domain.entity.Category
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant
import domain.entity.UserToken
import domain.gateway.IRemoteGateWay
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.http.parameters

class RemoteGateWay(private val client: HttpClient) : IRemoteGateWay {

    //region login
    override suspend fun loginUser(userName: String, password: String): UserToken {
        tryToExecute<Boolean>() {
            submitForm("/user/login",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)
                }
            )
        }
        return UserToken("")
    }
    // endregion

    //region restaurant
    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant? {
        return restaurant
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant? {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId }
    }
    //endregion restaurant

    //region meal
    override suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal> {
        return emptyList()
    }

    override suspend fun getMealById(mealId: String): Meal? {
        return getMealsByRestaurantId("ef77d90").find { it.id == mealId }
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
    //endregion meal

    //region order
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order? {

        return null
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }
    //endregion category

    private suspend inline fun <reified T> tryToExecute(
        setErrorMessage: (errorCodes: List<Int>) -> List<Map<Int, String>> = { emptyList() },
        method: HttpClient.() -> HttpResponse
    ): T {

        val response = client.method()
        if (response.status.isSuccess()) {
            return response.body<T>()
        } else {
            val errorResponse = response.body<List<Int>>()
            val errorMessage = setErrorMessage(errorResponse)
            throw response.body()
        }
    }

}