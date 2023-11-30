package presentation.mealManagement.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.MealScreenInteractionListener
import resources.Resources
import util.getNavigationBarPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CuisineBottomSheet(
    cuisines: List<CuisineUIState>,
    listener: MealScreenInteractionListener,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().heightIn(max = 320.dp)

            .padding(
                top = Theme.dimens.space16,
                bottom = getNavigationBarPadding().calculateBottomPadding()
            ).padding(horizontal = Theme.dimens.space16)
    ) {

        stickyHeader {
            Row(
                modifier = Modifier.fillMaxWidth().background(Theme.colors.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    Resources.strings.chooseCuisine,
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )

                Spacer(Modifier.weight(1f))

                BpTransparentButton(
                    title = Resources.strings.cancel,
                    onClick = listener::onCuisinesCancel
                )

                BpOutlinedButton(
                    title = Resources.strings.save,
                    onClick = listener::onSaveCuisineClick,
                    modifier = Modifier.heightIn(max = 32.dp),
                    textPadding = PaddingValues(Theme.dimens.space4)
                )

            }
        }

        items(cuisines) { cuisine ->
            BpCheckBox(
                label = cuisine.name,
                onCheck = { listener.onCuisineSelected(cuisine.id) },
                isChecked = cuisine.isSelected,
                modifier = Modifier.padding(vertical = Theme.dimens.space4)
            )
        }
    }
}
