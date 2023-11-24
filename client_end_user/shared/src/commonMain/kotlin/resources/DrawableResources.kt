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
    val arrowLeft: String = "arrow_left.xml",
    val chatImage: String = "img_chat.png",
    val orderImage: String = "img_order.png",
    val orderTaxi: String = "img_taxi.png",
    val filledStar: String = "ic_filled_star_light.xml",
    val placeholder: String = "placeholder.jpeg",
    val scooter: String = "scooter.xml",
    val icError: String = "ic_error.xml",
    val backgroundCard: String = "img_background.png",
    val cart: String = "img_cart.png",
    val taxiOnTheWay: String = "taxi_on_the_way.png",
    val orderOnTheWay: String = "order_on_the_way.png",
    val iconBack: String = "ic_back.xml",
    val wishDishLow: String = "wish_dish_low.png",
    val wishDishMedium: String = "wish_dish_medium.png",
    val wishDishHigh: String = "wish_dish_high.png",
    val quickRide: String = "quick_ride.png",
    val slowRide: String = "slow_ride.png",
    val transferIcon: String = "icon_from_to.xml",
    val logout: String = "logout_2.xml",
    val requireLoginToShowProfilePlaceholder: String = "login_placeholder_profile.png",
    val requireLoginToShowOrdersHistoryPlaceholder: String = "login_placeholder_order_history.png",
    val requireLoginToShowNotificationPlaceholder: String = "login_placeholder_notification.png",
    val needLogin: String = "need_login.png",
    val heart: String = "Heart.png",
    val mapPoint: String = "map_point.xml",
    val star: String = "star.xml",
    val starHalf: String = "star_half.xml",
    val heartFilled: String = "heart_fill.xml",
    val plus: String = "plus_icon.xml",
    val minus: String = "minus_icon.xml",
    val close: String = "close.xml",
    val unread: String = "unread.xml",
    val sendMessage: String = "ic_send.xml",
    val chatPlaceholder: String = "img_chat_placeholder.png",
    val icTime: String = "ic_estimated_time.xml",
    val icHome: String = "ic_home.xml",
    val icScooter: String = "ic_scooter.xml",
    val icInCooking: String = "ic_in_cooking.xml",
    val inCookingFood: String = "food_cooking.png",
    val approvedFood: String = "food_approved.png",
    val warningIcon: String = "ic_error_icon.xml",
    val arrowDown: String = "arrow_down.xml",
    val restaurantErrorPlaceholder: String = "restaurant_error.png",
    val mealErrorPlaceholder: String = "meal_error_placeholder_light.jpg",
    val cuisinePlaceholder:String = "cuisine_placeholder.png"
)

val BpDrawableDarkResources = DrawableResources(
    filledStar = "ic_filled_star_dark.xml",
    restaurantErrorPlaceholder = "restaurant_error_dark.jpg",
    mealErrorPlaceholder = "meal_error_placeholder_dark.jpg"
)
