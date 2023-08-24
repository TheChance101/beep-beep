package org.thechance.common.presentation.login

sealed interface LoginUIEffect {

    object LoginUISuccess : LoginUIEffect

    object LoginUIFailed : LoginUIEffect

}