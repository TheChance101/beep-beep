package org.thechance.common.presentation.resources


data class StringResources(
    val login: String,
    val loginTitle: String,
    val loginUsername: String,
    val loginPassword: String,
    val loginButton: String,
    val loginKeepMeLoggedIn: String,
    val loginImageDark: String,
    val loginImageLight: String,
)

val Strings = StringResources(
    login = "Login",
    loginTitle = "Use Admin account to login",
    loginUsername = "Username",
    loginPassword = "Password",
    loginButton = "Login",
    loginKeepMeLoggedIn = "Keep me logged in",
    loginImageDark = "login_image_dark.png",
    loginImageLight = "login_image_light.png",
)
