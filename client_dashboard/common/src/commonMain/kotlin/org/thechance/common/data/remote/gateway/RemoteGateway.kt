package org.thechance.common.data.remote.gateway

import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.util.TaxiStatus
import java.net.ConnectException
import java.net.URL


class RemoteGateway(
    private val client: HttpClient,
) : IRemoteGateway {

    override fun getUserData(): Admin {
        return Admin("aaaa")
    }

    override fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        return DataWrapper(
            totalPages = 0,
            numberOfResult = 0,
            result = emptyList(),
        )
    }

    override suspend fun getTaxis(): List<Taxi> {
        return emptyList()
    }

    override suspend fun createTaxi(taxi: AddTaxi): Taxi {
        println("createTaxi: $taxi")
        return Taxi("1", "1", CarColor.BLACK, "1", 4, "1", TaxiStatus.OFFLINE, "1")
    }

    override suspend fun findTaxiByUsername(username: String): List<Taxi> {
        return emptyList()
    }

    override suspend fun getPdfTaxiReport() {
        //todo
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return emptyList()
    }

    override suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        val result = tryToExecute<ServerResponse<UserTokensRemoteDto>> {
            submitForm(
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) {
                url("login")
                //TODO left until complete get user preferences
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value

        return Pair(result?.accessToken ?: "", result?.refreshToken ?: "")
    }

    override suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant> {
        return emptyList()
    }

    override suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return emptyList()
    }

    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
        return Restaurant(
            id = "1",
            name = restaurant.name,
            ownerUsername = restaurant.ownerUsername,
            phoneNumber = restaurant.phoneNumber,
            workingHours = restaurant.workingHours,
            rating = 0.0,
            priceLevel = 0
        )
    }

    override suspend fun getCurrentLocation(): Location {
        val url = URL("https://ipinfo.io/json")
        val json = url.readText()
        val data = Gson().fromJson(json, Location::class.java)
        val location = data.location
        return Location(location = location)
    }


    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse,
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<*>>().status.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException()
        } catch (e: ConnectException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

    private fun throwMatchingException(errorMessages: Map<String, String>) {
        errorMessages.let {
            if (it.containsErrors(WRONG_PASSWORD)) {
                throw InvalidCredentialsException(it.getOrEmpty(WRONG_PASSWORD))
            } else {
                if (it.containsErrors(USER_NOT_EXIST)) {
                    throw UserNotFoundException(it.getOrEmpty(USER_NOT_EXIST))
                } else {
                    throw UnknownErrorException()
                }
            }
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
    }

}