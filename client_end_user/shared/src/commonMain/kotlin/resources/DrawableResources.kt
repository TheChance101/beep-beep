package resources

data class DrawableResources(
    val bpIcon: String = "ic_beep_beep_icon.xml",
    val bpLogo: String = "ic_beep_beep_logo.xml",
    val filledStar: String = "ic_filled_star_light.xml",
    )

val BpDrawableDarkResources = DrawableResources(
    filledStar = "ic_filled_star_dark.xml",
)
