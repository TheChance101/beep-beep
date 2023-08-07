package org.thechance.service_notification.data.gateway

import org.koin.core.annotation.Single
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.domain.gateway.DatabaseGateway

@Single
class DatabaseGatewayImp(private val databaseContainer: DatabaseContainer) : DatabaseGateway {

}