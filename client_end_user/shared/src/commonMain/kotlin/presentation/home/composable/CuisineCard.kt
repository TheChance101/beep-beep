package presentation.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
) {
    Column(
        modifier = modifier.size(height = 92.dp , width = 76.dp).clickable { onClickCuisine(cuisine.cuisineId) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.secondary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                rememberAsyncImagePainter(cuisine.cuisineImageUrl),
                contentDescription = "${cuisine.cuisineName} ${Resources.strings.cuisineImageDescription}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
        Text(
            text = cuisine.cuisineName,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary
        )
    }
}