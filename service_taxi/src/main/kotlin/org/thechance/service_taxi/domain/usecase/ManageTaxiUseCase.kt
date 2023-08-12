package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.util.AlreadyExistException
import org.thechance.service_taxi.domain.util.InvalidIdException
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.Validations

interface IManageTaxiUseCase {
    suspend fun createTaxi(taxi: Taxi): Boolean
    suspend fun deleteTaxi(taxiId: String): Boolean
    suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi
}

class ManageTaxiUseCase(
    private val dataBaseGateway: DataBaseGateway,
    private val validations: Validations,
) : IManageTaxiUseCase {
    override suspend fun createTaxi(taxi: Taxi): Boolean {
        validations.validationTaxi(taxi)
        dataBaseGateway.getTaxiById(taxi.id)?.let { throw AlreadyExistException }
        return dataBaseGateway.addTaxi(taxi)
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        if (!validations.isValidId(taxiId)) throw InvalidIdException
        dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
        return dataBaseGateway.deleteTaxi(taxiId)
    }

    override suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean {
        if (!validations.isValidId(taxi.id)) throw InvalidIdException
        dataBaseGateway.getTaxiById(taxi.id) ?: throw ResourceNotFoundException
        validations.validateUpdateRequest(taxi)
        return dataBaseGateway.updateTaxi(taxi)
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return dataBaseGateway.getAllTaxes(page, limit)
    }

    override suspend fun getTaxi(taxiId: String): Taxi {
        return dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
    }
}