package org.thechance.common.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composable.MainAppbar
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

class ChatScreen : BaseScreen<ChatScreenModel, ChatUIEffect, ChatUIState, ChatInteractionListener>() {
    override fun onEffect(effect: ChatUIEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun OnRender(state: ChatUIState, listener: ChatInteractionListener) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
        ) {
            MainAppbar(
                username = "username",
                isDropMenuExpanded = false,
                onClickDropDownMenu = {},
                onDismissDropDownMenu = { },
                onLogOut = { },
            )
            ChatScreenContent()
        }
    }

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    @Composable
    private fun ChatScreenContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
                .padding(40.kms)
                .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium))
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ChatHeader()
            }
        }
    }

    @Composable
    private fun ChatHeader() {
        Column {
            Row(
                modifier = Modifier.padding(24.kms),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Resources.Drawable.beepBeepLogoExpanded),
                    contentDescription = "Profile picture",
                    modifier = Modifier.size(40.kms).padding(end = 16.kms)
                )
                Text(
                    text = "@sadeq",
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "H81200133",
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentTertiary,
                )
                Text(
                    text = "3:09 PM",
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentTertiary,
                    modifier = Modifier.padding(horizontal = 40.kms)
                )
                BpOutlinedButton(
                    title = "Close Ticket",
                    onClick = { },
                )
            }

            Divider(color = Theme.colors.divider, thickness = 1.kms)
        }
    }

}