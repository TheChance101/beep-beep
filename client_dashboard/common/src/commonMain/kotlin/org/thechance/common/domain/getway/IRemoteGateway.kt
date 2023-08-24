package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User


interface IRemoteGateway {

    fun getUserData(): Admin

    fun getUsers(page:Int,numberOfUsers:Int): DataWrapper<User>

    suspend fun getTaxis(): List<Taxi>

    suspend fun createTaxi(taxi: AddTaxi): Taxi

    suspend fun findTaxiByUsername(username: String): List<Taxi>

    suspend fun getRestaurants(): List<Restaurant>

    suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant>

    suspend fun loginUser(username: String, password: String): UserTokens
}