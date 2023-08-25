package presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.Theme
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.modifier.noRippleEffect
import resources.Resources.images

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BpAppBar(
    onNavigateUp: () -> Unit,
    title: String = "",
    modifier: Modifier = Modifier,
    content: (@Composable (() -> Unit))? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = Theme.typography.titleLarge,
                color = colors.contentPrimary
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(images.arrowLeft),
                contentDescription = "",
                modifier = Modifier.noRippleEffect { onNavigateUp() }
                    .padding(start = dimens.space16, end = dimens.space16),
                tint = colors.contentSecondary,
            )
        },
        actions = {
            content?.invoke()
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colors.surface),
        modifier = modifier,
    )
}

