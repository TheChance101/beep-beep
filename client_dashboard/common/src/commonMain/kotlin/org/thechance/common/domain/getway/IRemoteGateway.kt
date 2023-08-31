package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.*


interface IRemoteGateway {

    fun getUserData(): String

    fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User>

    suspend fun getTaxis(page: Int, numberOfTaxis: Int): DataWrapper<Taxi>

    suspend fun createTaxi(taxi: NewTaxiInfo): Taxi

    suspend fun findTaxisByUsername(username: String, page: Int, numberOfTaxis:Int): DataWrapper<Taxi>

    suspend fun filterTaxis(taxi: TaxiFiltration, page: Int, numberOfTaxis: Int): DataWrapper<Taxi>

    suspend fun getPdfTaxiReport()

    suspend fun getRestaurants(): List<Restaurant>

    suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant>

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant>

    suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant>


    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getCurrentLocation(): Location

    suspend fun getCuisines(): List<String>

    suspend fun addCuisine(cuisineName: String): List<String>

    suspend fun deleteCuisine(cuisineName: String): List<String>

}
