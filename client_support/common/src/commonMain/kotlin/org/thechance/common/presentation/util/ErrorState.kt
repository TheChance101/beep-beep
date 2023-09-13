package org.thechance.common.presentation.util

sealed interface ErrorState {

    object NoConnection : ErrorState

    object UnKnownError : ErrorState

    class InvalidCredentials(val errorMessage: String) : ErrorState

    class UserNotExist(val errorMessage: String) : ErrorState

}