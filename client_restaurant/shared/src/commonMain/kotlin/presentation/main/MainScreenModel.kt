package presentation.main

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class MainScreenModel : StateScreenModel<MainScreenUIState>(MainScreenUIState()), KoinComponent {



    private fun updateState(update: (MainScreenUIState) -> MainScreenUIState) {
        mutableState.update(update)
    }
}