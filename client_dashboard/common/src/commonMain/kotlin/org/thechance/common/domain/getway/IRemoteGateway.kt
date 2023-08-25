package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.entity.UserTokens


interface IRemoteGateway {

    fun getUserData(): Admin

    fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User>

    suspend fun getTaxis(): List<Taxi>

    suspend fun createTaxi(taxi: AddTaxi): Taxi

    suspend fun findTaxiByUsername(username: String): List<Taxi>

    suspend fun getPdfTaxiReport()

    suspend fun getRestaurants(): List<Restaurant>

    suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant>

    suspend fun loginUser(username: String, password: String): UserTokens

    suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant>

    suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant>


    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getCurrentLocation(): Location

}
