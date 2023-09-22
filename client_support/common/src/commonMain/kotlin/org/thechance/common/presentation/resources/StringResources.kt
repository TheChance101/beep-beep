package org.thechance.common.presentation.resources

data class StringResources(
    //region Login
    val login: String = "Login",
    val loginTitle: String = "Use Admin account to login",
    val loginUsername: String = "Username",
    val loginPassword: String = "Password",
    val loginKeepMeLoggedIn: String = "Keep me logged in",
    //endregion Login

    val logout: String = "Logout",
    val profileImage: String = "Profile Image",
    val closeTicket: String = "Close Ticket",
    val writeYourMessage: String = "Write your message",
    val idle: String = "Idle",
    val idleTitle: String = "There is no inquiry at the moment",
    val idleSubtitle: String = "Please wait for a new inquiry",
)

val englishStrings = StringResources()