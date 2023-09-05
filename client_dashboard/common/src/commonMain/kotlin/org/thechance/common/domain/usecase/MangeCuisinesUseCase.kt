package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRemoteGateway

interface IMangeCuisinesUseCase {
    suspend fun getCuisines(): List<String>

    suspend fun createCuisine(cuisineName: String): String?

    suspend fun deleteCuisine(cuisineName: String): String
}

class MangeCuisinesUseCase(private val remoteGateway: IRemoteGateway) : IMangeCuisinesUseCase {
    override suspend fun getCuisines(): List<String> {
        return remoteGateway.getCuisines()
    }

    override suspend fun createCuisine(cuisineName: String): String? {
        return remoteGateway.createCuisine(cuisineName)
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        return remoteGateway.deleteCuisine(cuisineName)
    }
}