package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.Wallet

interface DataBaseGateway {
    suspend fun getPermission(permissionId: String): Permission?
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(permissionId: String, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: String): Boolean

    // region: wallet
    suspend fun getWallet(walletId: String): Wallet
    suspend fun createWallet(wallet: Wallet): Boolean
    suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean

    // endregion: wallet
}