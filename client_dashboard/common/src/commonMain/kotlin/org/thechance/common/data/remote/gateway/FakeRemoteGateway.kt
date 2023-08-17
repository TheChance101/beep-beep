package org.thechance.common.data.remote.gateway

import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway


class FakeRemoteGateway: IRemoteGateway {
    override fun getUserData(): User {
        return UserDto(0, "asia", "Admin").toEntity()
    }
}