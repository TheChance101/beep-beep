package org.thechance.common.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.thechance.common.presentation.overview.OverviewScreen
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.restaurant.RestaurantScreen
import org.thechance.common.presentation.taxi.TaxiScreen
import org.thechance.common.presentation.users.UserScreen

object OverviewTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val title = Resources.Strings.overview
            return remember { TabOptions(index = 0u, title = title) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = OverviewScreen)
    }

}

object TaxisTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val title = Resources.Strings.taxis
            return remember { TabOptions(index = 1u, title = title) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = TaxiScreen())
    }
}

object RestaurantsTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val title = Resources.Strings.restaurants
            return remember { TabOptions(index = 2u, title = title) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = RestaurantScreen())
    }
}

object UsersTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val title = Resources.Strings.users
            return remember { TabOptions(index = 3u, title = title) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = UserScreen())
    }
}
