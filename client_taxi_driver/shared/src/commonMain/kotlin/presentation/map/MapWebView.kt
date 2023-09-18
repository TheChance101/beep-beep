package presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapWebView(modifier: Modifier = Modifier, url: String,lat:String,lng:String): Unit