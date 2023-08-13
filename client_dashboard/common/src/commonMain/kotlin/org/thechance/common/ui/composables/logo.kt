package org.thechance.common.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun Logo(expanded: Boolean, modifier: Modifier = Modifier) {
    Box(modifier) {
        Crossfade(expanded){targetState ->
            Image(
                painterResource(if (targetState) "ic_beepbeep_logo_expanded.svg" else "ic_beepbeep_logo.svg"),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                modifier = Modifier
            )
        }
    }
}