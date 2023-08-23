package org.thechance.common.presentation.taxi


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.util.ErrorState

data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
    val isAddNewTaxiDialogVisible: Boolean = false,
    val addNewTaxiDialogUiState: AddTaxiDialogUiState = AddTaxiDialogUiState(),
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
        Header("Car color", 3f),
        Header("Seats", 3f),
        Header("Trips", 3f),
        Header("", 1f),
    )
}

data class TaxiDetailsUiState(
    val id: String,
    val plateNumber: String,
    val color: CarColor,
    val type: String,
    val seats: Int = 4,
    val username: String = "",
    val status: TaxiStatus = TaxiStatus.ONLINE,
    val trips: String = "1",
) {
    val statusColor: Color
        @Composable get() = when (status) {
            TaxiStatus.OFFLINE -> Theme.colors.primary
            TaxiStatus.ONLINE -> Theme.colors.success
            TaxiStatus.ON_RIDE -> Theme.colors.warning
        }
    val statusText: String
        get() = when (status) {
            TaxiStatus.OFFLINE -> "Offline"
            TaxiStatus.ONLINE -> "Online"
            TaxiStatus.ON_RIDE -> "On Ride"
        }
}


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

data class AddTaxiDialogUiState(
    val plateNumber: String = "",
    val driverUserName: String = "",
    val carModel: String = "",
    val selectedCarColor: CarColor = CarColor.WHITE,
    val seats: Int = 0
)

fun AddTaxiDialogUiState.toEntity() = AddTaxi(plateNumber, driverUserName, carModel, selectedCarColor, seats)

