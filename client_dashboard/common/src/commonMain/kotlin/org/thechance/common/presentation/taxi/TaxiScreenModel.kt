package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.presentation.uistate.TaxiDetailsUiState
import org.thechance.common.presentation.uistate.TaxiUiState


class TaxiScreenModel : StateScreenModel<TaxiUiState>(TaxiUiState()) {


    init {
        getDummyTaxiData()
    }
    private fun getDummyTaxiData() {
        mutableState.update { it.copy(taxis = listOf(
                    TaxiDetailsUiState("1", "ABC123", 1, "Sedan", true, 4, "asia", "Online", "12"),
                    TaxiDetailsUiState("2", "XYZ789", 2, "SUV", false, 6, "asia", "Online", "12"),
                    TaxiDetailsUiState(
                        "3",
                        "DEF456",
                        1,
                        "Hatchback",
                        true,
                        4,
                        "asia",
                        "On ride",
                        "12"
                    ),
                    TaxiDetailsUiState(
                        "4",
                        "GHI789",
                        1,
                        "Minivan",
                        true,
                        6,
                        "asia",
                        "On ride",
                        "12"
                    ),
                    TaxiDetailsUiState(
                        "5",
                        "JKL012",
                        1,
                        "Convertible",
                        false,
                        6,
                        "asia",
                        "Online",
                        "12"
                    ),
                    TaxiDetailsUiState(
                        "21",
                        "MNO345",
                        1,
                        "Sedan",
                        true,
                        4,
                        "asia",
                        "On ride",
                        "12"
                    ),
                    TaxiDetailsUiState("22", "PQR678", 1, "SUV", false, 6, "asia", "Online", "12"),
                    TaxiDetailsUiState(
                        "23",
                        "STU901",
                        1,
                        "Hatchbac",
                        true,
                        4,
                        "asia",
                        "Offline",
                        "12"
                    ),
                    TaxiDetailsUiState(
                        "24",
                        "VWX234",
                        1,
                        "Minivan",
                        true,
                        4,
                        "asia",
                        "Offline",
                        "12"
                    ),
                    TaxiDetailsUiState(
                        "25",
                        "YZA567",
                        1,
                        "Convertible",
                        false,
                        2,
                        "asia",
                        "Offline",
                        "12"
                    )
                )) }
    }

    fun onSearchInputChange(searchQuery: String) {
        mutableState.update { it.copy(searchQuery = searchQuery) }
    }

}