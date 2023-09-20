package org.thechance.common.presentation.chat

import org.thechance.common.presentation.base.BaseScreenModel

class ChatScreenModel : BaseScreenModel<ChatUIState, ChatUIEffect>(ChatUIState()), ChatInteractionListener {
}