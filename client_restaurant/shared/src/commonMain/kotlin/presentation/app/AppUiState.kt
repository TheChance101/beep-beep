package presentation.app

data class AppUiState(
    val isKeptLoggedIn: Boolean = false,
    val isFirstTimeOpenApp: Boolean = false,
)