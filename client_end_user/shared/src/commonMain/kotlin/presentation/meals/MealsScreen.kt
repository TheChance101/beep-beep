package presentation.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpPagingList
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
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
import com.beepbeep.designSystem.ui.composable.BpToastMessage
import presentation.resturantDetails.MealUIState
import resources.Resources
import util.getNavigationBarPadding

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

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: MealsUiState, listener: MealsInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
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

            LaunchedEffect(state.showToast) {
                if (state.showToast) {
                    delay(2000)
                    listener.onDismissSnackBar()
                }
            }
            BpToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
                state = state.showToast,
                message = if (state.errorAddToCart == null)
                    Resources.strings.mealAddedToYourCart else Resources.strings.mealFailedToAddInCart,
                isError = state.errorAddToCart != null,
                successIcon = painterResource(Resources.images.unread),
                warningIcon = painterResource(Resources.images.warningIcon)
            )
        }

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
        onBackClicked: () -> Unit,
    ) {
        val meals = state.meals.collectAsLazyPagingItems()
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
            BpPagingList(data = meals) { meal ->
                meal?.let {
                    MealCard(
                        meal = it,
                        modifier = Modifier.noRippleEffect { onMealSelected(it) }
                    )
                }
            }
        }
    }
}
