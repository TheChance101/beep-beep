package org.thechance.api_gateway.data.service

import aws.sdk.kotlin.runtime.auth.credentials.EnvironmentCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.DeleteObjectRequest
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.net.Url
import org.koin.core.annotation.Single

@Single
class ImageService {
    suspend fun uploadImage(
        image: ByteArray,
        name: String,
        oldImageUrl: String? = null
    ): String {
        val fileName = "${name.replace(" ", "_")}${Math.random()}.png"
        val bucketName = "beepbeep-resource"
        val imageUrl = "https://beepbeep-resource.fra1.digitaloceanspaces.com"

        val metadataVal = mutableMapOf<String, String>()

        val s3Client = S3Client.fromEnvironment {
            endpointUrl = Url.parse("https://fra1.digitaloceanspaces.com")
            region = "us-east-1"
            credentialsProvider = EnvironmentCredentialsProvider()

        }

        if (oldImageUrl != null) {
            val deleteObjectRequest = DeleteObjectRequest {
                bucket = bucketName
                key = oldImageUrl.replace("$imageUrl/", "")
            }
            val deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest)
        }

        val putObjectRequest = PutObjectRequest {
            bucket = bucketName
            key = fileName
            acl = ObjectCannedAcl.fromValue("public-read")
            metadata = metadataVal
            body = ByteStream.fromBytes(image)
        }

        val putObjectResponse = s3Client.putObject(putObjectRequest)
        println("Tag information is ${putObjectResponse.eTag}")

        return "$imageUrl/$fileName"
    }
}