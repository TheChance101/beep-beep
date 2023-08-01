import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay

@Single
class DeleteWalletUseCaseImpl (
    private val walletGateWay: WalletGateWayImpl
) : DeleteWalletUseCase {
    override suspend fun invoke(id : String) : Boolean{
      return  walletGateWay.deleteWallet(id)
    }
}