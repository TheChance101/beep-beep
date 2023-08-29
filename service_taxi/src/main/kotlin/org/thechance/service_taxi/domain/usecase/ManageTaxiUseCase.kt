package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Color
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.exceptions.AlreadyExistException
import org.thechance.service_taxi.domain.exceptions.INVALID_CAR_TYPE
import org.thechance.service_taxi.domain.exceptions.INVALID_COLOR
import org.thechance.service_taxi.domain.exceptions.INVALID_ID
import org.thechance.service_taxi.domain.exceptions.INVALID_PLATE
import org.thechance.service_taxi.domain.exceptions.InvalidIdException
import org.thechance.service_taxi.domain.exceptions.MultiErrorException
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.exceptions.SEAT_OUT_OF_RANGE
import org.thechance.service_taxi.domain.gateway.ITaxiGateway
import org.thechance.service_taxi.domain.usecase.utils.IValidations

interface IManageTaxiUseCase {
    suspend fun createTaxi(taxi: Taxi): Taxi
    suspend fun deleteTaxi(taxiId: String): Taxi
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi

    suspend fun editTaxi(taxiId: String,taxi: Taxi): Taxi
}

class ManageTaxiUseCase(
    private val taxiGateway: ITaxiGateway,
    private val validations: IValidations,
) : IManageTaxiUseCase {
    override suspend fun createTaxi(taxi: Taxi): Taxi {
        validationTaxi(taxi)
        taxiGateway.getTaxiById(taxi.id)?.let { throw AlreadyExistException }
        return taxiGateway.addTaxi(taxi)
    }

    override suspend fun deleteTaxi(taxiId: String): Taxi {
        if (!validations.isValidId(taxiId)) throw InvalidIdException
        taxiGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
        return taxiGateway.deleteTaxi(taxiId) ?: throw ResourceNotFoundException
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return taxiGateway.getAllTaxes(page, limit)
    }

    override suspend fun getTaxi(taxiId: String): Taxi {
        return taxiGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
    }

    override suspend fun editTaxi(taxiId: String,taxi: Taxi): Taxi {
        validationTaxi(taxi)
        return taxiGateway.editTaxi(taxiId,taxi)
    }

    private fun validationTaxi(taxi: Taxi) {
        val validationErrors = mutableListOf<Int>()

        if (!validations.isisValidPlateNumber(taxi.plateNumber)) {
            validationErrors.add(INVALID_PLATE)
        }
        if (taxi.color == Color.OTHER) {
            validationErrors.add(INVALID_COLOR)
        }
        if (taxi.type.isBlank()) {
            validationErrors.add(INVALID_CAR_TYPE)
        }
        if (!validations.isValidId(taxi.driverId)) {
            validationErrors.add(INVALID_ID)
        }
        if (taxi.seats !in SEAT_RANGE) {
            validationErrors.add(SEAT_OUT_OF_RANGE)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    private companion object{
        val SEAT_RANGE = 1..8
    }
}