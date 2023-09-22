package presentation.composable

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun CustomBottomSheet(
    sheetContent: @Composable () -> Unit,
    sheetState: ModalBottomSheetState,
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
    sheetShape: RoundedCornerShape,
    sheetBackgroundColor: Color,
    onBackGroundClicked: () -> Unit,
    sheetContent: @Composable () -> Unit
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
                shape = sheetShape,
                color = sheetBackgroundColor
            )
        ) {
            sheetContent()
        }
    }
}

@Stable
class ModalBottomSheetState {
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

    fun onDrag(deltaY: Float) {
        offset = (offset - deltaY.dp).coerceIn(0.dp, bottom)
    }

    internal fun updateBottom(newBottom: Dp) {
        bottom = newBottom
        offset = newBottom
    }
}