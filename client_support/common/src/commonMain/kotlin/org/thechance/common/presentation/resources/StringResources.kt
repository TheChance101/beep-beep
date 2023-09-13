package org.thechance.common.presentation.resources

data class StringResources(
    //region Login
    val login: String = "Login",
    val loginTitle: String = "Use Admin account to login",
    val loginUsername: String = "Username",
    val loginPassword: String = "Password",
    val loginKeepMeLoggedIn: String = "Keep me logged in",
    //endregion Login
)

val englishStrings = StringResources()