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
            { manageLanguage.saveLanguageCode(language.code) },
            ::onSavedLanguageSuccess,
            ::onError
        )
    }

    private fun onSavedLanguageSuccess(unit:Unit) {
        sendNewEffect(PickLanguageUIEffect.onGoToPreferredLanguage)
    }

    private fun onError(errorState: ErrorState) {
        println(errorState)
    }
}