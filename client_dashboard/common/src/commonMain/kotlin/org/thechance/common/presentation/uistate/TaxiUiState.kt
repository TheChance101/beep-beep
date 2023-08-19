package org.thechance.common.presentation.uistate


data class TaxiUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val taxis: List<TaxiDetailsUiState> = emptyList(),
    val searchQuery: String = "",
)

data class TaxiDetailsUiState(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
    val username: String = "",
    val status: String = "",
    val trips: String = "1",
)

