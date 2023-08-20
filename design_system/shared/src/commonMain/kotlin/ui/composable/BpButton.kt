package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@ExperimentalMaterial3Api
@Composable
fun BpButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    containerColor: Color = Theme.colors.primary,
    contentColor: Color = Color.White,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center
) {
    val buttonColor by animateColorAsState(
        if (enabled) containerColor else Theme.colors.disable
    )

    Surface(
        modifier = modifier.height(56.dp),
        onClick = onClick,
        shape = shape,
        color = buttonColor,
        enabled = enabled,
        contentColor = contentColor,
    ) {
        Row(
            Modifier.defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            ),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            painter?.let {
                Image(
                    modifier = Modifier.size(24.dp).padding(end = 8.dp),
                    painter = painter,
                    contentDescription = null
                )
            }
            Text(
                text = title,
                style = Theme.typography.titleLarge.copy(color = contentColor),
                modifier=Modifier)
        }
    }

}