package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway

@Single
class GetNotificationHistoryUseCase(private val databaseGateway: IDatabaseGateway) : IGetNotificationHistoryUseCase {


}

interface IGetNotificationHistoryUseCase {

}