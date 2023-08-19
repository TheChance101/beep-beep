package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import java.util.*

@Single
class ResourcesGateway : IResourcesGateway {

    override suspend fun getLocalizedErrorMessage(errorCode: Int, locale: Locale): Map<Int, String> {
        val resourceBundle = ResourceBundle.getBundle("errors", locale)
        val localizedMessage = resourceBundle.getString(errorCode.toString())
        return mapOf(errorCode to localizedMessage)
    }

}