package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import presentation.composable.modifier.roundedBorderShape
import presentation.resturantDetails.MealUIState

@Composable
fun MealCard(
    meal: MealUIState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            modifier = Modifier.roundedBorderShape().size(height = 72.dp, width = 80.dp),
            painter = rememberAsyncImagePainter(meal.image),
            contentScale = ContentScale.Crop,
            contentDescription = meal.name
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = meal.name,
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(2.dp).weight(.2f))

                Text(
                    text = "${meal.currency} ${meal.price}",
                    style = Theme.typography.body,
                    color = Theme.colors.primary,
                    maxLines = 1
                )
            }
            Text(
                text = meal.restaurantName,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary,
                maxLines = 1
            )
        }
    }

}
