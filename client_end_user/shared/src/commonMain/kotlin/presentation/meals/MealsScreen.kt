package presentation.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BottomSheet
import presentation.composable.MealBottomSheet
import presentation.composable.MealCard
import presentation.composable.ModalBottomSheetState
import presentation.resturantDetails.MealUIState
import resources.Resources
import util.getStatusBarPadding

class MealsScreen(private val cuisineId: String, private val cuisineName: String) :
    BaseScreen<MealsScreenModel, MealsUiState, MealsUiEffect, MealsInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(cuisineId, cuisineName) })
    }

    override fun onEffect(effect: MealsUiEffect, navigator: Navigator) {
        when (effect) {
            is MealsUiEffect.NavigateBack -> navigator.pop()
        }
    }

    @Composable
    override fun onRender(state: MealsUiState, listener: MealsInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        BottomSheet(
            sheetContent = {
                MealBottomSheet(
                    meal = state.selectedMeal,
                    onDismissSheet = listener::onDismissSheet,
                    onDecreaseQuantity = {},
                    onIncreaseQuantity = {},
                    onAddToCart = {}
                )
            },
            sheetBackgroundColor = Theme.colors.background,
            onBackGroundClicked = listener::onDismissSheet,
            sheetState = sheetState,
            content = { content(state, onBackClicked = listener::onBackClicked) }
        )

        LaunchedEffect(state.showMealSheet) {
            if (state.showMealSheet) {
                sheetState.show()
            } else {
                sheetState.dismiss()
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun content(
        state: MealsUiState,
        onBackClicked: () -> Unit
    ) {
        Column(Modifier.fillMaxSize().background(Theme.colors.background)) {
            BpAppBar(
                title = state.cuisineName,
                onNavigateUp = onBackClicked,
                painterResource = painterResource(Resources.images.arrowLeft)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(state.meals) { meal ->
                    MealCard(meal = meal)
                }
            }
        }
    }
}
