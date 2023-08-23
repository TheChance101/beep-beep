package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import java.util.*

@Single
class ResourcesGateway : IResourcesGateway {

    override suspend fun getLocalizedResponseMessage(code: Int, locale: Locale): Map<Int, String> {
        val resourceBundle = ResourceBundle.getBundle("response_messages", locale)
        val localizedMessage = resourceBundle.getString(code.toString())
        return mapOf(code to localizedMessage)
    }

}