package di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import org.koin.dsl.module

val NetworkModule =  module {
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
        client
    }
}