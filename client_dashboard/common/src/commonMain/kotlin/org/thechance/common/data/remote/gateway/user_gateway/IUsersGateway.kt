package org.thechance.common.data.remote.gateway.user_gateway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User

interface IUsersGateway {
    suspend fun getUserData():String
    suspend fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User>
    suspend fun loginUser(username: String, password: String): Pair<String, String>
}