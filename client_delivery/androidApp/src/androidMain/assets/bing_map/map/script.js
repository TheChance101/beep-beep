var latitude2 = 0
var map,directionsManager;

function GetMap() {
map = new Microsoft.Maps.Map('#myMap', {

    credentials: 'Access_token',
    center: new Microsoft.Maps.Location(29.0,32.0),
    mapTypeId: Microsoft.Maps.MapTypeId.grayscale,
    minZoom: 2,
    maxZoom: 7
});
    var center = map.getCenter();
   var pin = new Microsoft.Maps.Pushpin(center, {
       color: 'red',
   });
   map.entities.push(pin);
}

function getDirections(lat,lng,latt,lngg){
Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {
    directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);
    directionsManager.setRequestOptions({
        routeMode: Microsoft.Maps.Directions.RouteMode.driving,
        routeDraggable: false,
        autoUpdateMapView: false
    });
    directionsManager.setRenderOptions({
                   drivingPolylineOptions: {
                       strokeColor: 'red',
                       strokeThickness: 2
                   },
                   autoUpdateMapView:false
               });
    var waypoint1 = new Microsoft.Maps.Directions.Waypoint({ location: new Microsoft.Maps.Location(latt,lngg) });
    var waypoint2 = new Microsoft.Maps.Directions.Waypoint({ location: new Microsoft.Maps.Location(lat,lng) });
    directionsManager.addWaypoint(waypoint1);
    directionsManager.addWaypoint(waypoint2);
    directionsManager.calculateDirections();
    });
    var newCenter = new Microsoft.Maps.Location(lat-1, lng-1.5);
    map.setView({
            center: map.getCenter(),
        });
}

function clearDirections() {
directionsManager.clearAll();
directionsManager.clearDisplay();
}

function clearMap(){
map.entities.clear();
}

