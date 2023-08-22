package presentation.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun CustomBottomSheet(
    sheetContent: @Composable () -> Unit,
    sheetState: ModalBottomSheetState,
    sheetBackgroundColor: Color = Color.White,
    sheetShape: RoundedCornerShape = RoundedCornerShape(
        topStart = Theme.radius.medium,
        topEnd = Theme.radius.medium
    ),
    content: @Composable (PaddingValues) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content(
            PaddingValues(
                bottom = animateDpAsState(
                    if (sheetState.isVisible) sheetState.bottom else 0.dp
                ).value
            )
        )

        if (sheetState.isVisible) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.fillMaxHeight().weight(1f))

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
