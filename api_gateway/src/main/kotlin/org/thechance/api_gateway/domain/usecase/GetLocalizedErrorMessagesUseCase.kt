package org.thechance.api_gateway.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.api_gateway.domain.gateway.IResourcesGateway
import java.util.*

interface IGetLocalizedErrorMessagesUseCase {

    suspend fun getLocalizedErrors(errorCodes: List<Int>, locale: Locale): List<String>

}

@Single
class GetLocalizedErrorMessagesUseCase(
    private val resourcesGateway: IResourcesGateway
) : IGetLocalizedErrorMessagesUseCase {

    override suspend fun getLocalizedErrors(errorCodes: List<Int>, locale: Locale): List<String> {
        return errorCodes.map { resourcesGateway.getLocalizedErrorMessage(it, locale) }
    }

}