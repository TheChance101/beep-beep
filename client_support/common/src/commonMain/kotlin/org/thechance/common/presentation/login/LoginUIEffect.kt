package org.thechance.common.presentation.login

sealed interface LoginUIEffect {

    data object LoginSuccess : LoginUIEffect

    class LoginFailed(val errorMessage: String) : LoginUIEffect

}