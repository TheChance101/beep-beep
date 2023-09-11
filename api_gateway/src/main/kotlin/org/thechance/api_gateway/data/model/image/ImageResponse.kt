package org.thechance.api_gateway.data.model.image


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("data")
    val data: Data?,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("status")
    val status: Int?
){
    @Serializable
    data class Data(
        @SerialName("id")
        val id: String?,
        @SerialName("type")
        val type: String?,
        @SerialName("link")
        val link: String?
    )
}