package org.thechance.service_notification.data.gateway

import org.koin.core.annotation.Single
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.domain.entity.User
import org.thechance.service_notification.domain.gateway.UserGateway

@Single
class UserGatewayImp(private val databaseContainer: DatabaseContainer) : UserGateway {

    override suspend fun createUser(user: User) {
        databaseContainer.userCollection.insertOne(user.toCollection())
    }



}