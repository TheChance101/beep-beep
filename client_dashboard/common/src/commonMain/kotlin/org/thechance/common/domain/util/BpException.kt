package org.thechance.common.domain.util

open class BeepBeepException(message: String) : Exception(message)

class UnknownErrorException(message: String) : BeepBeepException(message)

class NoInternetException(message: String) : BeepBeepException(message)

class NotFoundException(message: String) : BeepBeepException(message)

class MultipleErrorException(val errors: List<BpError>) : BeepBeepException("Multiple exceptions occurred")

sealed interface BpError{

    data class UnknownError(val message: String) : BpError

    data class NoInternetConnection(val message: String) : BpError

    data class NotFoundException(val message: String) : BpError

    data class InvalidPassword(val message: String) : BpError

    data class InvalidUserName(val message: String) : BpError
    data class InvalidPermission(val message: String) : BpError

    data class UsernameCannotBeBlank(val message: String) : BpError

    data class PasswordCannotBeBlank(val message: String) : BpError

    data class InvalidTaxiId(val message: String) : BpError

    data class InvalidTaxiPlate(val message: String) : BpError

    data class InvalidTaxiColor(val message: String) : BpError

    data class InvalidCarType(val message: String) : BpError

    data class SeatOutOfRange(val message: String) : BpError

    data class TaxiAlreadyExists(val message: String) : BpError

    data class TaxiNotFound(val message: String) : BpError

    data class RestaurantInvalidId(val message: String) : BpError

    data class RestaurantInvalidName(val message: String) : BpError

    data class RestaurantInvalidLocation(val message: String) : BpError

    data class RestaurantInvalidDescription(val message: String) : BpError

    data class RestaurantInvalidPhone(val message: String) : BpError

    data class RestaurantInvalidTime(val message: String) : BpError

    data class RestaurantInvalidPage(val message: String) : BpError

    data class RestaurantInvalidPageLimit(val message: String) : BpError

    data class RestaurantInvalidUpdateParameter(val message: String) : BpError

    data class RestaurantInvalidAddress(val message: String) : BpError

    data class RestaurantInvalidRequestParameter(val message: String) : BpError

    data class RestaurantNotFound(val message: String) : BpError

    data class RestaurantErrorAdd(val message: String) : BpError

    data class RestaurantClosed(val message: String) : BpError

    data class CuisineNameAlreadyExisted(val message: String) : BpError

}
