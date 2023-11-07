package presentation.main

data class MainUiState(
    val isLoading: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarMessage: String = ""
)
