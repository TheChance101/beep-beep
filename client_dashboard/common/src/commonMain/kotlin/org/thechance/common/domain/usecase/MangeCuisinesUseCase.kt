package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRemoteGateway

interface IMangeCuisinesUseCase {
    suspend fun getCuisines(): List<String>

    suspend fun addCuisine(cuisineName: String): List<String>

    suspend fun deleteCuisine(cuisineName: String): List<String>
}

class MangeCuisinesUseCase(private val remoteGateway: IRemoteGateway) : IMangeCuisinesUseCase {
    override suspend fun getCuisines(): List<String> {
        return remoteGateway.getCuisines()
    }

    override suspend fun addCuisine(cuisineName: String): List<String> {
        return remoteGateway.addCuisine(cuisineName)
    }

    override suspend fun deleteCuisine(cuisineName: String): List<String> {
        return remoteGateway.deleteCuisine(cuisineName)
    }
}