package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.Validations

interface IManageTaxiUseCase {
    suspend fun createTaxi(taxi: Taxi): Boolean
    suspend fun deleteTaxi(taxiId: String): Boolean
    suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi
}

@Single
class ManageTaxiUseCase(
    private val dataBaseGateway: DataBaseGateway,
    private val validations: Validations,
) : IManageTaxiUseCase {
    override suspend fun createTaxi(taxi: Taxi): Boolean {
        validations.validationTaxi(taxi)
        return dataBaseGateway.addTaxi(taxi)
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
        return dataBaseGateway.deleteTaxi(taxiId)
    }

    override suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean {
        dataBaseGateway.getTaxiById(taxi.id) ?: throw ResourceNotFoundException
        return dataBaseGateway.updateTaxi(taxi)
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return dataBaseGateway.getAllTaxes(page, limit)
    }

    override suspend fun getTaxi(taxiId: String): Taxi {
        return dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
    }
}