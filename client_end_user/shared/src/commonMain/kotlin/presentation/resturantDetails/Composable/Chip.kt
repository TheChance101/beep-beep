package presentation.resturantDetails.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
 fun Chip(
    color: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(size = Theme.radius.small)
            )
            .padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
    ) {
        content()
    }
}
