package presentation.resturantDetails

sealed class RestaurantUIEffect{
    object onBack : RestaurantUIEffect()

    object onFavourite : RestaurantUIEffect()

    object onGoToDetails : RestaurantUIEffect()


}
