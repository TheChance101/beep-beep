package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.security.SaltedHash

interface IDataBaseGateway {

    //region address
    suspend fun addLocation(userId: String, location: Location): Address

    suspend fun addAddress(userId: String, address: Address): Address

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(addressId: String, location: Location): Address

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(options: UserOptions): List<UserManagement>

    suspend fun createUser(saltedHash: SaltedHash, country: String, user: UserInfo): UserManagement

    suspend fun updateUser(userId: String, fullName: String?, phone: String?): UserManagement

    suspend fun deleteUser(id: String): Boolean

    suspend fun getNumberOfUsers(): Long

    suspend fun isUserDeleted(id: String): Boolean

    // endregion: user

    // region token

    suspend fun getSaltedHash(username: String): SaltedHash

    // endregion

    // region: user permission management

    suspend fun updateUserPermission(userId: String, permissions: Int): UserManagement

    suspend fun getUserPermission(userId: String): Int

    suspend fun getUserPermissionByUsername(username: String): Int

    // endregion: user permission management

    // region: Wallet
    suspend fun createWallet(userId: String, currency: String): Wallet

    suspend fun subtractFromWallet(userId: String, amount: Double): Wallet

    suspend fun getWalletBalance(userId: String): Wallet

    suspend fun addToWallet(userId: String, amount: Double): Wallet

    suspend fun updateWalletCurrency(userId: String, currency: String)

    // endregion

    // region: favorite

    suspend fun getFavoriteRestaurants(userId: String): List<String>

    suspend fun addToFavorite(userId: String, restaurantId: String): Boolean

    suspend fun deleteFromFavorite(userId: String, restaurantId: String): Boolean
    //endregion

    suspend fun getUserByUsername(username: String): UserManagement

    suspend fun getLastRegisterUser(limit: Int): List<UserManagement>
}