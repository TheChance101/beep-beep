package org.thechance.common.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.util.kms

@Composable
fun BpDropdownMenuItem(
    onClick: () -> Unit,
    text: String,
    leadingIconPath: String? = null,
    modifier: Modifier = Modifier,
    showBottomDivider: Boolean = true,
    isSecondary: Boolean = false,
) {
    Column(modifier = modifier) {
        DropdownMenuItem(
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 24.kms),
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (leadingIconPath != null)
                        Image(
                            painter = painterResource(leadingIconPath),
                            contentDescription = null,
                            alignment = Alignment.CenterEnd,
                            colorFilter = ColorFilter.tint(
                                if (isSecondary) Theme.colors.contentSecondary
                                else Theme.colors.contentPrimary
                            ),
                            modifier = Modifier.size(16.dp)
                        )
                    Spacer(Modifier.width(8.kms))
                    Text(
                        text = text,
                        style = Theme.typography.body,
                        color = if (isSecondary) Theme.colors.contentSecondary else Theme.colors.contentPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.width(100.kms)
                    )

                }
            },
        )
        if (showBottomDivider) {
            Divider(
                thickness = 1.kms,
                color = Theme.colors.divider,
                modifier = Modifier.padding(horizontal = 8.kms)
            )
        }
    }
}