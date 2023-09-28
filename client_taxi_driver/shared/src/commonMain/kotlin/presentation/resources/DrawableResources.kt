package presentation.resources

data class DrawableResources(
    val backgroundPattern: String = "background_pattern.png",
    val mainScreenImage: String = "main_screen_image.png",
    val errorIcon: String = "ic_error_icon.xml",
    val beepBeepLogo: String = "ic_beep_beep_logo.xml",
    val mapPoint: String = "map_point.xml",
    val close: String = "close.xml",
    val currentLocation: String = "current_location.xml",
    val location: String = "location.xml",
)

val darkDrawableResources = DrawableResources(
    mapPoint = "map_point_dark.xml",
)
