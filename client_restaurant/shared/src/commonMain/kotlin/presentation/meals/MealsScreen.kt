package presentation.meals

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.NoItemsPlaceholder
import presentation.composable.MealCard
import presentation.mealManagement.MealScreen
import presentation.mealManagement.ScreenMode
import resources.Resources
import resources.Resources.strings

class MealsScreen(private val restaurantId: String) :
    BaseScreen<MealsScreenModel, MealsScreenUIState, MealsScreenUIEffect, MealScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(restaurantId) })
    }

    override fun onEffect(effect: MealsScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MealsScreenUIEffect.Back -> navigator.pop()
            is MealsScreenUIEffect.NavigateToMealDetails -> navigator.push(
                MealScreen(
                    ScreenMode.EDIT, effect.mealId, restaurantId = restaurantId
                )
            )

            is MealsScreenUIEffect.NavigateToAddMeal -> navigator.push(
                MealScreen(ScreenMode.CREATION, restaurantId = effect.restaurantId)
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: MealsScreenUIState, listener: MealScreenInteractionListener) {
        Scaffold(
            containerColor = colors.background,
            topBar = {
                BpAppBar(
                    title = strings.allMeals,
                    onNavigateUp = { listener.onClickBack() },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = listener::onAddMeaClick,
                    containerColor = colors.primary,
                    shape = shapes.medium,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add FAB",
                        tint = colors.onPrimary,
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValue ->
            NoItemsPlaceholder(
                painter = painterResource(Resources.images.emptyScreen),
                text = strings.noMeals,
                isVisible = (state.meals.isEmpty() && !state.isLoading)
            )
            AnimatedContent(state.isLoading) {
                if (state.isLoading) {
                    LoadingMeals(paddingValue)
                } else {
                    MealsContent(state, listener, paddingValue)
                }
            }
        }
    }

    @Composable
    fun LoadingMeals(paddingValue: PaddingValues) {
        Column(
            modifier = Modifier.padding(paddingValue).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(10) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                            .padding(start = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                    )
                }
            }
            LazyVerticalGrid(
                contentPadding = PaddingValues(dimens.space16),
                columns = GridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(10) {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(dimens.space4)
                                .clip(shape = shapes.medium).height(104.dp).shimmerEffect()
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth().height(20.dp)
                                .padding(top = 8.dp, start = 8.dp, end = 16.dp).shimmerEffect()
                        )
                        Box(
                            modifier = Modifier.width(90.dp).height(20.dp)
                                .padding(top = 8.dp, start = 8.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MealsContent(
        state: MealsScreenUIState,
        listener: MealScreenInteractionListener,
        paddingValue: PaddingValues,
    ) {
        val selectedCuisine by remember { mutableStateOf(state.selectedCuisine) }
        val lazyRowState = rememberLazyListState()
        Column(
            modifier = Modifier.padding(paddingValue).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = lazyRowState
            ) {
                items(state.cuisines.size) { index ->
                    BpChip(
                        label = state.cuisines[index].name,
                        isSelected = state.cuisines[index] == selectedCuisine,
                        onClick = { listener.onClickCuisineType(state.cuisines[index], index) }
                    )
                }
            }
            LazyVerticalGrid(
                contentPadding = PaddingValues(16.dp),
                columns = GridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.meals, key = { it.id }) { meal ->
                    MealCard(onClick = { listener.onClickMeal(meal.id) }, meal = meal)
                }
            }

            LaunchedEffect(selectedCuisine) {
                val index = state.cuisines.indexOf(selectedCuisine)
                if (index >= 0) {
                    lazyRowState.animateScrollToItem(index)
                }
            }
        }
    }
}