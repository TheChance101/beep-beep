package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.image.ImageResponse
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs
import java.io.InputStream

@Single
class ImageService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {

    private val clientId = System.getenv("CLIENT_ID").toString()
    suspend fun uploadImage(imageFile: InputStream): ImageResponse {
        return client.tryToExecute<ImageResponse>(
            api = APIs.IMAUR_API,
            attributes = attributes
        ) {
            post("/3/image") {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Image.Any)
                    append(HttpHeaders.Authorization, "Client-ID $clientId")
                }
                setBody(
                    MultiPartFormDataContent(formData {
                        append("image", imageFile.readBytes())
                    })
                )
            }
        }
    }
}