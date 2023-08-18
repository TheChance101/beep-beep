package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.User


interface IRemoteGateway {
     fun getUserData(): User
}