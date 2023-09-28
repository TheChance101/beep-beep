package presentation.SearchTaxi

data class SearchTaxiUIState(
    val searchTerm: String = "",
    val isSearchButtonEnabled: Boolean = false,
    val result : List<SearchResultUIState>   = emptyList(),
)

data class SearchResultUIState(
    val country: String = "",
    val city: String = "",
    val address: String = "",
)
