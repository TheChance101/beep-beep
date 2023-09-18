package org.thechance.common.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.thechance.common.presentation.util.kms

@Composable
fun GoogleMap(
    modifier: Modifier = Modifier,
    lat: String,
    lng: String,
    onGetLocation: (String) -> Unit,
) {
    Box(modifier.size(400.kms, 400.kms)) {
        SwingPanel(
            factory = {
                mapFromWebView(
                    lat = lat,
                    lng = lng,
                    onGetLocation = onGetLocation
                )
            },
        )
    }
}