package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme

@Composable
fun BeepBeepCircleImage(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    boxSize: Int = 72,
    imageSize: Int = 32,
    strokeWidth: Int = 2,
    strokeColor: Color = BeepBeepTheme.colorScheme.onTertiary,
    backgroundColor: Color = BeepBeepTheme.colorScheme.surface,
    imageScale: ContentScale = ContentScale.Crop,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(backgroundColor)
            .size(boxSize.dp)
            .clickable { onClick() }
            .border(width = strokeWidth.dp, color = strokeColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(imageSize.dp),
            painter = painter,
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = imageScale
        )
    }
}