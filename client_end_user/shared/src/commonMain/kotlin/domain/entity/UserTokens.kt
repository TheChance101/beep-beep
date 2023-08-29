package domain.entity

data class UserTokens(
    var accessToken: String,
    var refreshToken: String,
)