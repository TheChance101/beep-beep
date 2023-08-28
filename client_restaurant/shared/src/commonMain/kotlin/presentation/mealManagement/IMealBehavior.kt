package presentation.mealManagement

import presentation.base.BaseScreenModel

abstract class IMealBehavior : BaseScreenModel<MealEditorUIState, MealScreenUIEffect>(
    MealEditorUIState()
), MealScreenInteractionListener