package domain.entity

data class User(
    val addresses: List<Address>,
    val country: String,
    val email: String,
    val fullName: String,
    val id: String,
    val permission: Int,
    val username: String,
    val phoneNumber: String,
    val wallet: Price
)
