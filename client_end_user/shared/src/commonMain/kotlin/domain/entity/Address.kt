package domain.entity


data class Address(
    val id: String,
    val address: String,
    val location: Location? = null,
)
