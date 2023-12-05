package domain.entity

data class TaxiRequestPermission(
    val driverFullName: String,
    val driverEmail: String,
    val description: String,
)