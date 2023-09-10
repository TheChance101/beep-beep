package resources

data class StringResources(
    val beepBeep: String = "Beep Beep",
    val backgroundDescription: String = "background image",
    val loginWelcomeMessage: String = "Welcome To Beep Beep App",
    val loginSubWelcomeMessage: String = "Login to access all the features",
    val usernameLabel: String = "Username",
    val passwordLabel: String = "Password",
    val login: String = "Login",
    val keepMeLoggedIn: String = "Keep me logged in",
    //region permission
    val wrongPermissionMessage: String = "Looks like your account isn't assigned as a restaurant owner, ask for permission?",
    val wrongPermission: String = "Wrong permission",
    val requestAPermission: String = "Request a permission",
    val close: String = "Close",
    val cancel: String = "Cancel",
    //endregion
)