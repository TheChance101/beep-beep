package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.util.TaxiStatus
import java.net.ConnectException


class RemoteGateway(
    private val client: HttpClient
) : IRemoteGateway {

    override fun getUserData(): Admin {
        return Admin("aaaa")
    }

    override fun getUsers(): List<User> {
        return emptyList()
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

    override suspend fun getRestaurants(): List<Restaurant> {
        return emptyList()
    }

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens {
        return tryToExecute<ServerResponse<UserTokensRemoteDto>> {
            submitForm(
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) {
                url("login")
                //left until complete get user preferences
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }


    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            return client.method().body<T>()
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