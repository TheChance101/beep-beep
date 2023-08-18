package org.thechance.api_gateway.data.gateway

import java.util.*

interface IResourcesGateway {

    suspend fun getLocalizedErrorMessage(errorCode: Int, locale: Locale): Map<Int, String>

}