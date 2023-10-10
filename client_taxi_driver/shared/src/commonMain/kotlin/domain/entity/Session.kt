package domain.entity

data class Session(
    val accessToken: String,
    val refreshToken: String,
)