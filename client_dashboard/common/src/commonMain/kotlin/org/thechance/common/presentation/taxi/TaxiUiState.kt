package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.CarColor


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val addTaxiDialogUiState: AddTaxiDialogUiState = AddTaxiDialogUiState()
)

data class AddTaxiDialogUiState(
    val isAddNewTaxiDialogVisible: Boolean = false,
    val taxiPlateNumber: String = "",
    val driverUserName: String = "",
    val carModel: String = "",
    val selectedCarColor: CarColor = CarColor.WHITE,
    val seats: Int = 0
)

fun AddTaxiDialogUiState.toEntity(): AddTaxi {
    return AddTaxi(isAddNewTaxiDialogVisible, taxiPlateNumber, driverUserName, carModel, selectedCarColor, seats)
}
