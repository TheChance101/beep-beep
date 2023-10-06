package presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.cuisines.CuisineUiState


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PreferredFoodCard(
    onClick: (String) -> Unit,
    state: CuisineUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(width = 1.dp, color = Theme.colors.divider, shape = MaterialTheme.shapes.medium)
            .height(112.dp)
            .background(color = Theme.colors.surface, shape = MaterialTheme.shapes.medium)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { onClick(state.cuisineId) },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = state.cuisineName,
            color = Theme.colors.contentSecondary,
            style = Theme.typography.title,
            modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp)
        )
        BpImageLoader(
            imageUrl = state.cuisineImageUrl,
            contentDescription = "${state.cuisineName} Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(88.dp).clip(shape = MaterialTheme.shapes.medium)
                .align(Alignment.End)
        )

    }

}