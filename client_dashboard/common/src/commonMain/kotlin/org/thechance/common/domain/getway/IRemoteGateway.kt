package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User


interface IRemoteGateway {
    fun getUserData(): Admin
    fun getUsers(): List<User>
    suspend fun getTaxis(): List<Taxi>
    suspend fun findTaxiByUsername(username: String): List<Taxi>
}