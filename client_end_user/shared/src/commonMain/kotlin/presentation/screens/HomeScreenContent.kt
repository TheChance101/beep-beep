package presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.core.component.inject
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent

class HomeScreen : Screen, KoinComponent {

    private val homeViewModel: HomeViewModel by inject()

    @Composable
    override fun Content() {
        val state by homeViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        HomeScreenContent { navigator push DetailsScreen(state.text) }
    }

}

@Composable
fun HomeScreenContent(onNavigateToDetails: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToDetails) {
            Text(text = "Go to Details")
        }
    }

}
