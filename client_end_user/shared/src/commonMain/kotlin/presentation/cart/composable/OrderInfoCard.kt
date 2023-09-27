package presentation.cart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.composable.exitinstion.topBorder
import resources.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderInfoCard(
    onClickOrderNow: () -> Unit,
    totalPrice: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().height(96.dp).background(Theme.colors.surface)
            .topBorder(1.dp, Theme.colors.divider)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                Resources.strings.total,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(totalPrice, style = Theme.typography.headline, color = Theme.colors.contentPrimary)
        }
        Spacer(modifier = Modifier.weight(1f))
        BpButton(
            title = Resources.strings.orderNow,
            onClick = { onClickOrderNow() },
            modifier = Modifier.width(208.dp).padding(end = 16.dp, top = 16.dp)
        )
    }
}
