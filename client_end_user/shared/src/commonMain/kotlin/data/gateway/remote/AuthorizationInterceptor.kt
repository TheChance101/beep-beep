package data.gateway.remote

import data.gateway.local.LocalConfigurationGateway
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import org.koin.core.scope.Scope

fun Scope.authorizationIntercept(client: HttpClient) {

    val localConfigurationGateway: LocalConfigurationGateway by inject()
    val userRemoteGateway: UserGateway by inject()

    client.plugin(HttpSend).intercept { request ->

        val accessToken = localConfigurationGateway.getAccessToken()
        val languageCode = localConfigurationGateway.getLanguageCode()

        request.headers {
            append("Authorization", "Bearer $accessToken")
            append("Accept-Language", languageCode)
        }

        val originalCall = execute(request)
        if (originalCall.response.status.value == 401 && accessToken.isNotEmpty() ) {
            val (access, refresh) = userRemoteGateway.refreshAccessToken()
            localConfigurationGateway.saveAccessToken(access)
            localConfigurationGateway.saveRefreshToken(refresh)
            execute(request)
        } else {
            originalCall
        }
    }
}
