package org.thechance.common.presentation.composables

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.thechance.common.presentation.resources.Resources

@Composable
fun BpLogo(expanded: Boolean, modifier: Modifier = Modifier) {
    Box(modifier) {
        Crossfade(expanded) { targetState ->
            Image(
                painterResource(
                    if (targetState) Resources.Drawable.beepBeepLogoExpanded else Resources.Drawable.beepBeepLogo
                ),
                contentDescription = null,
                alignment = Alignment.CenterStart,
            )
        }
    }
}