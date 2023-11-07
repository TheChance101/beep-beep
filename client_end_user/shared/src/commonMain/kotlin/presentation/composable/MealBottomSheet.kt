package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.resturantDetails.MealUIState
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MealBottomSheet(
    meal: MealUIState,
    isLoading: Boolean = false,
    onDismissSheet: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(Resources.images.placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = "${meal.name} image",
                modifier = Modifier.height(240.dp).padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
            BackButton(
                onClick = onDismissSheet,
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.End,
            )
        }
        Text(
            text = meal.name,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = meal.description,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                text = "${meal.currency}${meal.totalPrice}",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                quantityButton(
                    onClick = onDecreaseQuantity,
                    icon = Resources.images.minus
                )
                Text(
                    text = "${meal.quantity}",
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary,
                )
                quantityButton(onClick = onIncreaseQuantity, icon = Resources.images.plus)
            }
        }
        BpButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
            title = Resources.strings.addToCart,
            onClick = onAddToCart,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun quantityButton(onClick: () -> Unit, icon: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Theme.colors.contentBorder,
                shape = RoundedCornerShape(size = Theme.radius.small)
            )
            .width(28.dp)
            .height(28.dp).noRippleEffect { onClick() }
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Theme.colors.contentPrimary,
            modifier = Modifier.padding(4.dp)
        )
    }
}
