package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.UserDetails

interface UserDetailsGateway {

    suspend fun createUserDetails(user: UserDetails)

    suspend fun getUserDetails(userId: String): UserDetails

    suspend fun updateUserDetails(user: UserDetails)

    suspend fun addAddressToUser(userId: String, addressId: String)

    suspend fun removeAddressFromUser(userId: String, addressId: String)

    suspend fun addPermissionToUser(userId: String, permissionId: String)

    suspend fun removePermissionFromUser(userId: String, permissionId: String)

    suspend fun addWalletToUser(userId: String, walletId: String)

    suspend fun deleteUserWallet(walletId: String)

}