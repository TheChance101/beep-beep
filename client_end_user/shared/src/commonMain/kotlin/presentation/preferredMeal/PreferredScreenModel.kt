package presentation.preferredMeal

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import domain.usecase.IMangeUserPreferenceUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class PreferredScreenModel(
    private val manageUser: IManageUserUseCase,
    private val userPreferences: IMangeUserPreferenceUseCase,
    ) :
    BaseScreenModel<PreferredScreenUiState, PreferredScreenUiEffect>(PreferredScreenUiState()),
    PreferredScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onClickPreferredMeal(priceLevel: String) {
        savePriceLevel(priceLevel)
        saveFirstTimePreferences()
    }


    private fun savePriceLevel(priceLevel: String) {
        tryToExecute(
            { userPreferences.savePriceLevel(priceLevel) },
            ::onSavePriceLevelSuccess,
            ::onSavePriceLevelError
        )
    }

    private fun saveFirstTimePreferences() {
        tryToExecute(
            { manageUser.saveIsFirstTimeUseApp(false) },
            ::onSavePriceLevelSuccess,
            ::onSavePriceLevelError
        )
    }

    private fun onSavePriceLevelError(errorState: ErrorState) {
        println("$errorState")
    }

    private fun onSavePriceLevelSuccess(unit: Unit) {
        sendNewEffect(PreferredScreenUiEffect.NavigateToHomeScreen)
    }
}
