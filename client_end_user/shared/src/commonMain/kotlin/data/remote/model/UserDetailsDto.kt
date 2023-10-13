package data.remote.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserDetailsDto(
    @SerialName("id")
    val id: String?,
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("username")
    val username: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("walletBalance")
    val walletBalance: Double?,
    @SerialName("currency")
    val currency: String?,
    @SerialName("addresses")
    val addresses: List<AddressDto?>?,
    @SerialName("country")
    val country: String?,
    @SerialName("permission")
    val permission: Int?,
    @SerialName("phoneNumber")
    val phoneNumber: String?
)