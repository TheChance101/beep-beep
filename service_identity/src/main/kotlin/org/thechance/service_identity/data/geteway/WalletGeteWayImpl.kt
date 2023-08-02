package org.thechance.service_identity.data.geteway


import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.litote.kmongo.unset
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.utils.Constants.WALLET_COLLECTION

@Single
class WalletGateWayImpl(dataBase : DataBaseContainer) : WalletGateWay {

    private val walletCollection by lazy {
        dataBase.database.getCollection<WalletCollection>(
            WALLET_COLLECTION
        )
    }

    private val userDetailsCollection by lazy {
        dataBase.database.getCollection<UserDetailsCollection>("user_details")
    }

    override  suspend fun getWalletById(id: String): Wallet {
     return   walletCollection.findOneById(ObjectId(id))?.toWallet() ?: throw Exception("Wallet not found")
    }

    override  suspend fun getWalletByUserId(userId: String): Wallet {
      return walletCollection.findOne(WalletCollection::userId eq userId)?.toWallet()
          ?: throw Exception("Wallet not found")
    }

    override suspend fun createWallet(wallet: Wallet): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq wallet.userId,
            update = set(UserDetailsCollection::walletId setTo wallet.id)
        )
        return walletCollection.insertOne(wallet.toCollection()).wasAcknowledged()
    }

    override suspend fun updateWallet(id: String,wallet: Wallet): Boolean{
     return    walletCollection.updateOneById(
            id = ObjectId(id),
            update = wallet.toCollection(),
        ).wasAcknowledged()
    }

    override suspend fun deleteWallet(id: String): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::walletId eq id,
            update = unset(UserDetailsCollection::walletId)
        )
        return walletCollection.deleteOne(id).wasAcknowledged()
    }

    private fun Wallet.toCollection(): WalletCollection {
        return WalletCollection(
            id = ObjectId(this.id),
            userId = userId,
            walletBalance = this.walletBalance,
        )
    }
}