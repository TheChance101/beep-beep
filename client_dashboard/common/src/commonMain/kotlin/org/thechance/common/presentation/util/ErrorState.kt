package org.thechance.common.presentation.util

sealed interface ErrorState {

    object NoConnection : ErrorState

    object UnKnownError : ErrorState
    
    data class InvalidPassword(val errorMessage: String) : ErrorState

    data class InvalidUserName(val errorMessage: String) : ErrorState

    data class UserNotExist(val errorMessage: String) : ErrorState

    data class UsernameCannotBeBlank(val errorMessage: String) : ErrorState
    data class InvalidPermission(val errorMessage: String) : ErrorState

    data class InvalidUserRequestParameter(val errorMessage: String) : ErrorState

    data class InvalidTaxiId(val errorMessage: String) : ErrorState

    data class InvalidTaxiPlate(val errorMessage: String) : ErrorState

    data class InvalidTaxiColor(val errorMessage: String) : ErrorState

    data class InvalidCarType(val errorMessage: String) : ErrorState

    data class SeatOutOfRange(val errorMessage: String) : ErrorState

    data class TaxiAlreadyExists(val errorMessage: String) : ErrorState

    data class InvalidTaxiRequestParameter(val errorMessage: String) : ErrorState

    data class TaxiNotFound(val errorMessage: String) : ErrorState

    data class RestaurantInvalidId(val errorMessage: String) : ErrorState

    data class RestaurantInvalidName(val errorMessage: String) : ErrorState

    data class RestaurantInvalidLocation(val errorMessage: String) : ErrorState

    data class RestaurantInvalidDescription(val errorMessage: String) : ErrorState

    data class RestaurantInvalidPhone(val errorMessage: String) : ErrorState

    data class RestaurantInvalidTime(val errorMessage: String) : ErrorState

    data class RestaurantInvalidPage(val errorMessage: String) : ErrorState

    data class RestaurantInvalidPageLimit(val errorMessage: String) : ErrorState

    data class RestaurantInvalidUpdateParameter(val errorMessage: String) : ErrorState

    data class RestaurantInvalidAddress(val errorMessage: String) : ErrorState

    data class RestaurantInvalidRequestParameter(val errorMessage: String) : ErrorState

    data class RestaurantNotFound(val errorMessage: String) : ErrorState

    data class RestaurantErrorAdd(val errorMessage: String) : ErrorState

    data class RestaurantClosed(val errorMessage: String) : ErrorState

    data class CuisineNameAlreadyExisted(val errorMessage: String) : ErrorState

    data class MultipleErrors(val errors: List<ErrorState>) : ErrorState

    data class PasswordCannotBeBlank(val message: String) : ErrorState

}