package org.thechance.common.presentation.overview

import androidx.compose.runtime.Composable
import org.thechance.common.domain.entity.OrdersRevenue
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.TripsRevenue
import org.thechance.common.domain.entity.User
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.ErrorState


data class OverviewUiState(
    val hasInternetConnection: Boolean = true,
    val isLoading: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
    val users: List<LatestRegisteredUserUiState> = emptyList(),
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
    val earningData: List<Double> = emptyList(),
    val revenueData: List<Double> = emptyList(),
    val revenueShare: List<String> = emptyList(),
    val tripsRevenueShare: TripsRevenueShareUiState = TripsRevenueShareUiState(),
    val ordersRevenueShare: OrdersRevenueShareUiState = OrdersRevenueShareUiState(),
)

data class TripsRevenueShareUiState(
    val acceptedTrips: Double = 1.0,
    val pendingTrips: Double = 1.0,
    val rejectedTrips: Double = 1.0,
    val canceledTrips: Double = 1.0
)

data class OrdersRevenueShareUiState(
    val completedOrders: Double = 1.0,
    val canceledOrders: Double = 1.0,
    val inTheWayOrders: Double = 1.0,
)

data class LatestRegisteredUserUiState(
    val name: String,
    val image: String = "dummy_img.png",
    val permission: List<PermissionUiState>,
)

data class DropdownMenuState(
    val isExpanded: Boolean = false,
    val selectedIndex: Int = 0,
){
    val items: List<String>
      @Composable get() = listOf(
              Resources.Strings.monthly,
              Resources.Strings.daily,
              Resources.Strings.weekly
      )

}

fun User.toLastUserUiState(): LatestRegisteredUserUiState {
    return LatestRegisteredUserUiState(
        name = fullName,
        image = imageUrl.ifEmpty { "dummy_img.png" },
        permission = permission.toUiState(),
    )
}

fun List<User>.toLatestUsersUiState(): List<LatestRegisteredUserUiState> {
    return this.map { it.toLastUserUiState() }
}

enum class PermissionUiState {
    RESTAURANT,
    DRIVER,
    END_USER,
    SUPPORT,
    DELIVERY,
    ADMIN,
}

fun Permission.toUiState(): PermissionUiState {
    return when (this) {
        Permission.RESTAURANT_OWNER -> PermissionUiState.RESTAURANT
        Permission.DRIVER -> PermissionUiState.DRIVER
        Permission.END_USER -> PermissionUiState.END_USER
        Permission.SUPPORT -> PermissionUiState.SUPPORT
        Permission.DELIVERY -> PermissionUiState.DELIVERY
        Permission.ADMIN -> PermissionUiState.ADMIN
    }
}

fun List<Permission>.toUiState(): List<PermissionUiState> {
    return this.map { it.toUiState() }
}

fun TripsRevenue.toUiState(): TripsRevenueShareUiState {
    return TripsRevenueShareUiState(
        acceptedTrips = acceptedTrips,
        pendingTrips = pendingTrips,
        rejectedTrips = rejectedTrips,
        canceledTrips = canceledTrips,
    )
}

fun OrdersRevenue.toUiState(): OrdersRevenueShareUiState {
    return OrdersRevenueShareUiState(
        completedOrders = completedOrders,
        canceledOrders = canceledOrders,
        inTheWayOrders = inTheWayOrders,
    )
}


