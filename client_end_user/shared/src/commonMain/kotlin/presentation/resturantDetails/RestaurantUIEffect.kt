package presentation.resturantDetails

sealed class RestaurantUIEffect{
    object onBack : RestaurantUIEffect()


    object onGoToDetails : RestaurantUIEffect()


}
