package presentation.pickLanguage

import presentation.base.BaseInteractionListener

interface PickLanguageInteractionListener :BaseInteractionListener {
    fun onLanguageSelected(language: LanguageUIState)

}