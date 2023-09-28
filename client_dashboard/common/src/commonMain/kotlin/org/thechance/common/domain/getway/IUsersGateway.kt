package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Country
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User

interface IUsersGateway {

    suspend fun getUsers(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<Country>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User>

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun deleteUser(userId: String): Boolean

    suspend fun getLastRegisteredUsers(limit : Int): List<User>

    suspend fun updateUserPermissions(userId: String, permissions: List<Permission>): User

}