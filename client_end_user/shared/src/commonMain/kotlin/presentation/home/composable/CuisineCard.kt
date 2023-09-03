package presentation.home.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import presentation.home.CuisineUiState
import resources.Resources

@Composable
fun CuisineCard(
    cuisine: CuisineUiState,
    onClickCuisine: (cuisineId: String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedFloat by animateFloatAsState(targetValue = if (isPressed) 1.2f else 1f)
    Column(
        modifier = modifier.size(height = 92.dp, width = 76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.secondary)
                .clickable(
                    interactionSource,
                    indication = null,
                    onClick = { onClickCuisine(cuisine.cuisineId) }
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                rememberAsyncImagePainter(cuisine.cuisineImageUrl),
                contentDescription = "${cuisine.cuisineName} ${Resources.strings.cuisineImageDescription}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(0.8f).scale(animatedFloat)
            )
        }
        Text(
            text = cuisine.cuisineName,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}