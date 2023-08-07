package org.thechance.service_notification.data.gateway

import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.NotFoundException

@Single
class DatabaseGateway(private val userCollection: CoroutineCollection<UserCollection>) : IDatabaseGateway {

    override suspend fun getUserTokensById(id: String): List<String> {
        return userCollection.findOneById(id)?.deviceTokens ?: throw NotFoundException("4001")
    }

}