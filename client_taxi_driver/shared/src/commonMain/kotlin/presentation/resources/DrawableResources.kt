package presentation.resources

data class DrawableResources(
    val backgroundPattern: String = "background_pattern.png",
    val mainScreenImage: String = "main_screen_image.png",
    val mapPoint: String = "map_point.xml",
    val close: String = "close.xml",
    val location: String = "location.xml",
    val gps: String = "gps.xml",
)

val darkDrawableResources = DrawableResources(
    mapPoint = "map_point_dark.xml",
)
