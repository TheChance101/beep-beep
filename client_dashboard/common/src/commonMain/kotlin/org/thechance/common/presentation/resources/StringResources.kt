package org.thechance.common.presentation.resources


data class StringResources(
    //region Login
    val login: String,
    val loginTitle: String,
    val loginUsername: String,
    val loginPassword: String,
    val loginButton: String,
    val loginKeepMeLoggedIn: String,
    val loginImageDark: String,
    val loginImageLight: String,
    //endregion Login

    //region Restaurant
    val search: String,
    val export: String,
    val addCuisine: String,
    val newRestaurant: String,
    val restaurant: String,
    val save: String,
    val cancel: String,
    val dollarSign: String,
    val priceLevel: String,
    val starLight: String,
    val starDark: String,
    val halfFilledStarDark: String,
    val halfFilledStarLight: String,
    val filledStarDark: String,
    val filledStarLight: String,
    val rating: String,
    val filter: String,
    val filterIcon: String,
    val filterTitle: String,
    val dots: String,
    //endregion Restaurant

    //region Taxi
    val searchForTaxis: String,
    val searchIcon: String,
    val newTaxi: String,
    val taxi: String,
    val iconDownloadMark: String,
    val downloadSuccessMessage: String,
    val seat: String,
    //endregion Taxi

    //region User
    val user: String,
    val permission: String,
    val country: String,
    val searchForUsers: String,
    val dummyImg: String,
    val edit: String,
    val delete: String
    //endregion User
)

val Strings = StringResources(
    //region Login
    login = "Login",
    loginTitle = "Use Admin account to login",
    loginUsername = "Username",
    loginPassword = "Password",
    loginButton = "Login",
    loginKeepMeLoggedIn = "Keep me logged in",
    loginImageDark = "login_image_dark.png",
    loginImageLight = "login_image_light.png",
    //endregion Login

    //region Restaurant
    search = "Search for restaurants",
    export = "Export",
    addCuisine = "Add cuisine",
    newRestaurant = "New Restaurant",
    restaurant = "restaurant",
    save = "Save",
    cancel = "Cancel",
    dollarSign = "ic_dollar_sign.svg",
    priceLevel = "Price level",
    starLight = "ic_star_light.svg",
    starDark = "ic_star_dark.svg",
    halfFilledStarDark = "ic_half_filled_star_dark.svg",
    halfFilledStarLight = "ic_half_filled_star_light.svg",
    filledStarDark = "ic_filled_star_dark.svg",
    filledStarLight = "ic_filled_star_light.svg",
    rating = "Rating",
    filter = "Filter",
    filterIcon = "ic_filter.svg",
    filterTitle = "Filter",
    dots = "horizontal_dots.xml",
    //endregion Restaurant

    //region Taxi
    searchForTaxis = "Search for Taxis",
    searchIcon = "ic_search.svg",
    newTaxi = "New Taxi",
    taxi = "taxi",
    iconDownloadMark = "ic_download_mark.svg",
    downloadSuccessMessage = "Your file download was successful.",
    seat = "outline_seat.xml",
    //endregion Taxi

    //region User
    user = "user",
    permission = "Permission",
    country = "Country",
    searchForUsers = "Search for users",
    dummyImg = "dummy_img.png",
    edit = "Edit",
    delete = "Delete"
    //endregion User
)

