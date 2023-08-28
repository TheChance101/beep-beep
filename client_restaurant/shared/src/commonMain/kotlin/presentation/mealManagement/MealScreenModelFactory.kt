package presentation.mealManagement

import presentation.mealManagement.model.MealCreationScreenModel
import presentation.mealManagement.model.MealEditorScreenModel


// todo add to a module for injection
class MealScreenModelFactory {
    fun create(screenMode: ScreenMode, mealId: String): MealBehavior {
        return when (screenMode) {
            ScreenMode.CREATION -> MealCreationScreenModel()
            ScreenMode.EDIT -> MealEditorScreenModel(mealId)
        }
    }
}

