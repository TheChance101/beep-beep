package presentation.notification

sealed class NotificationUiEffect {
    data class NavigateToTrackFoodOrder(val orderId: String) : NotificationUiEffect()
    data class NavigateToTrackDelivery(val tripId: String) : NotificationUiEffect()
    data class NavigateToTaxiRide(val tripId: String) : NotificationUiEffect()
    data object NavigateToLoginScreen : NotificationUiEffect()
}
