package presentation.screens


import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class HomeViewModel : StateScreenModel<HomeUiState>(HomeUiState()), KoinComponent {

    init {
        initializeState()
    }

    private fun initializeState() {
        mutableState.update { it.copy(text = "aya") }
    }

}