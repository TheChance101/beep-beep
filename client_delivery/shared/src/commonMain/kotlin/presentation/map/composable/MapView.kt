package presentation.map.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewStateWithHTMLData
import presentation.map.LocationUiState
import presentation.map.OrderState

@Composable
fun MapView(
    deliveryLocation: LocationUiState,
    restaurantLocation: LocationUiState,
    destination: LocationUiState,
    modifier: Modifier = Modifier,
    orderState: OrderState,
    calculateRouteDistance: (distance: String, time: String) -> Unit,
) {
    val stateWebView = rememberWebViewStateWithHTMLData(
        data = map,
        mimeType = "text/html",
        encoding = "UTF-8"
    )

    val jsCode = """
                GetMap();
                clearDirections();
            """.trimIndent()

    stateWebView.settings.javaScriptEnabled = true
    WebView(
        state = stateWebView,
        modifier = modifier.fillMaxSize(),
        onCreated = { stateWebView.evaluateJavascript(jsCode, null) }
    )

    LaunchedEffect(orderState) {
        if (orderState == OrderState.LOADING) {
            stateWebView.evaluateJavascript("clearDirections()", null)
        }
        if (orderState == OrderState.NEW_ORDER) {
            stateWebView.evaluateJavascript(
                "getDirections(${restaurantLocation.lat},${restaurantLocation.lng},${destination.lat},${destination.lng})",
                null
            )
        }

        if (orderState == OrderState.ACCEPTED) {
            val distanceJsCode = """
            calculateDistance(${restaurantLocation.lat},${restaurantLocation.lng},${destination.lat},${destination.lng})
             """.trimIndent()


            stateWebView.evaluateJavascript(distanceJsCode) { result ->
                println("Distance calculation result: $result")
                calculateRouteDistance(result.toString(), "20:22")
            }
        }
    }
}

private val map = """<!doctype HTML><head>
    <link rel="stylesheet" href="style.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,700" rel="stylesheet">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
            }

            .content {
                width: 100%;
                height: 100%;
            }

            #myMap {
                position: relative;
                width: 100%;
                height: 100%;
            }
        </style>
    <meta charset="utf-8"/>
</head>
<body>
<div class="content">
    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&branch=experimental&key=AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO' async defer></script>
    <div id="myMap" style="position:relative;width:100%;height:100%;"></div>
</div>
<script>
var map, directionsManager;
function GetMap() {
    map = new Microsoft.Maps.Map('#myMap', {
        credentials: 'Access_token',
        mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
        minZoom: 1,
        maxZoom: 10
    });
}

function getDirections(startLat, startLng, endLat, endLng) {
    Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {
        directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);

        const requestOptions = {
            routeMode: Microsoft.Maps.Directions.RouteMode.driving,
            routeDraggable: false,
            autoUpdateMapView: false,
        };

        const renderOptions = {
            drivingPolylineOptions: {
                strokeColor: 'red',
                strokeThickness: 8,
            },
            autoUpdateMapView: true
        };

        const startWaypoint = createWaypoint(startLat, startLng ,'../../../../resources/restaurant_location.svg');
        const endWaypoint = createWaypoint(endLat, endLng , '../../../../resources/current_location.svg');

        directionsManager.addWaypoint(startWaypoint);
        directionsManager.addWaypoint(endWaypoint);
        directionsManager.setRequestOptions(requestOptions);
        directionsManager.setRenderOptions(renderOptions); 
        directionsManager.calculateDirections();
    });
}

function createWaypoint(lat, lng , icon) {
    const pushpin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(lat, lng), {
        icon: icon,
        anchor: new Microsoft.Maps.Point(1, 1),
    });

    map.entities.push(pushpin)

    return new Microsoft.Maps.Directions.Waypoint({ location: pushpin.getLocation(), pushpin: pushpin });
}

function clearDirections() {
    directionsManager.clearAll();
    directionsManager.clearDisplay();
}

function clearMap() {
    map.entities.clear();
}

function calculateDistance(startLat, startLng, endLat, endLng) {
    const startLocation = new Microsoft.Maps.Location(startLat, startLng);
    const endLocation = new Microsoft.Maps.Location(endLat, endLng);
    
    const distanceInMeters = Microsoft.Maps.Location.calculateDistance(startLocation, endLocation);
    
    const distanceInKilometers = distanceInMeters / 1000;
    return distanceInKilometers;
}

</script>
<script src="script.js" async></script>
</body>""".trimIndent()