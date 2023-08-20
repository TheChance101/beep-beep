package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun TestScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "This is a custom snack-bar",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        ) { Text("Show Custom Snack bar") }


        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier.padding(25.dp),
                    containerColor = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ).fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Home, contentDescription = null, tint = Color.Red)
                        Text(data.visuals.message, color = Color.Black)
                    }
                }
            }
        )
    }

}