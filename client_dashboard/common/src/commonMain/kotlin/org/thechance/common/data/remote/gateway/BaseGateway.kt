package org.thechance.common.data.remote.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.util.*
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

abstract class BaseGateway {

    suspend inline fun <reified T> tryToExecute(
        client: HttpClient,
        method: HttpClient.() -> HttpResponse,
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<String>>().status?.errorMessages
            errorMessages?.let(::throwMatchingException)
        } catch (e: Exception) {
            when (e) {
                is UnresolvedAddressException,
                is ConnectException,
                is UnknownHostException -> throw NoInternetException(e.message.toString())
                else -> throw UnknownErrorException(e.message.toString())
            }
        }
        throw UnknownErrorException("Unknown Error Exception")
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
        val exceptions = mutableListOf<BpError>()

        for (errorCode in errorCodes) {
            val errorMessage = errorMessages[errorCode] ?: continue

            val exception = when (errorCode) {
                NO_PERMISSION -> BpError.InvalidPermission(errorMessage)
                WRONG_PASSWORD -> BpError.InvalidPassword(errorMessage)
                INVALID_USERNAME, USER_NOT_EXIST -> BpError.InvalidUserName(errorMessage)
                INVALID_TAXI_ID -> BpError.InvalidTaxiId(errorMessage)
                INVALID_TAXI_PLATE -> BpError.InvalidTaxiPlate(errorMessage)
                INVALID_TAXI_COLOR -> BpError.InvalidTaxiColor(errorMessage)
                INVALID_CAR_TYPE -> BpError.InvalidCarType(errorMessage)
                SEAT_OUT_OF_RANGE -> BpError.SeatOutOfRange(errorMessage)
                ALREADY_TAXI_EXIST -> BpError.TaxiAlreadyExists(errorMessage)
                TAXI_NOT_FOUND -> BpError.TaxiNotFound(errorMessage)
                RESTAURANT_INVALID_ID -> BpError.RestaurantInvalidId(errorMessage)
                RESTAURANT_INVALID_NAME -> BpError.RestaurantInvalidName(errorMessage)
                RESTAURANT_INVALID_LOCATION -> BpError.RestaurantInvalidLocation(errorMessage)
                RESTAURANT_INVALID_DESCRIPTION -> BpError.RestaurantInvalidDescription(errorMessage)
                RESTAURANT_INVALID_PHONE -> BpError.RestaurantInvalidPhone(errorMessage)
                RESTAURANT_INVALID_TIME -> BpError.RestaurantInvalidTime(errorMessage)
                RESTAURANT_INVALID_PAGE -> BpError.RestaurantInvalidPage(errorMessage)
                RESTAURANT_INVALID_PAGE_LIMIT -> BpError.RestaurantInvalidPageLimit(errorMessage)
                RESTAURANT_INVALID_UPDATE_PARAMETER -> BpError.RestaurantInvalidUpdateParameter(errorMessage)
                RESTAURANT_INVALID_ADDRESS -> BpError.RestaurantInvalidAddress(errorMessage)
                RESTAURANT_INVALID_REQUEST_PARAMETER -> BpError.RestaurantInvalidRequestParameter(errorMessage)
                RESTAURANT_NOT_FOUND -> BpError.RestaurantNotFound(errorMessage)
                RESTAURANT_ERROR_ADD -> BpError.RestaurantErrorAdd(errorMessage)
                RESTAURANT_CLOSED -> BpError.RestaurantClosed(errorMessage)
                CUISINE_NAME_ALREADY_EXISTED -> BpError.CuisineNameAlreadyExisted(errorMessage)
                USERNAME_CANNOT_BE_BLANK -> BpError.UsernameCannotBeBlank(errorMessage)
                PASSWORD_CANNOT_BE_BLANK -> BpError.PasswordCannotBeBlank(errorMessage)
                else -> BpError.UnknownError(errorMessage)
            }
            exceptions.add(exception)
        }

        if (exceptions.isNotEmpty()) {
            throw MultipleErrorException(exceptions)
        }
    }

    private val errorCodes: List<String> = listOf(
        NO_PERMISSION,
        WRONG_PASSWORD,
        INVALID_USERNAME,
        USER_NOT_EXIST,
        INVALID_TAXI_ID,
        INVALID_TAXI_PLATE,
        INVALID_TAXI_COLOR,
        INVALID_CAR_TYPE,
        SEAT_OUT_OF_RANGE,
        ALREADY_TAXI_EXIST,
        INVALID_TAXI_REQUEST_PARAMETER,
        TAXI_NOT_FOUND,
        RESTAURANT_INVALID_ID,
        RESTAURANT_INVALID_NAME,
        RESTAURANT_INVALID_LOCATION,
        RESTAURANT_INVALID_DESCRIPTION,
        RESTAURANT_INVALID_PHONE,
        RESTAURANT_INVALID_TIME,
        RESTAURANT_INVALID_PAGE,
        RESTAURANT_INVALID_PAGE_LIMIT,
        RESTAURANT_INVALID_UPDATE_PARAMETER,
        RESTAURANT_INVALID_ADDRESS,
        RESTAURANT_INVALID_REQUEST_PARAMETER,
        RESTAURANT_NOT_FOUND,
        RESTAURANT_ERROR_ADD,
        RESTAURANT_CLOSED,
        CUISINE_NAME_ALREADY_EXISTED,
        USERNAME_CANNOT_BE_BLANK,
        PASSWORD_CANNOT_BE_BLANK,
        INVALID_USER_REQUEST_PARAMETER
    )

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {

        const val WRONG_PASSWORD = "1013"
        const val NO_PERMISSION = "1014"
        const val USER_NOT_EXIST = "1043"
        const val INVALID_USERNAME = "1003"
        const val USERNAME_CANNOT_BE_BLANK = "1006"
        const val PASSWORD_CANNOT_BE_BLANK = "1007"
        const val INVALID_USER_REQUEST_PARAMETER = "1042"
        const val INVALID_TAXI_ID = "3001"
        const val INVALID_TAXI_PLATE = "3002"
        const val INVALID_TAXI_COLOR = "3003"
        const val INVALID_CAR_TYPE = "3004"
        const val SEAT_OUT_OF_RANGE = "3005"
        const val INVALID_TAXI_LOCATION = "3006"
        const val INVALID_TAXI_RATE = "3007"
        const val INVALID_DATE = "3008"
        const val INVALID_TAXI_PRICE = "3009"
        const val ALREADY_TAXI_EXIST = "3010"
        const val INVALID_TAXI_REQUEST_PARAMETER = "3100"
        const val REQUIRED_QUERY = "3200"
        const val TAXI_NOT_FOUND = "3404"
        const val UNKNOWN_ERROR = "3911"
        const val RESTAURANT_INVALID_ID = "2001"
        const val RESTAURANT_INVALID_NAME = "2002"
        const val RESTAURANT_INVALID_LOCATION = "2003"
        const val RESTAURANT_INVALID_DESCRIPTION = "2004"
        const val RESTAURANT_INVALID_PRICE_LEVEL = "2005"
        const val RESTAURANT_INVALID_RATE = "2006"
        const val RESTAURANT_INVALID_PHONE = "2007"
        const val RESTAURANT_INVALID_TIME = "2008"
        const val RESTAURANT_INVALID_PAGE = "2009"
        const val RESTAURANT_INVALID_PAGE_LIMIT = "2010"
        const val RESTAURANT_INVALID_ONE_OR_MORE_IDS = "2011"
        const val RESTAURANT_INVALID_PERMISSION_UPDATE_LOCATION = "2012"
        const val RESTAURANT_INVALID_UPDATE_PARAMETER = "2013"
        const val RESTAURANT_INVALID_PROPERTY_RIGHTS = "2014"
        const val RESTAURANT_INVALID_PRICE = "2015"
        const val RESTAURANT_INVALID_CUISINE_LIMIT = "2016"
        const val RESTAURANT_INVALID_ADDRESS = "2017"
        const val RESTAURANT_INVALID_REQUEST_PARAMETER = "2100"
        const val RESTAURANT_NOT_FOUND = "2404"
        const val RESTAURANT_ERROR_ADD = "2405"
        const val RESTAURANT_CLOSED = "2500"
        const val CUISINE_NAME_ALREADY_EXISTED = "2503"
    }

    fun <T> paginateData(result: List<T>, limit: Int, total: Int): DataWrapper<T> {
        val pages = (total + limit - 1) / limit
        return DataWrapper(
            totalPages = pages,
            numberOfResult = total,
            result = result
        )
    }
}