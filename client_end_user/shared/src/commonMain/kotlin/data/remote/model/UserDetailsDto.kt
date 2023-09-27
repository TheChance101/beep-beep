package data.remote.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserDetailsDto(
    @SerialName("addresses")
    val addresses: List<AddressDto?>?,
    @SerialName("country")
    val country: String?,
    @SerialName("currency")
    val currency: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("permission")
    val permission: Int?,
    @SerialName("username")
    val username: String?,
    @SerialName("walletBalance")
    val walletBalance: Double?,
    @SerialName("phoneNumber")
    val phoneNumber: String?

)