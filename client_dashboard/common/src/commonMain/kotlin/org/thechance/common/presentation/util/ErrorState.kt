package org.thechance.common.presentation.util

sealed interface ErrorState {

    object NoConnection : ErrorState

    object UnKnownError : ErrorState
    class InvalidPassword(val errorMessage: String) : ErrorState

    class UserNotExist(val errorMessage: String) : ErrorState


    class UsernameCannotBeBlank(val errorMessage: String) : ErrorState
    class InvalidUserRequestParameter(val errorMessage: String) : ErrorState

    class InvalidTaxiId(val errorMessage: String) : ErrorState

    class InvalidTaxiPlate(val errorMessage: String) : ErrorState

    class InvalidTaxiColor(val errorMessage: String) : ErrorState

    class InvalidCarType(val errorMessage: String) : ErrorState

    class SeatOutOfRange(val errorMessage: String) : ErrorState

    class TaxiAlreadyExists(val errorMessage: String) : ErrorState

    class InvalidTaxiRequestParameter(val errorMessage: String) : ErrorState

    class TaxiNotFound(val errorMessage: String) : ErrorState

    class RestaurantInvalidId(val errorMessage: String) : ErrorState

    class RestaurantInvalidName(val errorMessage: String) : ErrorState

    class RestaurantInvalidLocation(val errorMessage: String) : ErrorState

    class RestaurantInvalidDescription(val errorMessage: String) : ErrorState

    class RestaurantInvalidPhone(val errorMessage: String) : ErrorState

    class RestaurantInvalidTime(val errorMessage: String) : ErrorState

    class RestaurantInvalidPage(val errorMessage: String) : ErrorState

    class RestaurantInvalidPageLimit(val errorMessage: String) : ErrorState

    class RestaurantInvalidUpdateParameter(val errorMessage: String) : ErrorState

    class RestaurantInvalidAddress(val errorMessage: String) : ErrorState

    class RestaurantInvalidRequestParameter(val errorMessage: String) : ErrorState

    class RestaurantNotFound(val errorMessage: String) : ErrorState

    class RestaurantErrorAdd(val errorMessage: String) : ErrorState

    class RestaurantClosed(val errorMessage: String) : ErrorState

    class CuisineNameAlreadyExisted(val errorMessage: String) : ErrorState
}