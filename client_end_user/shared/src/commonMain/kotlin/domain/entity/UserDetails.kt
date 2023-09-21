package domain.entity



data class UserDetails(
    val addresses: List<Address>?,
    val country: String?,
    val currency: String?,
    val email: String?,
    val fullName: String?,
    val id: String?,
    val permission: Int?,
    val username: String?,
    val walletBalance: Double?,
    val phoneNumber: String?
)