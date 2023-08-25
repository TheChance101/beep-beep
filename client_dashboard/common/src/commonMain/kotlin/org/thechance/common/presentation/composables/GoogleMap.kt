package org.thechance.common.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun GoogleMap(
    modifier: Modifier = Modifier,
    currentLocation: String,
    onGetAddress: (String) -> Unit,
) {
    Box(
        modifier.size(height = 464.dp, width = 760.dp)
    ) {
        SwingPanel(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
            factory = {
                webViewFromContent(
                    currentLocation = currentLocation,
                    onGetAddress = onGetAddress
                )
            },
        )
    }
}