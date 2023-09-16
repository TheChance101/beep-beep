package org.thechance.common.presentation.taxi


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.domain.util.TaxiStatus.ONLINE
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState

data class TaxiUiState(
    val isLoading: Boolean = true,
    val isNoInternetConnection: Boolean = false,
    val isAddNewTaxiDialogVisible: Boolean = false,
    val newTaxiInfo: TaxiInfoUiState = TaxiInfoUiState(),
    val taxiFilterUiState: TaxiFilterUiState = TaxiFilterUiState(),
    val taxis: List<TaxiDetailsUiState> = emptyList(),
    val searchQuery: String = "",
    val isReportExportedSuccessfully: Boolean = false,
    val pageInfo: TaxiPageInfoUiState = TaxiPageInfoUiState(),
    val specifiedTaxis: Int = 10,
    val currentPage: Int = 1,
    val taxiMenu: MenuUiState = MenuUiState(),
    val isFilterDropdownMenuExpanded: Boolean = false,
    val isEditMode: Boolean = false,

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
    val id: String = "",
    val plateNumber: String = "",
    val color: CarColor = CarColor.WHITE,
    val type: String = "",
    val seats: Int = 4,
    val username: String = "",
    val status: TaxiStatus = ONLINE,
    val trips: String = "1",
) {
    val statusColor: Color
        @Composable get() = when (status) {
            TaxiStatus.OFFLINE -> Theme.colors.primary
            TaxiStatus.ONLINE -> Theme.colors.success
        }
}

data class TaxiFilterUiState(
    val carColor: CarColor? = null,
    val seats: Int = -1,
    val status: TaxiStatus? = null
) {
    fun toEntity(): TaxiFiltration {
        return TaxiFiltration(
            carColor = carColor,
            seats = seats,
            status = status
        )
    }
}

@Composable
fun TaxiStatus.getStatusName(): String {
    return when (this) {
        TaxiStatus.OFFLINE -> Resources.Strings.offline
        TaxiStatus.ONLINE -> Resources.Strings.online
    }
}

data class TaxiPageInfoUiState(
    val data: List<TaxiDetailsUiState> = emptyList(),
    val numberOfTaxis: Int = 0,
    val totalPages: Int = 0,
)

data class TaxiInfoUiState(
    val id: String = "",
    val plateNumber: String = "",
    val driverUserName: String = "",
    val carModel: String = "",
    val selectedCarColor: CarColor = CarColor.WHITE,
    val seats: Int = 0,
    val plateNumberError: ErrorWrapper = ErrorWrapper(),
    val carModelError: ErrorWrapper = ErrorWrapper(),
    val driverUserNameError: ErrorWrapper = ErrorWrapper(),
    val isFormValid: Boolean = false,
)

data class MenuUiState(
    val id: String = "",
    val items: List<MenuItemUiState> = listOf(
        MenuItemUiState(
            iconPath = "ic_edit.xml",
            text = "Edit",
        ),
        MenuItemUiState(
            iconPath = "ic_delete.svg",
            text = "Delete",
            isSecondary = true,
        ),
    )
) {
    data class MenuItemUiState(
        val iconPath: String = "",
        val text: String = "",
        val isSecondary: Boolean = false,
    )
}


fun DataWrapper<Taxi>.toDetailsUiState(): TaxiPageInfoUiState {
    return TaxiPageInfoUiState(
        data = result.toDetailsUiState(),
        totalPages = totalPages,
        numberOfTaxis = numberOfResult
    )
}

fun Taxi.toDetailsUiState(): TaxiDetailsUiState = TaxiDetailsUiState(
    id = id,
    plateNumber = plateNumber,
    color = color,
    type = type,
    seats = seats,
    username = username,
    status = status,
    trips = trips,
)

fun List<Taxi>.toDetailsUiState() = map { it.toDetailsUiState() }

fun TaxiInfoUiState.toEntity() = NewTaxiInfo(
    plateNumber = plateNumber,
    driverUserName = driverUserName,
    carModel = carModel,
    selectedCarColor = selectedCarColor,
    seats = seats
)

fun TaxiDetailsUiState.toEntity(): Taxi = Taxi(
    id = id,
    plateNumber = plateNumber,
    color = color,
    type = type,
    seats = seats,
    username = username,
    status = status,
    trips = trips,
)

fun List<TaxiDetailsUiState>.toEntity() = map { it.toEntity() }

fun Taxi.toUiState(): TaxiInfoUiState = TaxiInfoUiState(
    id = id,
    plateNumber = plateNumber,
    driverUserName = username,
    carModel = type,
    selectedCarColor = color,
    seats = seats
)