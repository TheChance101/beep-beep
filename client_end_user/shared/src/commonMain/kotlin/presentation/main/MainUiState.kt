package presentation.main

import domain.entity.User


data class MainUiState(
    val username: String = "",
    val isLogin: Boolean = false,
    val wallet: Double = 0.0,
    val currency: String = ""
)


fun User.toUIState() = MainUiState(
    username = name,
    wallet = walletValue,
    currency = currency,
    isLogin = true
)
