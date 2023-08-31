package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
)

