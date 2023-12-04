package resources

data class DrawableResources(
    val bpIcon: String = "ic_beep_beep_icon.xml",
    val bpLogo: String = "ic_beep_beep_logo.xml",
    val arrowLeft: String = "arrow_left.xml",
    val moonStars: String = "moon_stars.xml",
    val filledStar: String = "ic_filled_star_light.xml",
    val halfFilledStar: String = "ic_half_filled_star_light.xml",
    val sort: String = "sort.xml",
    val sun: String = "sun.xml",
    val backgroundPattern: String = "background_pattern.png",
    val pattern: String = "pattern.png",
    val galleryAdd: String = "gallery_add.xml",
    val edit: String = "edit.xml",

    val flag: String = "flag.xml",
    val flagEgypt: String = "flag_egypt.xml",
    val flagIraq: String = "flag_iraq.xml",
    val flagSyria: String = "flag_syria.xml",
    val flagPalestine: String = "flag_of_palestine.xml",

    val logout: String = "ic_logout.xml",
    val info: String = "info.png",
    val meals: String = "meals.png",
    val orders: String = "orders.png",
    val ordersBig: String = "orders_big.png",
    val ordersHistory: String = "orders_history.png",
    val revenue: String = "revenue.png",
    val arrowDown: String = "arrow_down.xml",
    val errorIcon: String = "ic_error_icon.xml",
    val iconEdit: String = "ic_edit.xml",
    val emptyScreen: String = "empty.png",
    val restaurantErrorPlaceholder: String = "restaurant_error.png",
    val mealErrorPlaceholder: String = "meal_error_placeholder_light.jpg",
    val cuisinePlaceholder: String = "cuisine_placeholder.png",
    val unread: String = "unread.xml",
)

val BpDrawableDarkResources = DrawableResources(
    filledStar = "ic_filled_star_dark.xml",
    halfFilledStar = "ic_half_filled_star_dark.xml",
    backgroundPattern = "background_pattern.png",
    restaurantErrorPlaceholder = "restaurant_error_dark.jpg",
    mealErrorPlaceholder = "meal_error_placeholder_dark.jpg"
)
