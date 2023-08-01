package org.thechance.service_identity.domain.usecases.wallet

data class WalletUseCaseContainer(
    val getWalletUseCase: GetWalletUseCase,
    val deleteWalletUseCase: DeleteWalletUseCase,
    val addWalletUseCase: AddWalletUseCase,
    val updateWalletUseCase: UpdateWalletUseCase,
    val getUserWalletUseCase: GetUserWalletUseCase
)
