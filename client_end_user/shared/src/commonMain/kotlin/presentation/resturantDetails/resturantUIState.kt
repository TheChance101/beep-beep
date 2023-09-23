package presentation.resturantDetails

data class RestaurantUIState(
    val restaurantInfoUIState: RestaurantInfoUIState = RestaurantInfoUIState(),
    val isFavourite: Boolean= false,
)

data class RestaurantInfoUIState(
  val   id : String  = "",
  val name :String = "",
  val  address :String = "",
  val  rating :Double = 0.0,
  val  priceLevel :Double = 0.0,
  val  image:String = "",
  val  deliveryPrice:Double = 0.0,
  val  discount :Double = 0.0,
)