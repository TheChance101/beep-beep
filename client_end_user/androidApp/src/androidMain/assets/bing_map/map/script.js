var map, directionsManager, line;

function GetMap() {
    map = new Microsoft.Maps.Map('#myMap', {
        credentials: 'Access_token',
        mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
        minZoom: 1,
        maxZoom: 14
    });
}

function getDirections(startLat, startLng, endLat, endLng) {
    // Load the Microsoft Maps Directions module
    Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {

        // Initialize the DirectionsManager with the existing map
        directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);

        // ... (Your existing code for setting up directions)

        // Calculate the directions
        directionsManager.calculateDirections(function () {
            // After calculation, zoom to the destination
            zoomToDestination(endLat, endLng);

            // Draw a red line between the current location and destination
            drawRedLine(startLat, startLng, endLat, endLng);
        });
    });
}

function zoomToDestination(lat, lng) {
    var location = new Microsoft.Maps.Location(lat, lng);
    map.setView({ center: location, zoom: 10 }); // You can adjust the zoom level as needed
}

function drawRedLine(startLat, startLng, endLat, endLng) {
    var coordinates = [
        new Microsoft.Maps.Location(startLat, startLng),
        new Microsoft.Maps.Location(endLat, endLng)
    ];

    line = new Microsoft.Maps.Polyline(coordinates, {
        strokeColor: 'red',
        strokeThickness: 2
    });

    map.entities.push(line);
}

function clearDirections() {
    directionsManager.clearAll();
    directionsManager.clearDisplay();
}

function clearMap() {
    map.entities.clear();
}
