package org.thechance.common.presentation.util

sealed class ErrorState(val message: String) {

    object NoConnection : ErrorState("No Connection!")

    object UnKnownError : ErrorState("UnKnown Error!")

    class InvalidCredentials(val errorMessage: String) : ErrorState(errorMessage)

    class UserNotExist(val errorMessage: String) : ErrorState(errorMessage)
    class LoginError(val errorMessage: String): ErrorState(errorMessage)

}