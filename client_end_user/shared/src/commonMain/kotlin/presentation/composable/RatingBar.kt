package presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RatingBar(maxRating: Int = 5, currentRating: Double) {
    Row {
        for (i in 1..maxRating) {
            when {
                currentRating >= i -> Icon(
                    painter = painterResource("star.xml"),
                    contentDescription = null,
                    tint = Theme.colors.warning
                )

                currentRating >= i - 0.5 -> Icon(
                    painter = painterResource("star_half.xml"),
                    contentDescription = null,
                    tint = Theme.colors.warning
                )

                else -> Icon(
                    painter = painterResource("star.xml"),
                    contentDescription = null,
                    tint = Theme.colors.contentBorder
                )
            }
        }
    }
}