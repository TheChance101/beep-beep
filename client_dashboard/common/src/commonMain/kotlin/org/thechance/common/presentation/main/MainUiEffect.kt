package org.thechance.common.presentation.main

import org.thechance.common.presentation.login.LoginUIEffect

sealed interface MainUiEffect{
    object Logout : MainUiEffect
}
