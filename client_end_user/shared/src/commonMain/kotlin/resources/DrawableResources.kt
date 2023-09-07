package resources

data class DrawableResources(
    val bpIcon: String = "ic_beep_beep_icon.xml",
    val bpLogo: String = "ic_beep_beep_logo.xml",
    val backgroundPattern: String = "background_pattern.png",
    val homeOutlined: String = "ic_home_outlined.xml",
    val homeFilled: String = "ic_home_filled.xml",
    val notificationsOutlined: String = "ic_notifications_outlined.xml",
    val notificationsFilled: String = "ic_notifications_filled.xml",
    val profileOutlined: String = "ic_profile_outlined.xml",
    val profileFilled: String = "ic_profile_filled.xml",
    val searchOutlined: String = "ic_search_outlined.xml",
    val searchFilled: String = "ic_search_filled.xml",
    val ordersOutlined: String = "ic_orders_outlined.xml",
    val ordersFilled: String = "ic_orders_filled.xml",
    val arrowRight: String = "ic_right_arrow.xml",
    val chatImage: String = "img_chat.png",
    val orderImage: String = "img_order.png",
    val orderTaxi: String = "img_taxi.png",
    val filledStar: String = "ic_filled_star_light.xml",
    val placeholder: String = "placeholder.jpeg",
    val scooter: String = "scooter.xml",
    val icError: String = "ic_error.xml",
)

val BpDrawableDarkResources = DrawableResources(
    filledStar = "ic_filled_star_dark.xml",
)
