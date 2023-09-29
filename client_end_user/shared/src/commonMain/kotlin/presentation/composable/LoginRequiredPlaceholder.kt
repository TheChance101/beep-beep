package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import resources.Resources
import util.getStatusBarPadding

@Composable

fun LoginRequiredPlaceholder(
    placeHolder: Painter,
    message: String,
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background)
            .padding(getStatusBarPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = placeHolder,
            contentDescription = Resources.strings.login,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(112.dp).padding(bottom = 8.dp)
        )
        Text(
            text = message,
            style = Theme.typography.body,
            color = Theme.colors.contentTertiary,
        )
        TextOutLineButton(
            modifier = Modifier.width(115.dp).padding(top = 24.dp),
            text = Resources.strings.login,
            onClick = { onClickLogin() }
        )
    }
}