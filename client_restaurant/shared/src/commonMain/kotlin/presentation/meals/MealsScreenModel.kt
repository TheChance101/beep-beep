package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IMangeCousinUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.meals.MealsUIState.CousinUIState
import presentation.meals.MealsUIState.MealsScreenUIState
import presentation.meals.MealsUIState.toCousinUIState
import presentation.meals.MealsUIState.toUIState

class MealsScreenModel :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener  {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val mangeCousin: IMangeCousinUseCase by inject()

    init {
        getCousin()
    }

    private fun getCousin() {
        tryToExecute(
            function = {
                mangeCousin.getCousins("1")

            },
            onSuccess = { value ->
                updateState {
                    it.copy(
                        cousin = value.toCousinUIState(),
                        selectedCousin = value[0].toCousinUIState(),
                    )
                }
                getMeals(value[0].id)

            },
            onError = {
                updateState { it.copy(error = it.error) }
            }
        )
    }

    private fun getMeals(id: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                mangeCousin.getMealsByCousinId(id)
            },
            onSuccess = { value ->
                updateState { it.copy(meals = value.toUIState(), isLoading = false) }
            },
            onError = {
                updateState { it.copy(error = it.error, isLoading = false) }
            }
        )
    }



    override fun onClickBack() {
        sendNewEffect(MealsScreenUIEffect.Back)
    }


    override fun onClickCousinType(type: CousinUIState) {
        updateState { it.copy(selectedCousin = type) }
        getMeals(type.id)
    }
}
