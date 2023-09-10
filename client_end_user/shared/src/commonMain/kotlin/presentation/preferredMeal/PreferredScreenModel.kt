package presentation.preferredMeal

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class PreferredScreenModel() :
    BaseScreenModel<PreferredScreenUiState, PreferredScreenUiEffect>(PreferredScreenUiState()),
    PreferredScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

}