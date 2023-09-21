package presentation.mealManagement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.base.BaseScreen
import presentation.composables.CustomBottomSheet
import presentation.composables.ModalBottomSheetState
import presentation.mealManagement.composable.CuisineBottomSheet
import presentation.mealManagement.composable.MealContent
import resources.Resources
import util.ImagePickerFactory
import util.getPlatformContext

class MealScreen(
    private val screenMode: ScreenMode,
    private val mealId: String = "",
    private val restaurantId: String = ""
) :
    BaseScreen<IMealBehavior, MealEditorUIState, MealScreenUIEffect, MealScreenInteractionListener>(),
    KoinComponent {


    private val mealScreenModelFactory: MealScreenModelFactory by inject()

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { mealScreenModelFactory.create(screenMode, mealId,restaurantId) }
        initScreen(screenModel)
        println("mealId: $mealId")
    }

    @Composable
    override fun onRender(state: MealEditorUIState, listener: MealScreenInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }

        val titlesMap = mapOf(
            ScreenMode.CREATION to Pair(Resources.strings.addNewMeal, Resources.strings.add),
            ScreenMode.EDIT to Pair(Resources.strings.editMeal, Resources.strings.save)
        )

        val (screenTitle, buttonTitle) = titlesMap[screenMode] ?: Pair("", "")


        CustomBottomSheet(
            sheetContent = {
                CuisineBottomSheet(
                    cuisines = state.cuisines,
                    listener = listener
                )
            },
            sheetBackgroundColor = Theme.colors.background,
            onBackGroundClicked = { listener.onBackgroundClicked() },
            sheetState = sheetState,
        ) {
            println("meal state : ${state.meal}")
            MealContent(
                state.meal,
                listener,
                screenTitle = screenTitle,
                buttonTitle = buttonTitle,
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
            is MealScreenUIEffect.MealResponseFailed -> {}
        }
    }
}
