package org.thechance.api_gateway.data.gateway

import java.util.*

interface IResourcesGateway {

    suspend fun getLocalizedResponseMessage(code: Int, locale: Locale): Map<Int, String>

}