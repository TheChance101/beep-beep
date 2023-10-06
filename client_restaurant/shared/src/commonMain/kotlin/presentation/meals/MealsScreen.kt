package presentation.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.MealCard
import presentation.composable.ShimmerItemList
import presentation.mealManagement.MealScreen
import presentation.mealManagement.ScreenMode
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
            is MealsScreenUIEffect.NavigateToMealDetails ->
                navigator.push(MealScreen(ScreenMode.EDIT, effect.mealId,restaurantId = restaurantId))

            is MealsScreenUIEffect.NavigateToAddMeal -> navigator.push(MealScreen(ScreenMode.CREATION,restaurantId =effect.restaurantId))
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
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
        ) {
            Column(
                modifier = Modifier.padding(it).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                LazyRow(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.cuisines.size) { index ->
                        BpChip(
                            label = state.cuisines[index].name,
                            isSelected = state.cuisines[index] == state.selectedCuisine,
                            onClick = { listener.onClickCuisineType(state.cuisines[index]) },
                        )
                    }
                }
                LazyVerticalGrid(
                    contentPadding = PaddingValues(dimens.space16),
                    columns = GridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),

                    ) {
                    items(state.meals.size) { index ->
                       ShimmerItemList(
                           isLoading = state.isLoading,
                           cardHeight = 100.dp,
                           cardWidth = 150.dp,
                           contentAfterLoading = {
                               MealCard(onClick = {
                                   listener.onClickMeal(state.meals[index].id)
                               }, meal = state.meals[index])
                           }
                       )
                    }
                }
            }
        }
    }
}
