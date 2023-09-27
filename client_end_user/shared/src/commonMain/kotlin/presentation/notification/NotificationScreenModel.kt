package presentation.notification

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Notification
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.ManageNotificationsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class NotificationScreenModel(
    private val notificationManagement: ManageNotificationsUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<NotificationsUiState, NotificationUiEffect>(NotificationsUiState()),
    NotificationInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getNotifications()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onCheckIfLoggedInError
        )
    }

    private fun onCheckIfLoggedInSuccess(accessToken: String) {
        if (accessToken.isNotEmpty()) {
            updateState { it.copy(isLoggedIn = true) }
        } else {
            updateState { it.copy(isLoggedIn = false) }
        }
    }

    private fun onCheckIfLoggedInError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    private fun getNotifications() {
        tryToExecute(
            { notificationManagement.getTodayNotifications() },
            ::onGetTodayNotificationsSuccess,
            ::onError
        )
        tryToExecute(
            { notificationManagement.getThisWeekNotifications() },
            ::onGetThisWeekNotificationsSuccess,
            ::onError
        )
    }

    override fun onClickTrackOrder() {
        TODO("Not yet implemented")
    }

    override fun onClickTryAgain() {
        TODO("Not yet implemented")
    }

    override fun onClickLogin() {
        sendNewEffect(NotificationUiEffect.NavigateToLoginScreen)
    }

    private fun onGetTodayNotificationsSuccess(todayNotifications: List<Notification>) {
        updateState { it.copy(todayNotifications = todayNotifications.toUiState()) }
    }

    private fun onGetThisWeekNotificationsSuccess(weekNotifications: List<Notification>) {
        updateState { it.copy(thisWeekNotifications = weekNotifications.toUiState()) }
    }

    private fun onError(error: ErrorState) {
        println("error is $error")
    }

}