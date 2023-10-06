package presentation.map.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MapCardAnimation(
    modifier: Modifier = Modifier,
    visible: Boolean,
    enter: EnterTransition = slideInVertically(tween(500)) { it } + fadeIn(),
    exit: ExitTransition = slideOutVertically(tween(300)) { it } + fadeOut(),
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}