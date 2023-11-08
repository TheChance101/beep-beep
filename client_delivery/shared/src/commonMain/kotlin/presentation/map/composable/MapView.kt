package presentation.map.composable


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.DefaultScreenLifecycleOwner.onDispose
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import domain.entity.Location
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import util.getMapUrl

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    currentLocation: Location,
    destination: Location?,
) {

    val state = rememberWebViewState(url = "https://www.google.com/maps")
    state.settings.javaScriptEnabled = true

    WebView(
        state = state,
        modifier = modifier.fillMaxSize()
    )


    /*  LaunchedEffect(state.isLoading) {
          // Get the current loading state
      }

      AnimatedVisibility(destination == null) {
          LaunchedEffect(true) {
              val jsCode = """
                  GetMap();
                  clearDirections();
              """.trimIndent()

              state.evaluateJavascript(jsCode, null)
          }
      }

      LaunchedEffect(key1 = currentLocation) {
          destination?.let { location ->
              state.evaluateJavascript("clearDirections()", null)
              state.evaluateJavascript(
                  "getDirections(${currentLocation.latitude},${currentLocation.longitude},${location.latitude},${location.longitude})",
                  null
              )
          }
      }*/

}


val mapHtml = """
   <!DOCTYPE html>
<html>
<head>
    <title>Leaflet Map Example</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
    <style>
        #map {
            height: 400px;
        }
    </style>
</head>
<body>
<div id="map"></div>

<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
<script>
        var map = L.map('map', {
            center: [51.505, -0.09],
            zoom: 13,
            dragging: true, // Enable dragging to move the map
            tap: false // Disable touch-based panning for this example
        });

        L.tileLayer('https://tile.thunderforest.com/atlas/{z}/{x}/{y}.png?apikey=192edc9c6a234e869be48be7059e5529', {
            attribution: '&copy; <a href="https://www.thunderforest.com/">Thunderforest</a> contributors'
        }).addTo(map);

        var marker = L.marker([51.5, -0.09]).addTo(map)
            .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
            .openPopup();

        map.on('dblclick', function(e) {
            var newLatLng = e.latlng;
            marker.setLatLng(newLatLng)
                .bindPopup('New marker position: ' + newLatLng.toString())
                .openPopup();
        });
    </script>
</body>
</html>
"""


val fullHtmlWithJavaScript = mapHtml

