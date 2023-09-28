package di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.*
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.thechance.common.domain.getway.IUserLocalGateway

val NetworkModule = module {
    single {
        val client = HttpClient(CIO) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                url("https://beep-beep-api-gateway-nap2u.ondigitalocean.app")
                header("Application-Id", "3000")
                contentType(ContentType.Application.Json)
                logger.debug("Request: $url")
            }
            install(ContentNegotiation) {
                gson()
            }
        }
        intercept(client)
        client
    }

    single(named("locationClient")) {
        val client = HttpClient(CIO) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                gson()
            }
        }
        client
    }
}

fun Scope.intercept(client: HttpClient) {
    client.plugin(HttpSend).intercept { request ->

        val identityGateway = get<IUserLocalGateway>()

        val accessToken = identityGateway.getAccessToken()
        val refreshToken = identityGateway.getRefreshToken()
        val countryCode = identityGateway.getCountryCode()

        request.headers {
            append("Authorization", "Bearer $accessToken")
            append("Accept-Language", countryCode)
            append("Country-Code", countryCode)
        }
        val originalCall = execute(request)

        if (originalCall.response.status.value == 401) {
            val (access, refresh) = identityGateway.refreshAccessToken(refreshToken)
            identityGateway.saveAccessToken(access)
            identityGateway.saveRefreshToken(refresh)
            execute(request)
        } else {
            originalCall
        }
    }
}