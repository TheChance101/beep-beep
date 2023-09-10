package presentation.pickLanguage

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import domain.usecase.IMangeLanguageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class PickLanguageScreenModel(
    private val manageLanguage: IMangeLanguageUseCase
) : BaseScreenModel<PickLanguageUIState, PickLanguageUIEffect>(PickLanguageUIState()),
    PickLanguageInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    override fun onLanguageSelected(language: LanguageUIState) {
        tryToExecute(
            function = {
                manageLanguage.saveLanguageCode(language.code)
            },
            onSuccess = {
                onSavedLanguageSuccess()
            },
            ::onSavedLanguageError
        )

    }
    private fun onSavedLanguageSuccess() {
        sendNewEffect(PickLanguageUIEffect.onGoToPreferredLanguage)
    }

    private fun onSavedLanguageError(errorState: ErrorState) {
        showSnackBar(errorState.toString())
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