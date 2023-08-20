package org.thechance.common.presentation.taxi


import org.thechance.common.domain.entity.Taxi
import org.thechance.common.presentation.composables.table.Header


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val taxis: List<TaxiDetailsUiState> = emptyList(),
    val searchQuery: String = "",
    val taxiNumberInPage: String = "3",
) {
    val tabHeader = listOf(
        Header("No.", 1f),
        Header("Plate number", 3f),
        Header("Driver username", 3f),
        Header("Status", 3f),
        Header("Car model", 3f),
        Header("car color", 3f),
        Header("Seats", 3f),
        Header("Trips", 3f),
        Header("", 1f),
    )
}

data class TaxiDetailsUiState(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val seats: Int = 4,
    val username: String = "",
    val status: TaxiStatus = TaxiStatus.ONLINE,
    val trips: String = "1",
)

fun Taxi.toUiState(): TaxiDetailsUiState = TaxiDetailsUiState(
    id = id,
    plateNumber = plateNumber,
    color = color,
    type = type,
    seats = seats,
    username = username,
    status = status,
    trips = trips,
)

fun List<Taxi>.toUiState() = map { it.toUiState() }
enum class TaxiStatus( val status: String) {
    OFFLINE("Offline"),
    ONLINE("Online"),
    ON_RIDE("On ride"),
}