package presentation.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState

@Composable
fun Map() {
    val state = rememberWebViewState(
        url = "https://github.com/MohamedRejeb"
    )

    LaunchedEffect(state.isLoading) {
        // Get the current loading state
    }

    WebView(state, modifier = Modifier.fillMaxSize())
}