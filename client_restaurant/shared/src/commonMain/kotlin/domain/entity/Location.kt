package domain.entity

data class Location(
    val latitude: Double,
    val longitude: Double,
)
data class AddressInfo(
    val location: Location,
    val address: String
)