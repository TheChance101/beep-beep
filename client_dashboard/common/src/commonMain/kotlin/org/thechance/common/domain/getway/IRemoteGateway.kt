package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.entity.UserTokens


interface IRemoteGateway {

    fun getUserData(): Admin

    fun getUsers(): List<User>

    suspend fun getTaxis(): List<Taxi>

    suspend fun createTaxi(taxi: AddTaxi): Taxi

    suspend fun findTaxiByUsername(username: String): List<Taxi>

    suspend fun getRestaurants(): List<Restaurant>

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens

}