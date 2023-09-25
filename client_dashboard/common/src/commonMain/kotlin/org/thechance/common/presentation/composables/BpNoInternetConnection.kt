package org.thechance.common.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

@Composable
fun BpNoInternetConnection(hasConnection: Boolean,onRetry: () -> Unit) {
    AnimatedVisibility(visible = hasConnection) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.kms),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Resources.Drawable.noConnection),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize().clickable {
                    onRetry()
                }
            )
        }
    }
}