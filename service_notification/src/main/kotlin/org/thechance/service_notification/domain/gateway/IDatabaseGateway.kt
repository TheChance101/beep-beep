package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.model.User

interface IDatabaseGateway {
    suspend fun createUser(user: User): Boolean

    suspend fun getUsers(): List<User>

    suspend fun getUser(id: String): User

    suspend fun addTokenToUser(id: String, token: String): Boolean

    suspend fun getUserTokensById(id: String): List<String>

    suspend fun getUserTokensByTopic(topic: String): List<String>

}