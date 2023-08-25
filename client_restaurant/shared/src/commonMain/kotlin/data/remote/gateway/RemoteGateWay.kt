package data.remote.gateway

import data.remote.model.BaseResponse
import data.remote.model.UserTokensDto
import data.remote.model.toEntity
import domain.entity.Category
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Order
import domain.entity.OrderState
import domain.entity.Restaurant
import domain.entity.UserTokens
import domain.gateway.IRemoteGateWay
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.atomicfu.TraceBase.None.append
import presentation.base.InternetException
import presentation.base.InvalidCredentialsException
import presentation.base.NoInternetException
import presentation.base.UnknownErrorException
import presentation.base.UserNotFoundException

class RemoteGateWay(private val client: HttpClient) : IRemoteGateWay {

    //region login
    override suspend fun loginUser(userName: String, password: String): UserTokens {
     return   tryToExecute<BaseResponse<UserTokensDto>>() {
           submitForm(
                formParameters = Parameters.build {
                    append("username", userName)
                    append("password", password)
                }
            ) {
                url("login")
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
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

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order {
        return Order("", "", "", emptyList(), 0.0, "", OrderState.CANCELED)
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return null
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getCuisinesInMeal(mealId: String): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getCuisine(restaurantId: String): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return emptyList()
    }
    //endregion category

    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {

        try {
            return client.method().body<T>()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<BaseResponse<*>>().status.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException()
        } catch (e: InternetException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

 private fun throwMatchingException(errorMessages: Map<String, String>) {
        if (errorMessages.containsErrors("1013")) {
            throw InvalidCredentialsException(errorMessages["1013"] ?: "")
        } else if (errorMessages.containsErrors("1043")) {
            throw UserNotFoundException(errorMessages["1043"] ?: "")
        } else {
            throw UnknownErrorException()
        }
    }
      private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

}