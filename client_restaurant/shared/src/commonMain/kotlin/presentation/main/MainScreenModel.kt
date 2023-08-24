package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MainScreenModel : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()),
    MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        updateState {
            it.copy(
                restaurantName = "Restaurant Name", charts = listOf(
                    ChartItemUiState(
                        title = "Revenue",
                        points = listOf(
                            "Mon" to 100,
                            "Tue" to 200,
                            "Wed" to 300,
                            "Thu" to 400,
                            "Fri" to 500,
                            "Sat" to 600,
                            "Sun" to 700
                        ),
                        totalThisWeek = 4700,
                        sign = '$',
                        isRevenue = true
                    ),
                    ChartItemUiState(
                        title = "Orders",
                        points = listOf(
                            "Mon" to 10,
                            "Tue" to 20,
                            "Wed" to 30,
                            "Thu" to 40,
                            "Fri" to 50,
                            "Sat" to 60,
                            "Sun" to 70
                        ),
                        totalThisWeek = 280,
                        sign = null,
                        isRevenue = false
                    ),
                )
            )
        }
    }

    override fun onClickBack() {
        sendNewEffect(MainScreenUIEffect.Back)
    }
}