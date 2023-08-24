package presentation.meals

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.CircularProgressIndicator
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
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.base.BaseScreen
import presentation.composables.BpAppBar
import presentation.info.RestaurantInfoScreen
import presentation.meals.Composable.MealCard
import presentation.meals.MealsUIState.MealsScreenUIState
import resources.Resources.strings

class MealsScreen :
    BaseScreen<MealsScreenModel, MealsScreenUIState, MealsScreenUIEffect, MealScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MealsScreenModel() }
        initScreen(screenModel)
    }

    override fun onEffect(effect: MealsScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MealsScreenUIEffect.Back -> navigator.pop()
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
                ) {}

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    },
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
                        start = dimens.space16,
                        end = dimens.space16,
                        top = dimens.space16
                    ),
                    horizontalArrangement = Arrangement.spacedBy(dimens.space16),
                ) {
                    items(state.cousin.size) { index ->
                        BpChip(
                            label = state.cousin[index].name,
                            isSelected = state.cousin[index] == state.selectedCousin,
                            onClick = { listener.onClickCousinType(state.cousin[index]) },
                        )
                    }
                }
                AnimatedVisibility(visible = state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = colors.primary,
                    )
                }
                LazyVerticalGrid(

                    contentPadding = PaddingValues(dimens.space16),
                    columns = GridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(dimens.space8),
                    verticalArrangement = Arrangement.spacedBy(dimens.space8),

                    ) {
                    items(state.meals.size) { index ->
                        MealCard(onClick = { }, meal = state.meals[index])
                    }

                }

            }
        }
    }
}