package org.thechance.common.ui.composables.table

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BpToggleableTextButton(
    text: String,
    onSelectChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    selected: Boolean = false,
    textStyle: TextStyle = Theme.typography.body,
    shape: Shape = RoundedCornerShape(Theme.radius.small),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    AnimatedContent(
        targetState = selected,
        modifier = modifier,
        transitionSpec = { fadeIn() with fadeOut() }
    ) {
        Text(
            text = text,
            style = textStyle.copy(if (it) Theme.colors.onPrimary else Theme.colors.contentTertiary),
            modifier = Modifier.size(size).clip(shape)
                .background(if (it) Theme.colors.primary else Color.Transparent).padding(top = 4.dp)
                .clickable(interactionSource, null) { onSelectChange(!selected) },
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun BpToggleableTextButtonPreview() {
    var selected by remember { mutableStateOf(true) }

    BpTheme(useDarkTheme = false) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            BpToggleableTextButton(
                text = "1",
                selected = selected,
                onSelectChange = { selected = it }
            )
        }
    }
}