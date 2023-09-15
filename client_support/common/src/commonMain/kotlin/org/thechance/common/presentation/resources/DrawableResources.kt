package org.thechance.common.presentation.resources

data class DrawableResources(
    val login: String = "img_login_light.png",
    val beepBeepLogoExpanded: String = "ic_beepbeep_logo_expanded.svg",
)


val darkDrawableResource = DrawableResources(
    login = "img_login_dark.png",
)

val lightDrawableResource = DrawableResources()