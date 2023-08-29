package data.gateway.remote

import domain.gateway.local.ILocalConfigurationGateway
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin


class RequestInterceptor(
    private val client: HttpClient,
    private val localConfigurationGateway: ILocalConfigurationGateway
) {
    fun intercept() {
        client.plugin(HttpSend).intercept { request ->
        val accessToken = localConfigurationGateway.getAccessToken()
        val refreshToken = localConfigurationGateway.getRefreshToken()

            val originalCall = execute(request)
            if (originalCall.response.status.value !in 100..399) {
                execute(request)
            } else {
                originalCall
            }
        }
    }


}