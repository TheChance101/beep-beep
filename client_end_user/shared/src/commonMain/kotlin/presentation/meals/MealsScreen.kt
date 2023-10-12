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
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BottomSheet
import presentation.composable.MealBottomSheet
import presentation.composable.MealCard
import presentation.composable.ModalBottomSheetState
import presentation.composable.modifier.noRippleEffect
import presentation.resturantDetails.Composable.NeedToLoginSheet
import presentation.resturantDetails.MealUIState
import presentation.search.SearchUiEffect
import resources.Resources
import util.getNavigationBarPadding
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
            is MealsUiEffect.NavigateToLogin -> navigator.push(LoginScreen())
        }
    }

    @Composable
    override fun onRender(state: MealsUiState, listener: MealsInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        BottomSheet(
            sheetContent = {
                if (state.showMealSheet) {
                    MealBottomSheet(
                        modifier = Modifier.padding(getNavigationBarPadding()),
                        meal = state.selectedMeal,
                        onAddToCart = listener::onAddToCart,
                        onDismissSheet = listener::onDismissSheet,
                        onIncreaseQuantity = listener::onIncreaseMealQuantity,
                        onDecreaseQuantity = listener::onDecreaseMealQuantity
                    )
                } else if (state.showLoginSheet) {
                    NeedToLoginSheet(
                        modifier = Modifier.padding(getNavigationBarPadding()),
                        text = Resources.strings.loginToAddToFavourite,
                        onClick = listener::onLoginClicked,
                    )
                }
            },
            sheetBackgroundColor = Theme.colors.background,
            onBackGroundClicked = listener::onDismissSheet,
            sheetState = sheetState,
            content = {
                content(
                    state,
                    onBackClicked = listener::onBackClicked,
                    onMealSelected = listener::onMealClicked
                )
            }
        )

        LaunchedEffect(state.showMealSheet, state.showLoginSheet) {
            if (state.showMealSheet || state.showLoginSheet) {
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
        onMealSelected: (MealUIState) -> Unit,
        onBackClicked: () -> Unit
    ) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.background).padding(
                getNavigationBarPadding()
            )
        ) {
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
                    MealCard(
                        meal = meal,
                        modifier = Modifier.noRippleEffect { onMealSelected(meal) })
                }
            }
        }
    }
}
