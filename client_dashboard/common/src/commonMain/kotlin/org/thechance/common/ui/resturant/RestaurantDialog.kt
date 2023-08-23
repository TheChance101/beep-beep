package org.thechance.common.ui.resturant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import java.awt.Dimension

@Composable
fun RestaurantDialog(
    modifier: Modifier = Modifier,
) {
    val dialog = remember { ComposeDialog() }

    Dialog(
        visible = true,
        create = { dialog },
        dispose = { dialog.dispose() }
    ) {
        window.minimumSize = Dimension(1176, 700)
        Column(
            modifier
                .clip(RectangleShape)
                .background(Theme.colors.surface)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "New Restaurant",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxHeight().width(350.dp)) {
                    BpTextField(
                        onValueChange = { },
                        text = "",
                        label = "Restaurant name",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = { },
                        text = "",
                        label = "Owner username",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = { },
                        text = "",
                        label = "Phone number",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = { },
                        text = "",
                        label = "Working hours",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = { },
                        text = "Choose on map",
                        label = "Location",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )
                }
                Box(Modifier.clip(RoundedCornerShape(24.dp)).fillMaxSize()) {
                    SwingPanel(
                        modifier = Modifier.matchParentSize(),
                        factory = { webViewFromLinkOpenStreetMap() },
                    )
                }
            }
        }
    }
}

