package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User


interface IRemoteGateway {
     fun getUserData(): Admin
     fun getUsers(page:Int,numberOfUsers:Int): DataWrapper<User>
     suspend fun getTaxis():List<Taxi>
}