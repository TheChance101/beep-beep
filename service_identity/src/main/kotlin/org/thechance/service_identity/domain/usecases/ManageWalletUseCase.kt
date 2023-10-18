package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.INSUFFICIENT_FUNDS
import org.thechance.service_identity.domain.util.InsufficientFundsException


interface IManageWalletUseCase {
    suspend fun addToWallet(userId: String, amount: Double): Wallet

    suspend fun subtractFromWallet(userId: String, amount: Double): Wallet

}

@Single
class ManageWalletUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val walletBalanceValidationUseCase: IWalletBalanceValidationUseCase
) : IManageWalletUseCase {
    override suspend fun addToWallet(userId: String, amount: Double): Wallet {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        return dataBaseGateway.addToWallet(userId, amount)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Wallet {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        if (amount > dataBaseGateway.getWalletBalance(userId).walletBalance) {
            throw InsufficientFundsException(INSUFFICIENT_FUNDS)
        }
        return dataBaseGateway.subtractFromWallet(userId, amount)
    }

}