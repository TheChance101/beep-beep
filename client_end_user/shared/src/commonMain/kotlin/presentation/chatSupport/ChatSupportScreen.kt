package presentation.chatSupport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.chatSupport.composable.MessageCard
import resources.Resources
import util.getNavigationBarPadding
import kotlin.math.abs

class ChatSupportScreen() :
    BaseScreen<ChatSupportScreenModel, ChatUIState, ChatSupportUiEffect, ChatSupportInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
    @Composable
    override fun onRender(state: ChatUIState, listener: ChatSupportInteractionListener) {
        Scaffold(
            bottomBar = {
                BpSimpleTextField(
                    hint = Resources.strings.message,
                    text = state.message,
                    onValueChange = { listener.onMessageChanged(it) },
                    trailingIconEnabled = state.message.isNotEmpty(),
                    trailingPainter = painterResource(Resources.images.sendMessage),
                    onTrailingIconClick = { listener.onClickSendMessage(state.message, "1") },
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = getNavigationBarPadding().calculateBottomPadding() + 16.dp
                    )
                )
            },
            topBar = {
                BpAppBar(
                    title = Resources.strings.supportTeam,
                    onNavigateUp = { listener.onClickBack() },
                    painterResource = painterResource(Resources.images.iconBack),
                    isBackIconVisible = true,
                )
            }
        ) {
            val scrollState = rememberLazyListState()
            LaunchedEffect(state.messages.size) {
                scrollState.animateScrollToItem(abs(state.messages.size - 1))
            }
            LazyColumn(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                state = scrollState,
                contentPadding = PaddingValues(
                    top = it.calculateTopPadding()
                            + getNavigationBarPadding().calculateTopPadding()
                            + 16.dp,
                    bottom = 16.dp,
                    start = it.calculateStartPadding(LocalLayoutDirection.current),
                    end = it.calculateEndPadding(LocalLayoutDirection.current)
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.messages) { message ->
                    MessageCard(message)
                }
            }

        }
    }

    override fun onEffect(effect: ChatSupportUiEffect, navigator: Navigator) {
        when (effect) {
            is ChatSupportUiEffect.NavigateUp -> navigator.pop()
        }
    }

}