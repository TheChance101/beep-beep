package org.thechance.api_gateway.data.model.image


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("data")
    val `data`: Data?,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("status")
    val status: Int?
){
    @Serializable
    data class Data(
        @SerialName("id")
        val id: String?,
        @SerialName("title")
        val title: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("datetime")
        val datetime: Int?,
        @SerialName("type")
        val type: String?,
        @SerialName("animated")
        val animated: Boolean?,
        @SerialName("width")
        val width: Int?,
        @SerialName("height")
        val height: Int?,
        @SerialName("size")
        val size: Int?,
        @SerialName("views")
        val views: Int?,
        @SerialName("bandwidth")
        val bandwidth: Int?,
        @SerialName("vote")
        val vote: String?,
        @SerialName("favorite")
        val favorite: Boolean?,
        @SerialName("nsfw")
        val nsfw: String?,
        @SerialName("section")
        val section: String?,
        @SerialName("account_url")
        val accountUrl: String?,
        @SerialName("account_id")
        val accountId: Int?,
        @SerialName("is_ad")
        val isAd: Boolean?,
        @SerialName("in_most_viral")
        val inMostViral: Boolean?,
        @SerialName("has_sound")
        val hasSound: Boolean?,
        @SerialName("tags")
        val tags: List<String?>?,
        @SerialName("ad_type")
        val adType: Int?,
        @SerialName("ad_url")
        val adUrl: String?,
        @SerialName("edited")
        val edited: String?,
        @SerialName("in_gallery")
        val inGallery: Boolean?,
        @SerialName("deletehash")
        val deletehash: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("link")
        val link: String?
    )
}