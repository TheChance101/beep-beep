package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single

interface IAddressValidationUseCase {

    fun validateUserIdNotEmpty(userId: String): Boolean

    fun validateLocation(latitude: Double, longitude: Double): Boolean

    fun validateUserIdHexLength(userId: String): Boolean

}

@Single
class AddressValidationUseCase : IAddressValidationUseCase {
    override fun validateUserIdNotEmpty(userId: String): Boolean = userId.isNotEmpty()

    override fun validateLocation(latitude: Double, longitude: Double): Boolean =
        latitude in -90.0..90.0 && longitude in -180.0..180.0

    override fun validateUserIdHexLength(userId: String): Boolean {
        return userId.length == 24
    }

}