package presentation.composable

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.meals.state.MealUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MealCard(onClick:()->Unit, meal: MealUIState,) {

    Column(modifier = Modifier.background(Theme.colors.surface, shape = MaterialTheme.shapes.medium)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick()}) {
        Image(
            painter = rememberAsyncImagePainter(meal.imageUrl),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth().padding(Theme.dimens.space4)
                .clip(shape = MaterialTheme.shapes.medium).height(104.dp)
        )
        Text(
            text = meal.name,
            style = Theme.typography.body,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(Theme.dimens.space4)
        )
        Text(
            text = "\$ ${meal.price} ",
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(start=Theme.dimens.space4, bottom = Theme.dimens.space16)
        )
    }
}
