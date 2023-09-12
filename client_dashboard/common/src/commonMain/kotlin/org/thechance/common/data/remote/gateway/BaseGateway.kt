package org.thechance.common.data.remote.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.domain.util.*
import java.net.ConnectException

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
            throw UnknownErrorException(e.message)
        } catch (e: ConnectException) {
            throw NoInternetException()
        }catch(e: ConnectException){
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException(e.message.toString())
        }
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
        when {
            errorMessages.containsErrors(WRONG_PASSWORD) ->
                throw InvalidPasswordException(errorMessages.getOrEmpty(WRONG_PASSWORD))

            errorMessages.containsErrors(INVALID_USERNAME)->
                throw InvalidUserNameException(errorMessages.getOrEmpty(INVALID_USERNAME))

            errorMessages.containsErrors(USER_NOT_EXIST) ->
                throw InvalidUserNameException(errorMessages.getOrEmpty(USER_NOT_EXIST))

            errorMessages.containsErrors(INVALID_TAXI_ID) ->
                throw InvalidTaxiIdException(errorMessages.getOrEmpty(INVALID_TAXI_ID))

            errorMessages.containsErrors(INVALID_TAXI_PLATE) ->
                throw InvalidTaxiPlateException(errorMessages.getOrEmpty(INVALID_TAXI_PLATE))

            errorMessages.containsErrors(INVALID_TAXI_COLOR) ->
                throw InvalidTaxiColorException(errorMessages.getOrEmpty(INVALID_TAXI_COLOR))

            errorMessages.containsErrors(INVALID_CAR_TYPE) ->
                throw InvalidCarTypeException(errorMessages.getOrEmpty(INVALID_CAR_TYPE))

            errorMessages.containsErrors(SEAT_OUT_OF_RANGE) ->
                throw SeatOutOfRangeException(errorMessages.getOrEmpty(SEAT_OUT_OF_RANGE))

            errorMessages.containsErrors(ALREADY_TAXI_EXIST) ->
                throw TaxiAlreadyExistsException(errorMessages.getOrEmpty(ALREADY_TAXI_EXIST))

            errorMessages.containsErrors(INVALID_TAXI_REQUEST_PARAMETER) ->
                throw InvalidTaxiRequestParameterException(errorMessages.getOrEmpty(INVALID_TAXI_REQUEST_PARAMETER))

            errorMessages.containsErrors(TAXI_NOT_FOUND) ->
                throw TaxiNotFoundException(errorMessages.getOrEmpty(TAXI_NOT_FOUND))

            errorMessages.containsErrors(RESTAURANT_INVALID_ID) ->
                throw RestaurantInvalidIdException(errorMessages.getOrEmpty(RESTAURANT_INVALID_ID))

            errorMessages.containsErrors(RESTAURANT_INVALID_NAME) ->
                throw RestaurantInvalidNameException(errorMessages.getOrEmpty(RESTAURANT_INVALID_NAME))

            errorMessages.containsErrors(RESTAURANT_INVALID_LOCATION) ->
                throw RestaurantInvalidLocationException(errorMessages.getOrEmpty(RESTAURANT_INVALID_LOCATION))

            errorMessages.containsErrors(RESTAURANT_INVALID_DESCRIPTION) ->
                throw RestaurantInvalidDescriptionException(errorMessages.getOrEmpty(RESTAURANT_INVALID_DESCRIPTION))

            errorMessages.containsErrors(RESTAURANT_INVALID_PHONE) ->
                throw RestaurantInvalidPhoneException(errorMessages.getOrEmpty(RESTAURANT_INVALID_PHONE))

            errorMessages.containsErrors(RESTAURANT_INVALID_TIME) ->
                throw RestaurantInvalidTimeException(errorMessages.getOrEmpty(RESTAURANT_INVALID_TIME))

            errorMessages.containsErrors(RESTAURANT_INVALID_PAGE) ->
                throw RestaurantInvalidPageException(errorMessages.getOrEmpty(RESTAURANT_INVALID_PAGE))

            errorMessages.containsErrors(RESTAURANT_INVALID_PAGE_LIMIT) ->
                throw RestaurantInvalidPageLimitException(errorMessages.getOrEmpty(RESTAURANT_INVALID_PAGE_LIMIT))

            errorMessages.containsErrors(RESTAURANT_INVALID_UPDATE_PARAMETER) ->
                throw RestaurantInvalidUpdateParameterException(errorMessages.getOrEmpty(RESTAURANT_INVALID_UPDATE_PARAMETER))

            errorMessages.containsErrors(RESTAURANT_INVALID_ADDRESS) ->
                throw RestaurantInvalidAddressException(errorMessages.getOrEmpty(RESTAURANT_INVALID_ADDRESS))

            errorMessages.containsErrors(RESTAURANT_INVALID_REQUEST_PARAMETER) ->
                throw RestaurantInvalidRequestParameterException(errorMessages.getOrEmpty(RESTAURANT_INVALID_REQUEST_PARAMETER))

            errorMessages.containsErrors(RESTAURANT_NOT_FOUND) ->
                throw RestaurantNotFoundException(errorMessages.getOrEmpty(RESTAURANT_NOT_FOUND))

            errorMessages.containsErrors(RESTAURANT_ERROR_ADD) ->
                throw RestaurantErrorAddException(errorMessages.getOrEmpty(RESTAURANT_ERROR_ADD))

            errorMessages.containsErrors(RESTAURANT_CLOSED) ->
                throw RestaurantClosedException(errorMessages.getOrEmpty(RESTAURANT_CLOSED))

            errorMessages.containsErrors(CUISINE_NAME_ALREADY_EXISTED) ->
                throw CuisineNameAlreadyExistedException(errorMessages.getOrEmpty(CUISINE_NAME_ALREADY_EXISTED))

            errorMessages.containsErrors(USERNAME_CANNOT_BE_BLANK)->
                throw UsernameCannotBeBlankException(errorMessages.getOrEmpty(USERNAME_CANNOT_BE_BLANK))

            errorMessages.containsErrors(PASSWORD_CANNOT_BE_BLANK)->
                throw PasswordCannotBeBlankException(errorMessages.getOrEmpty(PASSWORD_CANNOT_BE_BLANK))

            errorMessages.containsErrors(INVALID_USER_REQUEST_PARAMETER)->
                throw InvalidUserRequestParameterException(errorMessages.getOrEmpty(INVALID_USER_REQUEST_PARAMETER))
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
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

}