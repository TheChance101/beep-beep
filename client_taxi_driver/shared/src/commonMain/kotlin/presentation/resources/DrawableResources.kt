package presentation.resources

data class DrawableResources(
    val backgroundPattern: String = "background_pattern.png",
    val mainScreenImage: String = "main_screen_image.png",
    val errorIcon: String = "ic_error_icon.xml",
    val beepBeepLogo: String = "ic_beep_beep_logo.xml",
)

val darkDrawableResources = DrawableResources()
