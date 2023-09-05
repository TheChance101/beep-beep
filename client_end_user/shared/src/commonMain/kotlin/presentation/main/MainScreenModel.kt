package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCuisinesUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.home.HomeScreenUiEffect
import presentation.home.HomeScreenUiState


class MainScreenModel : BaseScreenModel<MainUiState, MainScreenUiEffect>(MainUiState()),
    MainInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {

    }

    override fun onLoginClicked() {
        TODO("Not yet implemented")
    }


}
