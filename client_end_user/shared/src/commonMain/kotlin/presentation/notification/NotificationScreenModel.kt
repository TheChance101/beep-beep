package presentation.notification

import androidx.paging.PagingData
import androidx.paging.map
import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.NotificationHistory
import domain.usecase.IGetTransactionHistoryUseCase
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class NotificationScreenModel(
    private val transaction: IGetTransactionHistoryUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
) : BaseScreenModel<NotificationsUiState, NotificationUiEffect>(NotificationsUiState()),
    NotificationInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getNotificationHistory()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onCheckIfLoggedInError
        )
    }

    private fun onCheckIfLoggedInSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.collect { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLoggedIn = true) }
                } else {
                    updateState { it.copy(isLoggedIn = false) }
                }
            }
        }
    }

    private fun onCheckIfLoggedInError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    private fun getNotificationHistory() {
        tryToExecute(
            function = transaction::getNotificationHistory,
            onSuccess = ::onGetNotificationHistorySuccess,
            onError = ::onGetNotificationHistoryError
        )
    }

    private fun onGetNotificationHistorySuccess(notification: Flow<PagingData<NotificationHistory>>) {
        updateState {
            it.copy(notifications = notification.toUiState())
        }
    }

    private fun onGetNotificationHistoryError(errorState: ErrorState) {

    }

    override fun onClickTrackOrder() {
    }

    override fun onClickTryAgain() {
    }

    override fun onClickLogin() {
        sendNewEffect(NotificationUiEffect.NavigateToLoginScreen)
    }

}
