package com.beepbeep.designSystem.ui.composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.LocalDimens
import com.beepbeep.designSystem.ui.theme.LocalShapes
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToggleButton(
    isDark : Boolean = isSystemInDarkTheme(),
    onToggle: () -> Unit,
){
    val dimens= LocalDimens.current
    val shape = LocalShapes.current
    val horizontalBias by animateFloatAsState(
        targetValue =  when (isDark) {
            true -> 1f
            else -> -1f
        },
        animationSpec = tween(500)
    )
    val alignment=remember { derivedStateOf { BiasAlignment(horizontalBias = horizontalBias, verticalBias = 0f) } }
    Box(
        Modifier
            .border(width = 1.dp, color =  colorScheme.surfaceTint, shape =shape.small)
        .width(64.dp)
        .height(32.dp)
        .background(color =  colorScheme.background, shape = RoundedCornerShape(size = 4.dp))
        .padding(dimens.space2)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onToggle()
            }
    ){

        Card(modifier = Modifier.width(28.dp)
            .height(28.dp)
            .padding( dimens.space2)
            .align( alignment.value), shape= shape.small,
            colors= CardDefaults.cardColors(
                containerColor = colorScheme.primary
            ),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
        ){}

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier.fillMaxSize().padding(horizontal = dimens.space2)) {
            Icon(
                painter = painterResource("sun.xml"),
                contentDescription = "",
                tint =  if (isDark) colorScheme.onTertiary else Color.White,
                modifier = Modifier.size(24.dp).padding( dimens.space2)
            )

            Icon(
                painter = painterResource("moon_stars.xml"),
                contentDescription = "",
                tint =  if (isDark) Color.White else colorScheme.onTertiary,
                modifier = Modifier.size(24.dp).padding( dimens.space2)
            )
        }
    }
}