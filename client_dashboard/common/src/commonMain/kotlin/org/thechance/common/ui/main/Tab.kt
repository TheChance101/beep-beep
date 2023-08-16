package org.thechance.common.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.thechance.common.ui.main.OverviewScreen
import org.thechance.common.ui.restaurant.RestaurantScreen
import org.thechance.common.ui.screen.taxi.TaxiScreen
import org.thechance.common.ui.users.UserScreen

object OverviewTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val title = "Overview"
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = OverviewScreen)
    }

}

object TaxisTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val title = "Taxis"
            val icon = rememberVectorPainter(Icons.Default.Search)
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = TaxiScreen)
    }
}

object RestaurantsTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val title = "Restaurants"
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return remember { TabOptions(index = 2u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
       Navigator(screen = RestaurantScreen)
    }
}
object UsersTab : Tab {
    override val options: TabOptions
    @Composable get() {
            val title = "Users"
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember { TabOptions(index = 3u, title = title, icon = icon) }
        }

    @Composable override fun Content() {
       Navigator(screen = UserScreen)
    }
}