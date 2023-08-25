package presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun CustomBottomSheet(
    sheetContent: @Composable () -> Unit,
    sheetState: ModalBottomSheetState,
    sheetBackgroundColor: Color = Theme.colors.background,
    sheetShape: RoundedCornerShape = RoundedCornerShape(
        topStart = Theme.radius.medium,
        topEnd = Theme.radius.medium
    ),
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        content()

        if (sheetState.isVisible) {
            Column(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = .4f))) {

                Box(Modifier.fillMaxHeight().weight(1f)) {
                    Spacer(Modifier.fillMaxSize().clickable {
                        if (sheetState.isVisible) {
                            sheetState.dismiss()
                        }
                    })
                }

                Box(
                    modifier = Modifier.background(shape = sheetShape, color = sheetBackgroundColor)
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
