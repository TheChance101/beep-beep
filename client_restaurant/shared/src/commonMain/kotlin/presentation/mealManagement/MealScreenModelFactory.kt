package presentation.mealManagement

import presentation.mealManagement.model.IMealCreationScreenModel
import presentation.mealManagement.model.IMealEditorScreenModel


// todo add to a module for injection
class MealScreenModelFactory {
    fun create(screenMode: ScreenMode, mealId: String): IMealBehavior {
        return when (screenMode) {
            ScreenMode.CREATION -> IMealCreationScreenModel()
            ScreenMode.EDIT -> IMealEditorScreenModel(mealId)
        }
    }
}

