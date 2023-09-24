package presentation.cart.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.composable.exitinstion.bottomBorder

@Composable
fun ItemCart(
    onClickPlus: (Int, Long) -> Unit,
    onClickMinus: (Int, Long) -> Unit,
    mealName: String,
    restaurantName: String,
    price: String,
    imagePainter: Painter,
    count: Long,
    index: Int,
    modifier: Modifier = Modifier,
    isDividerVisible: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth().height(104.dp)
            .bottomBorder(1.dp, Theme.colors.divider, isDividerVisible),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePainter, contentDescription = null,
            modifier = Modifier.padding(start = 16.dp)
                .width(80.dp)
                .height(72.dp).clip(
                    RoundedCornerShape(8.dp)
                ),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = mealName,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = restaurantName,
                style = Theme.typography.body,
                color = Theme.colors.contentTertiary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = price,
                style = Theme.typography.body,
                color = Theme.colors.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.padding(end = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier.padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier.border(
                        width = 1.dp,
                        color = Theme.colors.divider,
                        shape = RoundedCornerShape(4.dp)
                    ).size(28.dp).noRippleEffect { onClickMinus(index, count) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "-", color = Theme.colors.contentTertiary)
                }
                Text(
                    text = count.toString(),
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier.border(
                        width = 1.dp,
                        color = Theme.colors.divider,
                        shape = RoundedCornerShape(4.dp)
                    ).size(28.dp).noRippleEffect { onClickPlus(index, count) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+", color = Theme.colors.contentTertiary)
                }
            }
        }

    }

}