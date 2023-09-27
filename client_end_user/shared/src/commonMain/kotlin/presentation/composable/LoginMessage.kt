package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources
import util.getStatusBarPadding

@OptIn(ExperimentalResourceApi::class)
@Composable

fun LoginMessage(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(getStatusBarPadding())
            .background(Theme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Resources.images.needLogin),
            contentDescription = Resources.strings.login,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(120.dp,).padding(bottom = 8.dp)

        )
        Text(
            text = Resources.strings.pleaseLogin,
            style = Theme.typography.body,
            color = Theme.colors.contentTertiary,
        )
        TextOutLineButton(
            modifier = Modifier.padding(top = 24.dp),
            text = Resources.strings.login,
            onClick = {
                onClick()

            }
        )
    }
}