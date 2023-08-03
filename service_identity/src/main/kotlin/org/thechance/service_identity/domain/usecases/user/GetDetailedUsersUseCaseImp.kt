package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class GetDetailedUsersUseCaseImp(private val gateWay: UserGateWay) : GetDetailedUsersUseCase {

    override suspend fun invoke(): List<User> = gateWay.getDetailedUsers()

}