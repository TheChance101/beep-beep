package presentation.mealManagement.mealCreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composables.CustomBottomSheet
import presentation.composables.ModalBottomSheetState
import presentation.mealManagement.MealScreenInteractionListener
import presentation.mealManagement.MealScreenUIEffect
import presentation.mealManagement.composable.CuisineBottomSheet
import presentation.mealManagement.composable.MealContent
import resources.Resources
import util.ImagePickerFactory
import util.getPlatformContext

class MealCreationScreen :
    BaseScreen<MealCreationScreenModel, MealCreationUIState, MealScreenUIEffect, MealScreenInteractionListener>() {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MealCreationScreenModel() }
        initScreen(screenModel)
    }

    @Composable
    override fun onRender(state: MealCreationUIState, listener: MealScreenInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        CustomBottomSheet(
            sheetContent = {
                CuisineBottomSheet(
                    cuisines = state.cuisines,
                    onCancelClick = {
                        listener.onCuisinesCancel()
                        sheetState.dismiss()
                    },
                    onSaveClick = {
                        listener.onSaveCuisineClick()
                        sheetState.dismiss()
                    },
                    onItemSelected = listener::onCuisineSelected
                )
            },
            sheetBackgroundColor = Theme.colors.background,
            sheetState = sheetState,
        ) {
            MealContent(
                state.meal,
                listener,
                screenTitle = Resources.strings.addNewMeal,
                buttonTitle = Resources.strings.add,
                imagePicker = ImagePickerFactory(context = getPlatformContext()).createPicker()
            )
        }

        LaunchedEffect(state.isCuisinesShow) {
            if (state.isCuisinesShow) {
                sheetState.show()
            } else {
                sheetState.dismiss()
            }
        }
    }

    override fun onEffect(effect: MealScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MealScreenUIEffect.Back -> {
                navigator.pop()

            }

            is MealScreenUIEffect.MealResponseSuccessfully -> navigator.pop()
            else -> {}
        }
    }
}
