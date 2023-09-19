package presentation.main

import presentation.base.BaseScreenModel

class MainScreenModel:
    BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {

}
