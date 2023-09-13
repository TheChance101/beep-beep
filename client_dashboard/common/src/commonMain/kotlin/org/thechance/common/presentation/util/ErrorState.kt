package org.thechance.common.presentation.util

import org.thechance.common.presentation.resources.englishStrings

sealed class ErrorState(val message: String) {

    object NoConnection : ErrorState(englishStrings.noInternet)

    object UnKnownError : ErrorState(englishStrings.unKnownError)

    class InvalidCredentials(val errorMessage: String) : ErrorState(errorMessage)

    class UserNotExist(val errorMessage: String) : ErrorState(errorMessage)
    class LoginError(val errorMessage: String): ErrorState(errorMessage)

}