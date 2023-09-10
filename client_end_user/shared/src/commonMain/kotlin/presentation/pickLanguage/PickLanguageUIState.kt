package presentation.pickLanguage

data class PickLanguageUIState(
    val isLoading: Boolean = false,
    val languages: List<LanguageUIState> = emptyList(),
    val selectedLanguage: LanguageUIState = LanguageUIState(),
    val  snackBarMessage: String = "",
    val showSnackBar: Boolean = false
)

data class LanguageUIState(
    val name: String = "",
    val code: String = "",
    val image: String = ""
)
