package resources

data class StringResources(
    val beepBeep: String = "Beep Beep",
    val backgroundDescription: String = "background image",
    val loginWelcomeMessage: String = "Welcome To Delivery App",
    val loginSubWelcomeMessage: String = "Login to access all the features",
    val usernameLabel: String = "Username",
    val passwordLabel: String = "Password",
    val login: String = "Login",
    val keepMeLoggedIn: String = "Keep me logged in",
    val signupWithBeepBeep: String = "Sign up with Beep Beep account",
    val foodsAreWaiting: String = "Foodies are waiting!",
    val tapStartTitle: String = "Tap Start and become their food \nsuperhero",
    val start: String = "Start",
    //region permission
    val wrongPermissionMessage: String = "Looks like your account isn't assigned as a restaurant owner, ask for permission?",
    val wrongPermission: String = "Wrong permission",
    val requestAPermission: String = "Request a permission",
    val close: String = "Close",
    val cancel: String = "Cancel",
    val askForPermission: String = "Ask for permission",
    val userEmailLabel: String = "User email",
    val restaurantName: String = "Full name",
    val questionHint: String = "Describe why you want to join us",
    val whyBeepBeep: String = "Why beep beep?",
    val submit: String = "Submit",
    //endregion
)