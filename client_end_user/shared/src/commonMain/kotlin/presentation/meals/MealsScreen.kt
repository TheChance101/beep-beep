package presentation.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
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
        val meals = state.meals.collectAsLazyPagingItems()
        println("Meals UI: ${meals.itemSnapshotList.items} ")
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
            PagingList(data = meals){ meal->
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

@Composable
private fun <T:Any> PagingList(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<T>,
    content : @Composable (T?) -> Unit
) {
    LazyColumn(
        modifier =  modifier
            .fillMaxSize()
            .background(Theme.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        when (val loadState = data.loadState.refresh) {
            LoadStateLoading -> {
                item {
                    CircularProgressIndicator()
                }
            }
            is LoadStateNotLoading -> {
                items(data.itemCount) { index ->
                    val item = data[index]
                    content(item)
                }
            }
            is LoadStateError -> {
                item {
                    Text(loadState.error.message ?: "Unknown error")
                }
            }
            else -> error("when should be exhaustive")
        }
    }
}