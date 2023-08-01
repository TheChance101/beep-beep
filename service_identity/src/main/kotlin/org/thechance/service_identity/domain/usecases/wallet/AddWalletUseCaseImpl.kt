import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.domain.usecases.wallet.AddWalletUseCase
import org.thechance.service_identity.entity.Wallet

@Single
class AddWalletUseCaseImpl (
    private val walletGateWay: WalletGateWayImpl
) : AddWalletUseCase {
    override suspend fun invoke(wallet: Wallet) : Boolean {
      return  walletGateWay.createWallet(wallet)
    }
}
