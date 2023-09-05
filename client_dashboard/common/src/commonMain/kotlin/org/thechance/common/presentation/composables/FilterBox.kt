package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

@Composable
fun FilterBox(
    title: String,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    hasClearAll: Boolean = true,
    onClearAllClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .width(450.kms)
            .background(Theme.colors.surface)
            .border(1.dp, Theme.colors.divider)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FilterBoxHeader(
                title = title,
                hasClearAll = hasClearAll,
                onClearAllClicked = onClearAllClicked
            )
            content()
            FilterBoxFooter(onSaveClicked, onCancelClicked)
        }
    }
}

@Composable
private fun FilterBoxHeader(
    title: String,
    modifier: Modifier = Modifier,
    hasClearAll: Boolean = true,
    onClearAllClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.kms),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Theme.typography.headline,
            color = Theme.colors.contentPrimary
        )
        if (hasClearAll) BpTransparentButton(
            title = Resources.Strings.clearAll,
            onClick = onClearAllClicked
        )
    }
}

@Composable
private fun FilterBoxFooter(
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.kms),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BpTransparentButton(
            title = Resources.Strings.cancel,
            onClick = onCancelClicked,
            modifier = Modifier
                .height(32.dp)
                .weight(1f)
                .padding(end = 16.kms)
                .cursorHoverIconHand()
        )
        BpOutlinedButton(
            title = Resources.Strings.save,
            onClick = onSaveClicked,
            textPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(32.dp)
                .weight(3f)
                .cursorHoverIconHand()
        )
    }
}