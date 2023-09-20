package presentation.notification

sealed class NotificationUiEffect{
    data object NavigateToTraceOrderScreen: NotificationUiEffect()
    data object MakeOrderAgain: NotificationUiEffect()
}
