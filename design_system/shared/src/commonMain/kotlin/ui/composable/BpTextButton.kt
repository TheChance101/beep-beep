package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import com.beepbeep.designSystem.ui.theme.Theme.radius
import com.beepbeep.designSystem.ui.theme.Theme.typography

@Composable
fun BpTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    heightInDp: Int = 32,
) {
    Surface(
        modifier = modifier
            .height(heightInDp.dp)
            .border(
                width = 1.dp,
                color = colors.contentBorder,
                shape = RoundedCornerShape(radius.small)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        color = Color.Transparent,
    ) {
        Text(
            text = text,
            style = typography.body,
            color = colors.contentTertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = dimens.space16, vertical = dimens.space16)

        )
    }
}