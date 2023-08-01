
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.entity.Wallet

@Single
class WalletGateWayImpl(dataBase : DataBaseContainer) : WalletGateWay {

    private val walletCollection by lazy { dataBase.database.getCollection<WalletCollection>(WALLET_COLLECTION) }

    override  suspend fun getWalletById(id: String): Wallet {
     return   walletCollection.findOneById(ObjectId(id))?.toWallet() ?: throw Exception("Wallet not found")
    }

    override  suspend fun getWalletByUserId(userId: String): Wallet {
      return  walletCollection.findOne(WalletCollection::userId eq ObjectId(userId).toId())?.toWallet() ?: throw Exception("Wallet not found")
    }

    override suspend fun createWallet(wallet: Wallet): Boolean {
      return  walletCollection.insertOne(wallet.toCollection()).wasAcknowledged()
    }

    override suspend fun updateWallet(id: String,wallet: Wallet): Boolean{
     return    walletCollection.updateOneById(
            id = ObjectId(id),
            update = wallet.toCollection(),
        ).wasAcknowledged()
    }

    override suspend fun deleteWallet(id: String): Boolean {
     return   walletCollection.deleteOne(id).wasAcknowledged()
    }

    private fun Wallet.toCollection(): WalletCollection {
        return WalletCollection(
            id = ObjectId(this.id),
            userId = ObjectId(this.userId).toId(),
            walletBalance = this.walletBalance,
        )
    }
}