package org.thechance.common.presentation.chat

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composable.MainAppbar
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms
import kotlin.math.abs

class ChatScreen : BaseScreen<ChatScreenModel, ChatUIEffect, ChatUIState, ChatInteractionListener>() {

    override fun onEffect(effect: ChatUIEffect, navigator: Navigator) {
        when (effect) {
            ChatUIEffect.NavigateToLogin -> navigator.replaceAll(LoginScreen())
        }
    }

    @Composable
    override fun OnRender(state: ChatUIState, listener: ChatInteractionListener) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
        ) {
            MainAppbar(
                username = state.appbar.username,
                isDropMenuExpanded = state.appbar.isDropdownMenuExpanded,
                onClickDropDownMenu = listener::onClickDropdownMenu,
                onDismissDropDownMenu = listener::onDismissDropdownMenu,
                onLogOut = listener::onClickLogOut,
            )
            AnimatedContent(targetState = state.idle) { idle ->
                if (state.loading) LoadingIndicator()
                else if (idle) IdlePlaceholder()
                else ChatScreenContent(state, listener)
            }
        }
    }

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    @Composable
    private fun ChatScreenContent(
        state: ChatUIState,
        listener: ChatInteractionListener,
    ) {
        Box(
            modifier = Modifier
                .background(Theme.colors.surface)
                .padding(40.kms)
                .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium))
        ) {
            Column {
                ChatHeader(state.ticket, listener)
                ChatMessages(state.messages, modifier = Modifier.weight(1f))
                Divider(color = Theme.colors.divider, thickness = 1.kms)
                BpSimpleTextField(
                    text = state.message,
                    onValueChange = listener::onMessageChange,
                    modifier = Modifier.padding(24.kms),
                    hint = Resources.Strings.writeYourMessage,
                    trailingPainter = painterResource(Resources.Drawable.send),
                    leadingPainter = null,
                    onTrailingIconClick = listener::onSendMessageClicked,
                    trailingIconEnabled = state.message.isNotEmpty(),
                )
            }
        }
    }

    @Composable
    private fun ChatHeader(ticket: ChatUIState.TicketUIState, listener: ChatInteractionListener) {
        Column {
            Row(
                modifier = Modifier.padding(24.kms),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Resources.Drawable.beepBeepLogoExpanded),
                    contentDescription = Resources.Strings.profileImage,
                    modifier = Modifier.size(40.kms).padding(end = 16.kms)
                )
                Text(
                    text = ticket.username,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = ticket.id,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentTertiary,
                )
                Text(
                    text = ticket.openedAt,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentTertiary,
                    modifier = Modifier.padding(horizontal = 40.kms)
                )
                BpOutlinedButton(
                    title = Resources.Strings.closeTicket,
                    onClick = listener::onCloseTicketClicked,
                )
            }
            Divider(color = Theme.colors.divider, thickness = 1.kms)
        }
    }

    @Composable
    private fun ChatMessages(messages: List<ChatUIState.MessageUIState>, modifier: Modifier = Modifier) {
        val scrollState = rememberLazyListState()
        LaunchedEffect(messages.size) {
            scrollState.animateScrollToItem(abs(messages.size - 1))
        }
        LazyColumn(modifier = modifier, state = scrollState) {
            items(messages) { message ->
                val currentMessage = messages.indexOf(message)
                val nextMessage = messages.getOrNull(currentMessage + 1)
                val showAvatar = nextMessage?.isMe != message.isMe && !message.isMe
                Message(message.copy(showAvatar = showAvatar))
            }
        }
    }

    @Composable
    private fun Message(message: ChatUIState.MessageUIState) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.kms, horizontal = 24.kms),
            horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
        ) {
            if (message.showAvatar) Image(
                painter = painterResource(Resources.Drawable.beepBeepLogoExpanded),
                contentDescription = null,
                modifier = Modifier.size(40.kms).padding(end = 8.kms)
            ) else Spacer(modifier = Modifier.size(40.kms))

            Text(
                text = message.message,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary,
                modifier = Modifier
                    .padding(end = if (message.isMe) 0.kms else 400.kms)
                    .clip(RoundedCornerShape(Theme.radius.medium))
                    .background(if (message.isMe) Theme.colors.background else Theme.colors.secondary)
                    .padding(vertical = 16.kms, horizontal = 24.kms)
            )
        }
    }

    @Composable
    private fun IdlePlaceholder() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.kms),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(Resources.Drawable.idle),
                contentDescription = Resources.Strings.idle,
                modifier = Modifier
                    .size(250.kms)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = Resources.Strings.idleTitle,
                style = Theme.typography.headline,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(vertical = 24.kms)
            )
            Text(
                text = Resources.Strings.idleSubtitle,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary
            )
        }
    }

    @Composable
    private fun LoadingIndicator(modifier: Modifier = Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = Theme.colors.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}