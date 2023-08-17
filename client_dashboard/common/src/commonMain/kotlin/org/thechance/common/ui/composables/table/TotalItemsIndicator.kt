package org.thechance.common.ui.composables.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun TotalItemsIndicator(
    modifier: Modifier = Modifier,
    numberItemInPage: Int,
    totalItems: Int
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        BpOutlinedButton(
            modifier = modifier.size(32.dp),
            title = "$numberItemInPage",
            onClick = {},
            enabled = false
        )

        Text(
            "item out of $totalItems item",
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary
        )
    }
}