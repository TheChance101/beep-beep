package org.thechance.common.presentation.resources

data class DrawableResources(
    val login: String = "img_login_light.png",
    val beepBeepLogoExpanded: String = "ic_beepbeep_logo_expanded.svg",
    val dropDownArrow: String = "ic_drop_down_arrow_light.svg",
    val logout: String = "ic_logout.svg",
    val send: String = "ic_send.svg",
    val idle: String = "idle.png",
)


val darkDrawableResource = DrawableResources(
    login = "img_login_dark.png",
    dropDownArrow = "ic_drop_down_arrow_dark.svg",
    send = "ic_send.svg",
    idle = "idle.png",
)

val lightDrawableResource = DrawableResources()