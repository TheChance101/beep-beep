package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.RequestValidationException
import org.thechance.service_identity.domain.util.AMOUNT_MUST_BE_NUMERIC
import org.thechance.service_identity.domain.util.AMOUNT_MUST_BE_POSITIVE

interface IWalletBalanceValidationUseCase {

    fun validateWalletBalance(amount: Double)

    fun validateWalletBalanceIsPositive(balance: Double): Boolean

    fun validateWalletBalanceIsNumeric(balance: Double): Boolean

}

@Single
class WalletBalanceValidationUseCase : IWalletBalanceValidationUseCase {

    override fun validateWalletBalance(amount: Double) {

        val reasons = mutableListOf<String>()

        if (!validateWalletBalanceIsPositive(amount)) {
            reasons.add(AMOUNT_MUST_BE_POSITIVE)
        }

        if (!validateWalletBalanceIsNumeric(amount)) {
            reasons.add(AMOUNT_MUST_BE_NUMERIC)
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateWalletBalanceIsPositive(balance: Double): Boolean = balance > 0

    override fun validateWalletBalanceIsNumeric(balance: Double): Boolean =
        balance.toString().matches("-?\\d+(\\.\\d+)?".toRegex())

}