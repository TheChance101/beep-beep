package data.gateway.remote

import data.gateway.local.LocalConfigurationGateway
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import org.koin.core.scope.Scope

fun Scope.authorizationIntercept(client: HttpClient) {

    val localConfigurationGateway: LocalConfigurationGateway by inject()
    val userGateway: UserGateway by inject()

    client.plugin(HttpSend).intercept { request ->

        val accessToken = localConfigurationGateway.getAccessToken()
        val refreshToken = localConfigurationGateway.getRefreshToken()

        request.headers {
            append("Authorization", "Bearer $accessToken")
        }

        val originalCall = execute(request)
        if (originalCall.response.status.value == 401) {
            val (access, refresh) = userGateway.refreshAccessToken(refreshToken)
            localConfigurationGateway.saveAccessToken(access)
            localConfigurationGateway.saveRefreshToken(refresh)
            execute(request)
        } else {
            originalCall
        }
    }

}