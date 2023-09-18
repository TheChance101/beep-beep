package presentation.notification

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Notification
import domain.usecase.ManageNotificationsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class NotificationScreenModel(private val notificationManagement: ManageNotificationsUseCase) :
    BaseScreenModel<NotificationsUiState, NotificationUiEffect>(NotificationsUiState()),
    NotificationInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getNotifications()
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