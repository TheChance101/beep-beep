package presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun BpTitleWithContentSection(
    title: String,
    content: @Composable RowScope.() -> Unit
) {
    Text(
        title,
        modifier = Modifier.padding(
            start = Theme.dimens.space16,
            top = Theme.dimens.space16,
            bottom = Theme.dimens.space8
        ),
        style = Theme.typography.caption.copy(color = Theme.colors.contentTertiary),
    )
    Row(content = content)
}