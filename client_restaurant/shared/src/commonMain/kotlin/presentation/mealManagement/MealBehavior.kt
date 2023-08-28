package presentation.mealManagement

import presentation.base.BaseScreenModel

abstract class MealBehavior : BaseScreenModel<MealEditorUIState, MealScreenUIEffect>(
    MealEditorUIState()
),
    MealScreenInteractionListener