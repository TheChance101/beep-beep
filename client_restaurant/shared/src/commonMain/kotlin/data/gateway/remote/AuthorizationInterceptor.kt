package data.gateway.remote

import data.gateway.local.LocalConfigurationGateway
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import org.koin.core.scope.Scope


fun Scope.intercept(client: HttpClient) {
    client.plugin(HttpSend).intercept { request ->
        val applicationId = 2000
        val localConfigurationGateway = get<LocalConfigurationGateway>()
        val remoteIdentityGateway = get<IdentityRemoteGateway>()

        val accessToken = localConfigurationGateway.getAccessToken()
        val refreshToken = localConfigurationGateway.getRefreshToken()

        request.headers {
            append("Authorization", "Bearer $accessToken")
            append("Application-Id","$applicationId")
        }
        val originalCall = execute(request)

        if (originalCall.response.status.value == 401) {
            val (access, refresh) = remoteIdentityGateway.refreshAccessToken(refreshToken)
            localConfigurationGateway.saveAccessToken(access)
            localConfigurationGateway.saveRefreshToken(refresh)
            execute(request)
        } else {
            originalCall
        }
    }
}

