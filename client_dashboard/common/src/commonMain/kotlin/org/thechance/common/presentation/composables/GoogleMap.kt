package org.thechance.common.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import org.thechance.common.presentation.util.kms

@Composable
fun GoogleMap(
    modifier: Modifier = Modifier,
    currentLocation: String,
    onGetAddress: (String) -> Unit,
) {
    Box(
        modifier.size(height = 464.kms, width = 760.kms)
    ) {
        SwingPanel(
            factory = {
                mapFromWebView(
                    currentLocation = currentLocation,
                    onGetAddress = onGetAddress
                )
            },
        )
    }
}