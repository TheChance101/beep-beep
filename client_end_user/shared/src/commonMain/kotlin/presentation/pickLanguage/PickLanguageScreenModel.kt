package presentation.pickLanguage

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class PickLanguageScreenModel : BaseScreenModel<PickLanguageUIState, PickLanguageUIEffect>(PickLanguageUIState()),PickLanguageInteractionListener{
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onLanguageSelected(language: LanguageUIState) {
        updateState { it.copy(selectedLanguage = language) }
    }

    override fun onGoToPreferredLanguage() {
        if(state.value.selectedLanguage!=null){
            sendNewEffect(PickLanguageUIEffect.onGoToPreferredLanguage)
        }else{
            showSnackBar("Please select a language")
        }
        sendNewEffect(PickLanguageUIEffect.onGoToPreferredLanguage)
    }
    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            updateState { it.copy(snackBarMessage = message, showSnackBar = true) }
            delay(2000)
            updateState { it.copy(showSnackBar = false) }
            delay(300)
            updateState { it.copy(snackBarMessage = "") }
        }
    }
}