package domain.entity

data class UserCreation(
    val fullName: String,
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
    val address: String,
)
