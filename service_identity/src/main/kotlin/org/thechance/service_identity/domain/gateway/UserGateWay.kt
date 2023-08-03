package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.User

interface UserGateWay {

    suspend fun getUserById(id: String): User?

    suspend fun getUsers(): List<User>

    suspend fun createUser(user: User): Boolean

    suspend fun updateUser(id: String,user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

}