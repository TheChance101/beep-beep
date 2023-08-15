package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.CreateAddressRequest
import org.thechance.service_identity.domain.entity.RequestValidationException
import org.thechance.service_identity.domain.entity.UpdateAddressRequest
import org.thechance.service_identity.domain.util.INVALID_ADDRESS_LOCATION

interface IAddressValidationUseCase {

    fun validateCreateAddressRequest(address: CreateAddressRequest)

    fun validateLocation(latitude: Double, longitude: Double): Boolean

    fun validateUpdateAddressRequest(address: UpdateAddressRequest)
}

@Single
class AddressValidationUseCase : IAddressValidationUseCase {

    override fun validateCreateAddressRequest(address: CreateAddressRequest) {
        val reasons = mutableListOf<String>()

        if (!validateLocation(address.location.latitude, address.location.longitude)) {
            reasons.add(INVALID_ADDRESS_LOCATION)
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateUpdateAddressRequest(address: UpdateAddressRequest) {
        val reasons = mutableListOf<String>()

        if (address.location?.latitude != null && address.location.longitude != null) {
            if (!validateLocation(address.location.latitude, address.location.longitude)) {
                reasons.add(INVALID_ADDRESS_LOCATION)
            }
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateLocation(latitude: Double, longitude: Double): Boolean =
        latitude in -90.0..90.0 && longitude in -180.0..180.0

}