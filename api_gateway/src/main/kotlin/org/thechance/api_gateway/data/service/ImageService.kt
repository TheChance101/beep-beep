package org.thechance.api_gateway.data.service

import aws.sdk.kotlin.runtime.auth.credentials.EnvironmentCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.net.Url
import org.koin.core.annotation.Single

@Single
class ImageService {
    suspend fun uploadImage(image: ByteArray, name: String): String {
        val fileName = "${name.replace(" ", "_")}.png"
        val bucketName = "beepbeep-resource"
        val imageUrl = "https://beepbeep-resource.fra1.digitaloceanspaces.com"

        val metadataVal = mutableMapOf<String, String>()
        metadataVal["myVal"] = "test"

        val s3Client = S3Client.fromEnvironment {
            endpointUrl = Url.parse("https://fra1.digitaloceanspaces.com")
            region = "us-east-1"
            credentialsProvider = EnvironmentCredentialsProvider()

        }
        val request = PutObjectRequest {
            bucket = bucketName
            key = fileName
            acl = ObjectCannedAcl.fromValue("public-read")
            metadata = metadataVal
            body = ByteStream.fromBytes(image)
        }

        val response = s3Client.putObject(request)
        println("Tag information is ${response.eTag}")

        return "$imageUrl/$fileName"
    }
}