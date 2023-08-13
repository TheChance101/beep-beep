package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.domain.gateway.IApiGateway
import java.net.http.HttpClient

@Single(binds = [IApiGateway::class])
class ApiGateway(private val client: HttpClient): IApiGateway {

    // region restaurant

    // endregion

    // region taxi

    // endregion

    // region identity

    // endregion

    // region notification

    // endregion

}