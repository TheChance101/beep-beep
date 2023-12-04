package presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpImageLoader
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.meals.MealUIState
import resources.Resources

@Composable
fun MealCard(onClick: () -> Unit, meal: MealUIState) {

    Column(modifier = Modifier.background(Theme.colors.surface, shape = MaterialTheme.shapes.medium)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }) {
        BpImageLoader(
            modifier = Modifier.fillMaxWidth().padding(Theme.dimens.space4)
                .clip(shape = MaterialTheme.shapes.medium).height(104.dp),
            imageUrl = meal.imageUrl,
            contentDescription = "${meal.name} Picture",
            errorPlaceholderImageUrl = Resources.images.mealErrorPlaceholder,
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = meal.name,
            style = Theme.typography.body,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = meal.price,
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )
    }
}
