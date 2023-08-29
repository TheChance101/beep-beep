package presentation.mealManagement.model

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IManageMealUseCase
import domain.usecase.IMangeCuisineUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.mealManagement.IMealBehavior
import presentation.mealManagement.toMealAddition

class IMealCreationScreenModel : IMealBehavior() {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IMangeCuisineUseCase by inject()


    override suspend fun addMeal(): Boolean {
        return manageMeal.addMeal(state.value.meal.toMealAddition())
    }

    override suspend fun getCuisiness(): List<Cuisine> {
        updateState { it.copy(isLoading = true) }
        return cuisines.getCuisines()
    }


}
