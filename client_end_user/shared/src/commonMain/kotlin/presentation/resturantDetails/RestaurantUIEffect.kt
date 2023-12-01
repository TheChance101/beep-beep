package presentation.resturantDetails

sealed class RestaurantUIEffect{
    object onBack : RestaurantUIEffect()


    object onGoToDetails : RestaurantUIEffect()

    object onGoToLogin : RestaurantUIEffect()
    object onGoToCart : RestaurantUIEffect()


}
