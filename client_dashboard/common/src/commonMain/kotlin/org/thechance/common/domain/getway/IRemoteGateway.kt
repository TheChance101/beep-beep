package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User


interface IRemoteGateway {

    fun getUserData(): String

    fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User>

    suspend fun getTaxis(page: Int, numberOfUsers: Int): DataWrapper<Taxi>

    suspend fun createTaxi(taxi: NewTaxiInfo): Taxi

    suspend fun findTaxisByUsername(username: String, page: Int, offset:Int): DataWrapper<Taxi>

    suspend fun getPdfTaxiReport()

    suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>

    suspend fun searchRestaurantsByRestaurantName(
        restaurantName: String,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun filterRestaurants(
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>

    suspend fun searchFilteredRestaurantsByName(
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>


    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getCurrentLocation(): Location

}
