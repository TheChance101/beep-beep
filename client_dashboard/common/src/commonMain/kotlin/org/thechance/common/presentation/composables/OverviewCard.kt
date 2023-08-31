package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.util.kms

@Composable
fun OverviewCard(
    start: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.background(Theme.colors.green, RoundedCornerShape(8.kms)).padding(24.kms),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.kms)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            start?.invoke()
            leading?.invoke()
        }
        content()
    }
}