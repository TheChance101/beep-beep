package di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.thechance.common.domain.getway.IIdentityGateway

val NetworkModule = module {
    single {
        val client = HttpClient(CIO) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                header("Content-Type", "application/json")
                url("https://beep-beep-api-gateway-nap2u.ondigitalocean.app")
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
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

        val identityGateway = get<IIdentityGateway>()

        val accessToken = identityGateway.getAccessToken()
        val refreshToken = identityGateway.getRefreshToken()

        request.headers {
            append(
                "Authorization",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhbGxfdXNlcnMiLCJpc3MiOiJ0aGUtY2hhbmNlLm9yZyIsInBlcm1pc3Npb24iOiI3IiwiZXhwIjoxNzI0NzcyMTQ3LCJ0b2tlblR5cGUiOiJBQ0NFU1NfVE9LRU4iLCJ1c2VySWQiOiI2NGYzNjUwYzVkZGJjMTViZmQxZWZjZjciLCJ1c2VybmFtZSI6ImhhaWR5X2Fib3Vnb21hYSJ9.zxdJT60ROmBr8hoDZXny252_jlUs3-tpQ3nH4dZ5V_8"
            )
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