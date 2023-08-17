package org.thechance.api_gateway.domain.gateway

import java.util.*

interface IResourcesGateway {

    suspend fun getLocalizedErrorMessage(errorCode: Int, locale: Locale): String

}