package presentation.map.composable


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import com.mohamedrejeb.calf.ui.web.rememberWebViewStateWithHTMLData
import presentation.map.LocationUiState

@Composable
fun MapView(
    deliveryLocation: LocationUiState,
    destination: LocationUiState,
    modifier: Modifier = Modifier,
) {
    val stateWebView = rememberWebViewStateWithHTMLData(
        data = map,
        mimeType = "text/html",
        encoding = "UTF-8"
    )

    stateWebView.settings.javaScriptEnabled = true
    WebView(
        state = stateWebView,
        modifier = modifier.fillMaxSize()
    )

    AnimatedVisibility(false) {
        LaunchedEffect(true) {
            val jsCode = """
                GetMap();
                clearDirections();
            """.trimIndent()

            stateWebView.evaluateJavascript(jsCode, null)
        }
    }

    LaunchedEffect(key1 = deliveryLocation) {
        destination.let { location ->
            stateWebView.evaluateJavascript("clearDirections()", null)
            stateWebView.evaluateJavascript(
                "getDirections(${deliveryLocation.lat},${deliveryLocation.lng},${location.lat},${location.lng})",
                null
            )
        }
    }
}


private val map1 = """<!DOCTYPE html>
    <html>
    <head>
        <title>OpenStreetMap Example</title>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
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
        var map = L.map('map').setView([47.6062, -122.3321], 12);
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        var marker;

        function handleMapClick(e) { // Corrected function name here
            if (marker) {
                map.removeLayer(marker);
            }
            marker = L.marker(e.latlng, { draggable: true }).addTo(map)
                .bindPopup('Drag me!')
                .openPopup();

            // Send coordinates back to JavaFX-Swing communication channel
            window.javaObj.markerClickEvent(JSON.stringify(e.latlng));
        }

        map.on('click', handleMapClick);

    </script>
    </body>
    </html>""".trimIndent()


private val map = """<!doctype HTML><head>
    <link rel="stylesheet" href="style.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,700" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo-respircov.png">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
</head>
<body>
<div class="content">
    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&branch=experimental&key=AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO' async defer></script>
    <div id="myMap"></div>
</div>
<script>
    var map, directionsManager;

    function GetMap() {
        map = new Microsoft.Maps.Map('#myMap', {
            credentials: 'Access_token',
            mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
            minZoom: 2,
            maxZoom: 7
        });
    }

    function getDirections(startLat, startLng, endLat, endLng) {
        // Load the Microsoft Maps Directions module
        Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {

            // Initialize the DirectionsManager with the existing map
            directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);

            // Configure the request options for route calculation
            const requestOptions = {
                routeMode: Microsoft.Maps.Directions.RouteMode.driving,
                routeDraggable: false,
                autoUpdateMapView: false
            };
            directionsManager.setRequestOptions(requestOptions);

            // Configure the render options for the route
            const renderOptions = {
                drivingPolylineOptions: {
                    strokeColor: 'red',
                    strokeThickness: 4
                },
                autoUpdateMapView: false
            };
            directionsManager.setRenderOptions(renderOptions);

            // Create waypoints using the provided latitude and longitude
            const startWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(startLat, startLng)
            });
            const endWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(endLat, endLng)
            });

            // Add the waypoints to the directions manager
            directionsManager.addWaypoint(startWaypoint);
            directionsManager.addWaypoint(endWaypoint);

            // Calculate the directions
            directionsManager.calculateDirections();
        });
    }

    function getRouteTime() {
        return directionsManager.time;
    }

    function clearDirections() {
        directionsManager.clearAll();
        directionsManager.clearDisplay();
    }

    function clearMap() {
        map.entities.clear();
    }
</script>
<script src="script.js" async></script>
</body>""".trimIndent()


private val map3 = """<!doctype HTML><head>
    <link rel="stylesheet" href="style.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,700" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo-respircov.png">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
</head>
<body>
<div class="content">
    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&branch=experimental&key=AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO' async defer></script>
    <div id="myMap"></div>
</div>
<script>
    var map, directionsManager, currentLocationIcon;

    function GetMap() {
        map = new Microsoft.Maps.Map('#myMap', {
            credentials: 'Access_token',
            mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
            center: new Microsoft.Maps.Location(47.6062, -122.3321), // Coordinates for Seattle, Washington
            zoom: 12,
            minZoom: 2,
            maxZoom: 20
        });

        // Add current location icon at initialization
        currentLocationIcon = new Microsoft.Maps.Pushpin(map.getCenter(), {
            icon: 'pin', // Using the built-in pin symbol
            anchor: new Microsoft.Maps.Point(12, 12) // Adjust anchor point if needed
        });
        map.entities.push(currentLocationIcon);
    }

    function getDirections(startLat, startLng, endLat, endLng) {
        // Load the Microsoft Maps Directions module
        Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {

            // Initialize the DirectionsManager with the existing map
            directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);

            // Configure the request options for route calculation
            const requestOptions = {
                routeMode: Microsoft.Maps.Directions.RouteMode.driving,
                routeDraggable: false,
                autoUpdateMapView: false
            };
            directionsManager.setRequestOptions(requestOptions);

            // Configure the render options for the route
            const renderOptions = {
                drivingPolylineOptions: {
                    strokeColor: 'red',
                    strokeThickness: 4
                },
                autoUpdateMapView: false
            };
            directionsManager.setRenderOptions(renderOptions);

            // Create waypoints using the provided latitude and longitude
            const startWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(startLat, startLng)
            });
            const endWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(endLat, endLng)
            });

            // Add the waypoints to the directions manager
            directionsManager.addWaypoint(startWaypoint);
            directionsManager.addWaypoint(endWaypoint);

            // Calculate the directions
            directionsManager.calculateDirections();
        });
    }

    function getRouteTime() {
        return directionsManager.time;
    }

    function clearDirections() {
        directionsManager.clearAll();
        directionsManager.clearDisplay();
    }

    function clearMap() {
        map.entities.clear();
    }

    // Function to set the current location icon
    function setMapIcon(lat, lng) {
        const location = new Microsoft.Maps.Location(lat, lng);
        currentLocationIcon.setLocation(location);
        map.setView({ center: location });
    }
</script>
<script src="script.js" async></script>
</body>""".trimIndent()


private val map4 = """<!doctype HTML><head>
    <link rel="stylesheet" href="style.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,700" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo-respircov.png">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
</head>
<body>
<div class="content">
    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&branch=experimental&key=AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO' async defer></script>
    <div id="myMap"></div>
</div>
<script>
    var map, directionsManager, currentLocationIcon;

    function GetMap() {
        map = new Microsoft.Maps.Map('#myMap', {
            credentials: 'Access_token',
            mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
            center: new Microsoft.Maps.Location(47.6062, -122.3321), // Coordinates for Seattle, Washington
            zoom: 12,
            minZoom: 2,
            maxZoom: 20
        });

        // Use addThrottledHandler to wait for the map to be fully initialized
        Microsoft.Maps.Events.addThrottledHandler(map, 'viewchangeend', function () {
            // Add current location icon after the map is fully initialized
            currentLocationIcon = new Microsoft.Maps.Pushpin(map.getCenter(), {
                icon: 'pin', // Using the built-in pin symbol
                anchor: new Microsoft.Maps.Point(12, 12) // Adjust anchor point if needed
            });
            map.entities.push(currentLocationIcon);
        });

        // Add click event listener to the map
        Microsoft.Maps.Events.addHandler(map, 'click', addPinOnClick);
    }

    // Function to handle map click event and add a pin
    function addPinOnClick(e) {
        // Get the location where the user clicked
        var location = e.location;

        // Create a new Pushpin at the clicked location
        var pin = new Microsoft.Maps.Pushpin(location, {
            icon: 'pin',
            anchor: new Microsoft.Maps.Point(12, 12)
        });

        // Add the Pushpin to the map
        map.entities.push(pin);
    }

    function getDirections(startLat, startLng, endLat, endLng) {
        // Load the Microsoft Maps Directions module
        Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {

            // Initialize the DirectionsManager with the existing map
            directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);

            // Configure the request options for route calculation
            const requestOptions = {
                routeMode: Microsoft.Maps.Directions.RouteMode.driving,
                routeDraggable: false,
                autoUpdateMapView: false
            };
            directionsManager.setRequestOptions(requestOptions);

            // Configure the render options for the route
            const renderOptions = {
                drivingPolylineOptions: {
                    strokeColor: 'red',
                    strokeThickness: 4
                },
                autoUpdateMapView: false
            };
            directionsManager.setRenderOptions(renderOptions);

            // Create waypoints using the provided latitude and longitude
            const startWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(startLat, startLng)
            });
            const endWaypoint = new Microsoft.Maps.Directions.Waypoint({
                location: new Microsoft.Maps.Location(endLat, endLng)
            });

            // Add the waypoints to the directions manager
            directionsManager.addWaypoint(startWaypoint);
            directionsManager.addWaypoint(endWaypoint);

            // Calculate the directions
            directionsManager.calculateDirections();
        });
    }

    function getRouteTime() {
        return directionsManager.time;
    }

    function clearDirections() {
        directionsManager.clearAll();
        directionsManager.clearDisplay();
    }

    function clearMap() {
        map.entities.clear();
    }

    // Function to set the current location icon
    function setMapIcon(lat, lng) {
        const location = new Microsoft.Maps.Location(lat, lng);
        currentLocationIcon.setLocation(location);
        map.setView({ center: location });
    }
</script>
<script src="script.js" async></script>
</body>""".trimIndent()


val mapWithIcon = """
   <!DOCTYPE html>
<html><head>
    <link rel="stylesheet" href="style.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,700" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo-respircov.png">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
    <style>
        #myMap {
            height: 400px;
        }
    </style>
</head>

<body>
    <div class="content">
        <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&branch=experimental&key=AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO' async defer></script>
        <div id="myMap"></div>
    </div>

    <script>
        var map, directionsManager, currentLocationIcon;

        function GetMap() {
            map = new Microsoft.Maps.Map('#myMap', {
                credentials: 'AgNnGNredNlcyG1M8AET0L9F43oDcjEUyZnPhX2mOf5nHNnQcFkz9IFHN_c7z6fO',
                mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
                center: new Microsoft.Maps.Location(47.6062, -122.3321), // Coordinates for Seattle, Washington
                zoom: 12,
                minZoom: 2,
                maxZoom: 20
            });

            // Use addThrottledHandler to wait for the map to be fully initialized
            Microsoft.Maps.Events.addThrottledHandler(map, 'viewchangeend', function () {
                // Add current location icon after the map is fully initialized
                currentLocationIcon = new Microsoft.Maps.Pushpin(map.getCenter(), {
                    icon: 'pin', // Using the built-in pin symbol
                    anchor: new Microsoft.Maps.Point(12, 12) // Adjust anchor point if needed
                });
                map.entities.push(currentLocationIcon);
            });

            // Add click event listener to the map
            Microsoft.Maps.Events.addHandler(map, 'click', handleMapClick);
        }

        // Function to handle map click event and add a marker
        function handleMapClick(e) {
            // Get the location where the user clicked
            var location = e.location;

            // Clear existing marker
            if (currentLocationIcon) {
                map.entities.remove(currentLocationIcon);
            }

            // Create a new marker at the clicked location
            currentLocationIcon = new Microsoft.Maps.Pushpin(location, {
                icon: '', // Empty string to remove the default marker image
                anchor: new Microsoft.Maps.Point(0, 0) // Adjust anchor point if needed
            });

            // Add the marker to the map
            map.entities.push(currentLocationIcon);

            // Send coordinates back to JavaFX-Swing communication channel
            window.javaObj.markerClickEvent(JSON.stringify(location));
        }
    </script>
    <script src="script.js" async></script>
</body>

</html>
""".trimIndent()




