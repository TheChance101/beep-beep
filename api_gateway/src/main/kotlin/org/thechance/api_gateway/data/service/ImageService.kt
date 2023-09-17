package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.image.ImageResponse
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs

@Single
class ImageService(
    private val client: HttpClient,
    private val attributes: Attributes
) {

    private val clientId = System.getenv("CLIENT_ID").toString()
    suspend fun uploadImage(image: ByteArray): ImageResponse {
        return client.tryToExecute<ImageResponse>(
            api = APIs.IMGUR_API,
            attributes = attributes
        ) {
            post("/3/image") {
                headers {
                    append(HttpHeaders.Authorization, "Client-ID $clientId")
                }
                setBody(
                    MultiPartFormDataContent(formData {
                        append("image", image)
                    })
                )
            }
        }
    }
}