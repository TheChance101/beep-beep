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
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.usecase.utils.IValidations

interface IManageTaxiUseCase {
    suspend fun createTaxi(taxi: Taxi): Taxi
    suspend fun deleteTaxi(taxiId: String): Taxi
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi
}

class ManageTaxiUseCase(
    private val dataBaseGateway: DataBaseGateway,
    private val validations: IValidations,
) : IManageTaxiUseCase {
    override suspend fun createTaxi(taxi: Taxi): Taxi {
        validationTaxi(taxi)
        dataBaseGateway.getTaxiById(taxi.id)?.let { throw AlreadyExistException }
        return dataBaseGateway.addTaxi(taxi)
    }

    override suspend fun deleteTaxi(taxiId: String): Taxi {
        if (!validations.isValidId(taxiId)) throw InvalidIdException
        dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
        return dataBaseGateway.deleteTaxi(taxiId) ?: throw ResourceNotFoundException
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return dataBaseGateway.getAllTaxes(page, limit)
    }

    override suspend fun getTaxi(taxiId: String): Taxi {
        return dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
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