package presentation.login

data class LoginScreenUIState(
    val restaurantName: String = "",
    val description: String = "",
    val email: String = "",
    val ownerEmail: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val hasPermission: Boolean = false,
    val error: String = ""
)
