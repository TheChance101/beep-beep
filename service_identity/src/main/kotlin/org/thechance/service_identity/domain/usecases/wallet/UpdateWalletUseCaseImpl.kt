import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.domain.usecases.wallet.UpdateWalletUseCase
import org.thechance.service_identity.entity.Wallet

@Single
class UpdateWalletUseCaseImpl(
    private val walletGateWay: WalletGateWay
) : UpdateWalletUseCase {
    override suspend fun invoke(id:String,wallet: Wallet) : Boolean{
        return walletGateWay.updateWallet(id,wallet)
    }
}