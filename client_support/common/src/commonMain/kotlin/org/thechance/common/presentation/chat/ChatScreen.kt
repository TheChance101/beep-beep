package org.thechance.common.presentation.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import org.thechance.common.presentation.base.BaseScreen

class ChatScreen : BaseScreen<ChatScreenModel, ChatUIEffect, ChatUIState, ChatInteractionListener>() {
    override fun onEffect(effect: ChatUIEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun OnRender(state: ChatUIState, listener: ChatInteractionListener) {
        ChatScreenContent()
    }

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    @Composable
    private fun ChatScreenContent() {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "Chat Screen", fontSize = 24.sp)
        }
    }

}