package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Color
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.exceptions.*
import org.thechance.service_taxi.domain.gateway.ITaxiGateway
import org.thechance.service_taxi.domain.usecase.utils.IValidations

interface IManageTaxiUseCase {
    suspend fun createTaxi(taxi: Taxi): Taxi
    suspend fun deleteTaxi(taxiId: String): Taxi
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi
    suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi
    suspend fun getNumberOfTaxis(): Long
    suspend fun findTaxisWithFilters(
        page: Int,
        limit: Int,
        status: Boolean?,
        color: Long?,
        seats: Int?,
        query: String?
    ): List<Taxi>

    suspend fun deleteTaxiByDriverId(driverId: String): Boolean
}

class ManageTaxiUseCase(
    private val taxiGateway: ITaxiGateway,
    private val validations: IValidations,
) : IManageTaxiUseCase {
    override suspend fun createTaxi(taxi: Taxi): Taxi {
        validateTaxi(taxi)
        if (isTaxiExistedBefore(taxi)) throw AlreadyExistException
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

    override suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi {
        validateTaxi(taxi)
        return taxiGateway.editTaxi(taxiId, taxi)
    }

    override suspend fun getNumberOfTaxis(): Long {
        return taxiGateway.getNumberOfTaxis()
    }

    override suspend fun findTaxisWithFilters(
        page: Int,
        limit: Int,
        status: Boolean?,
        color: Long?,
        seats: Int?,
        query: String?
    ): List<Taxi> {
        return taxiGateway.findTaxisWithFilters(page, limit, status, color, seats, query)
    }

    override suspend fun deleteTaxiByDriverId(driverId: String): Boolean {
        return taxiGateway.deleteTaxiByDriverId(driverId)
    }

    private suspend fun isTaxiExistedBefore(taxi: Taxi): Boolean {
        return taxiGateway.isTaxiExistedBefore(taxi)
    }

    private fun validateTaxi(taxi: Taxi) {
        val validationErrors = mutableListOf<Int>()

        if (!validations.isValidPlateNumber(taxi.plateNumber)) {
            validationErrors.add(INVALID_PLATE)
        }
        if (taxi.type.isBlank()) {
            validationErrors.add(INVALID_CAR_TYPE)
        }
        if (!validations.isValidCarType(taxi.type)) {
            validationErrors.add(INVALID_CAR_TYPE)
        }
        if (taxi.color == Color.OTHER) {
            validationErrors.add(INVALID_COLOR)
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

    private companion object {
        val SEAT_RANGE = 1..8
    }
}