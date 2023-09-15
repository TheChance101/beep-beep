package org.thechance.service_identity.domain.util


object Role {
    const val END_USER = 1
    const val DASHBOARD_ADMIN = 2
    const val RESTAURANT_OWNER = 4
    const val TAXI_DRIVER = 8
    const val SUPPORT = 16
    const val DELIVERY = 32
}

object ApplicationId {
    const val DASHBOARD = "dashboard"
    const val END_USER = "end_user"
    const val RESTAURANT = "restaurantId"
    const val TAXI_DRIVER = "taxi"
    const val SUPPORT = "support"
    const val DELIVERY = "delivery"
}

