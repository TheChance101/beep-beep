package domain.entity

/**
 * Created by Aziza Helmy on 8/24/2023.
 */
data class UserTokens(
    var accessToken: String,
    var refreshToken: String,
)