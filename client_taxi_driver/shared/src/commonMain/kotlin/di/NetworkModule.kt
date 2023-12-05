package di

import data.remote.gateway.remote.intercept
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import util.getEngine

val NetworkModule = module {
    single {
        val client = HttpClient(getEngine()) {
            expectSuccess = true

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
            }

            defaultRequest {
                header("Content-Type", "application/json")
                header("Accept-Language", "en")
                url(BASEURL)
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
                val urlBuilder = URLBuilder(
                    protocol = URLProtocol.WSS,
                    host = "beep-beep-api-gateway-nap2u.ondigitalocean.app/",
//    Local         host = "192.168.1.100",
//    Local         port = 8080
                )
                Url(urlBuilder)
                pingInterval = 10000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

        }
        intercept(client)
        client
    }
}
private const val BASEURL = "https://beep-beep-api-gateway-nap2u.ondigitalocean.app/"
