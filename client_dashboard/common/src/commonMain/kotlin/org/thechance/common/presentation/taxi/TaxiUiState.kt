package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.CarColor


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isAddNewTaxiDialogVisible: Boolean = false,
    val addNewTaxiDialogUiState: AddTaxiDialogUiState = AddTaxiDialogUiState()
)

data class AddTaxiDialogUiState(
    val taxiPlateNumber: String = "",
    val driverUserName: String = "",
    val carModel: String = "",
    val selectedCarColor: CarColor = CarColor.WHITE,
    val seats: Int = 0
)

fun AddTaxiDialogUiState.toEntity(): AddTaxi {
    return AddTaxi(taxiPlateNumber, driverUserName, carModel, selectedCarColor, seats)
}
