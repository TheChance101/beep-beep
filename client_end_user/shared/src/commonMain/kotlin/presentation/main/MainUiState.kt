package presentation.main


data class MainUiState(
    val username: String = "",
    val isLogin: Boolean = false,
    val wallet: Double = 0.0,
    val currency: String = ""
)
