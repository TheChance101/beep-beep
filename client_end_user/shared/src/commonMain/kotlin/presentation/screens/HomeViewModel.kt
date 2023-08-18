package presentation.screens


import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

class HomeViewModel : StateScreenModel<HomeUiState>(HomeUiState()){

    init {
        initializeState()
    }

    private fun initializeState() {
        mutableState.update { it.copy(text = "aya") }
    }

}