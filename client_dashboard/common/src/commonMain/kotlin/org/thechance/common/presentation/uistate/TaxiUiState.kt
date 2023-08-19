package org.thechance.common.presentation.uistate


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isAddNewTaxiDialogVisible : Boolean = false
)
