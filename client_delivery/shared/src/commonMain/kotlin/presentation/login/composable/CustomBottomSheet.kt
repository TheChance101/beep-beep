package presentation.login.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme


@Composable
fun LoginScaffold(
    sheetContent: @Composable () -> Unit,
    sheetState: BottomSheetState,
    onBackGroundClicked: () -> Unit = {},
    sheetBackgroundColor: Color = Theme.colors.background,
    sheetShape: RoundedCornerShape = RoundedCornerShape(
        topStart = Theme.radius.medium,
        topEnd = Theme.radius.medium
    ),
    content: @Composable () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {

        content()

        AnimatedVisibility(
            visible = sheetState.isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = .4f)))
        }

        AnimatedVisibility(
            visible = sheetState.isVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ),
        ) {
            SheetContent(sheetShape, sheetBackgroundColor, { onBackGroundClicked() }, sheetContent)
        }
    }
}


@Composable
private fun SheetContent(
    shape: RoundedCornerShape,
    backgroundColor: Color,
    onBackGroundClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(Modifier.fillMaxHeight().weight(1f)) {
            Spacer(Modifier.fillMaxSize().noRippleEffect {
                onBackGroundClicked()
            })
        }

        Box(
            modifier = Modifier.background(
                shape = shape,
                color = backgroundColor
            )
        ) {
            content()
        }
    }
}

@Stable
class BottomSheetState {
    var isVisible by mutableStateOf(false)
        private set

    var bottom by mutableStateOf(0.dp)
        private set

    var offset by mutableStateOf(0.dp)
        private set

    fun show() {
        isVisible = true
        offset = 0.dp
    }

    fun dismiss() {
        isVisible = false
        offset = bottom
    }
}