package presentation.meals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.theme.Theme.colors
import presentation.base.BaseScreen
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
            is MealsScreenUIEffect.NavigateToMealDetails -> {}
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
                    modifier = Modifier.padding(horizontal = 0.dp)
                ) {}
            }
        ) {
            Column (modifier = Modifier.padding(it)){
                BpChip(
                    label = strings.allMeals,
                    isSelected = false,
                    modifier = Modifier.padding(16.dp),
                    onClick = { listener.onClickBack() },

                    )
            }

        }
    }
}