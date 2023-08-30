package di

import data.gateway.remote.intercept
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

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
                header("Accept-Language", "en")
                url("http://0.0.0.0:8080")
            }

            install(ContentNegotiation) {
                json()
            }

        }
        intercept(client)
        client
    }
}

