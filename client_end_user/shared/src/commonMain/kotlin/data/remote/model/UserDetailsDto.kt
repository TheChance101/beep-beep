package data.remote.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserDetailsDto(
    @SerialName("id") val id: String?,
    @SerialName("fullName") val fullName: String? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("phone") val phoneNumber: String? = null,
    @SerialName("permission") val permission: Int? = null,
    @SerialName("walletBalance") val walletBalance: Double? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("addresses") val addresses: List<AddressDto?>?=  null,
)
