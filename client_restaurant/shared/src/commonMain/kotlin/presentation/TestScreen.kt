package presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.composables.BPDashedDivider
import presentation.composables.BPSnackBar
import presentation.composables.HomeAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen() {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HomeAppBar(
                onStateChange = {},
                state = "Open",
                restaurantName = "Yummies restaurant",
                showDropDownMenu = {

                },
                isMultipleRestaurants = true
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    BPSnackBar(Icons.Filled.Home) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(data.visuals.message, color = Color.Black)
                            Text(data.visuals.message, color = Color.Black)
                        }
                    }
                }
            )
        }
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "This is a custom snack-bar",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            ) { Text("Snack bar") }

            Spacer(Modifier.height(20.dp))

            BPDashedDivider(modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(20.dp))

            BPDashedDivider(
                modifier = Modifier.fillMaxWidth(),
                showDiamondIcon = true
            )


        }
    }
}