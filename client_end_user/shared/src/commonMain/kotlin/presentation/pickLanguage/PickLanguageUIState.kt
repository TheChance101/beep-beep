package presentation.pickLanguage

import util.langs

data class PickLanguageUIState(
    val isLoading: Boolean = false,
    val languages: List<LanguageUIState> = langs,
    val selectedLanguage: LanguageUIState = LanguageUIState(),
)

data class LanguageUIState(
    val name: String = "",
    val code: String = "",
    val image: String = ""
)


