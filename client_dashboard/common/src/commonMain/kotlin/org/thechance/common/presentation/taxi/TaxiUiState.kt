package org.thechance.common.presentation.taxi


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isAddNewTaxiDialogVisible: Boolean = false,
    val taxiPlateNumber: String = "",
    val driverUserName: String = "",
    val carModel: String = "",
    val selectedCarColor: CarColor = CarColor.WHITE,
    val seats: Int = 0
)

enum class CarColor {
    RED, YELLOW, GREEN, BLUE, WHITE, GREY, BLACK
}
