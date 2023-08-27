package presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun BpCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Theme.radius.medium),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.surface)
    ) {
        content()
    }
}