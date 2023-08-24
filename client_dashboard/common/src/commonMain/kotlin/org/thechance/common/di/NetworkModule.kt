package org.thechance.common.di

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import org.koin.dsl.module


val NetworkModule = module {
    single {
        HttpClient(OkHttp) {

            expectSuccess = true

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            defaultRequest {

                header("Content-Type", "application/json")

                url("http://0.0.0.0:8080")

            }

            install(ContentNegotiation) {
                gson()
            }
        }
    }
}