package presentation.PreferredFood

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class PreferredFoodScreenModel() :BaseScreenModel<PreferredFoodUIState, PreferredFoodUIEffect,>(PreferredFoodUIState()),PreferredFoodInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onPreferredFoodClicked() {
        tryToExecute(
            function = {
//                manageLanguage.saveLanguageCode(language.code)
            },
            onSuccess = {
                onSavedPreferredFoodSuccess()
            },
            ::onSavedPreferredFoodError
        )
    }
    private fun onSavedPreferredFoodSuccess() {
        sendNewEffect(PreferredFoodUIEffect.NavigateToPreferredScreen)
    }

    private fun onSavedPreferredFoodError(errorState: ErrorState) {
        println(errorState.toString())
    }

}