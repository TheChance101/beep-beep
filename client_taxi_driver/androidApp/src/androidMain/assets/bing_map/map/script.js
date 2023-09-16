var latitude2 = 0
var map,directionsManager;

function GetMap() {

map = new Microsoft.Maps.Map('#myMap', {
    credentials: 'Access_token',
    center: new Microsoft.Maps.Location(0,0),
    mapTypeId: Microsoft.Maps.MapTypeId.road,
    minZoom: 16,
    maxZoom: 18
});
    var center = map.getCenter();
           //Create custom Pushpin
           var pin = new Microsoft.Maps.Pushpin(center, {
               title: 'Microsoft',
               subTitle: 'City Center',
               text: '1',
               color: 'red',
           });
           //Add the pushpin to the map
           map.entities.push(pin);
            Microsoft.Maps.Events.addHandler(map, 'click', function () {
            pin.setLocation(map.getCenter())
            });
            getDirections()
}
function getPin(){
    var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), null);
    map.entities.push(pushpin);
}
function getDirections(){
Microsoft.Maps.loadModule('Microsoft.Maps.Directions', function () {
    directionsManager = new Microsoft.Maps.Directions.DirectionsManager(map);
    directionsManager.setRequestOptions({ routeMode: Microsoft.Maps.Directions.RouteMode.driving });
    var waypoint1 = new Microsoft.Maps.Directions.Waypoint({ location: new Microsoft.Maps.Location(40.6790229, -73.8740306) });
    var waypoint2 = new Microsoft.Maps.Directions.Waypoint({ location: new Microsoft.Maps.Location(40.6740229, -73.8740306) });
    directionsManager.addWaypoint(waypoint1);
    directionsManager.addWaypoint(waypoint2);
    directionsManager.calculateDirections();
});
}
function updateDirections(newLat) {
directionsManager.removeWaypoint(1);
  var waypoint2 = new Microsoft.Maps.Directions.Waypoint({ location: new Microsoft.Maps.Location(newLat, -73.8740306) });
   directionsManager.addWaypoint(waypoint2);
    directionsManager.calculateDirections();
}

var xhttp = new XMLHttpRequest();
var block,bs;
var d={}
var co = 0;
function createInfiniteLoopFunction(latitude) {
            return function() {
            clearMap()
    var pushpin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(latitude, -73.8740306), {
        icon: 'user_pin.svg',
     anchor: new Microsoft.Maps.Point(20, 20)
  });
   Microsoft.Maps.loadModule('Microsoft.Maps.SpatialMath', function () {
    let circle = createCircle(new Microsoft.Maps.Location(latitude, -73.8740306),120,'rgba(31, 0, 0, 0.16)')
        map.entities.push(circle);
       map.entities.push(pushpin);})
                    map.setView({ center: new Microsoft.Maps.Location(latitude, -73.8740306), minZoom: 16,
            maxZoom: 18});
        updateDirections(latitude)
            };
        }
function createCircle(center, radius, color) {
//Request the user's location
    //Calculate the locations for a regular polygon that has 36 locations which will rssult in an approximate circle.
    var locs = Microsoft.Maps.SpatialMath.getRegularPolygon(center, radius, 80, Microsoft.Maps.SpatialMath.Meters);

    return new Microsoft.Maps.Polygon(locs, { fillColor: color, strokeThickness: 0 });

}
function clearMap(){
map.entities.clear();
}
xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      block = this.responseText;
        var map = new Microsoft.Maps.Map('#myMap', {
            credentials: 'AlqUbMVkRpMkWMcFs_18FoZGeNcg8RLToYX5OeOCHnexAuUZxqtesONoCbT1sTAd',
            center: new Microsoft.Maps.Location(25,78),
            mapTypeId: Microsoft.Maps.MapTypeId.aerial,
            zoom: 5
        });
        infobox = new Microsoft.Maps.Infobox(map.getCenter(), {
            visible: false
        });
        infobox.setMap(map);
      renderBlocks(map);
    }
};
xhttp.open("GET", "/blocks", true);
xhttp.send();

const addPins = function(map,hospid,hospname,vacant,occupied,longi,lati,vbed,obed,k){
    if (longi!=null || lati!=null){
        co=co+1;
        var locs = new Microsoft.Maps.Location(longi,lati);
        var pin = new Microsoft.Maps.Pushpin(locs);
        pin.metadata ={
            title: hospname,
            description: '<table border="0px">\
            <tr> <th>Vacant</th> <th> Occupied</th></tr>\
            <tr><td>Ventilators</td></tr>\
            <tr><td>' +vacant.toString()+'</td><td>'+occupied.toString()+'</td>\
            <tr><td>Hospital Beds</td>\
            <tr><td>' +vbed.toString()+'</td><td>'+obed.toString() +'</td></tr>\
            </table><a href="https://www.google.com/maps/search/?api=1&query='+hospname+'">Get directions.'
        };
        Microsoft.Maps.Events.addHandler(pin, 'click', pushpinClicked);
        map.entities.push(pin);
    }
}
var vacants= occupieds = vbeds = obeds = 0;
const renderBlocks=function(map){
     bs = JSON.parse(block);
     for (var i in bs.chain){
        if (i!=0){
            d[bs.chain[i].ventilators[0].hospid]=[bs.chain[i].ventilators[0].hospitalname,parseInt(bs.chain[i].ventilators[0].vacant),parseInt(bs.chain[i].ventilators[0].occupied),bs.chain[i].ventilators[0].longi,bs.chain[i].ventilators[0].lati,parseInt(bs.chain[i].ventilators[0].vbed),parseInt(bs.chain[i].ventilators[0].obed)]
        }
     }
     console.log(d)
     pushAllPins(map)
}

const pushAllPins = function(map){
    var k = 1
    for (var j in d){
        vacants=vacants+d[j][1];
        occupieds=occupieds+d[j][2];
        vbeds = vbeds+d[j][5];
        obeds = obeds+d[j][6];
        addPins(map,j,d[j][0],d[j][1],d[j][2],d[j][3],d[j][4],d[j][5],d[j][6],k)
        k=k+1
     }
     document.querySelector(".vacant").innerHTML=vacants;
     document.querySelector(".occupied").innerHTML=occupieds;
     document.querySelector(".vbed").innerHTML=vbeds;
     document.querySelector(".obed").innerHTML=obeds;
     document.querySelector(".counts").innerHTML='<br><br>'+co.toString();
 };
 function pushpinClicked(e) {
        //Make sure the infobox has metadata to display.
        if (e.target.metadata) {
            //Set the infobox options with the metadata of the pushpin.
            infobox.setOptions({
                location: e.target.getLocation(),
                title: e.target.metadata.title,
                description: e.target.metadata.description,
                visible: true
            });
        }
    }
var ul = document.getElementById('dynamics');
function dynamicsearch(){
    var lo = document.getElementById('searchname').value;    
    ul.innerHTML=''
    for(var i in d) {
    if (d[i][0].search(lo)!=-1){
        ul.style.height='80px';
        let li = document.createElement('li');
        li.textContent=d[i][0]
        li.setAttribute('se-long',d[i][3])
        li.setAttribute('se-lat',d[i][4])
        li.setAttribute('se_id',i)
        ul.appendChild(li)
    }
    }
    document.querySelectorAll('li').forEach(function(elem) {
        elem.addEventListener('click',(e)=>{
        dynclick(e)
    })
})
}

function dynclick(e){
    ul.innerHTML=''
    ul.style.height='0px';
    var mnh = e.target.attributes[2].nodeValue
    document.querySelector('.searchres').innerHTML=e.target.textContent+"<br>\
    "+'<table border="0px">\
            <tr> <th>Vacant</th> <th> Occupied</th></tr>\
            <tr><td>Ventilators</td></tr>\
            <tr><td>' +d[mnh][2].toString()+'</td><td>'+d[mnh][1].toString()+'</td>\
            <tr><td>Hospital Beds</td>\
            <tr><td>' +d[mnh][6].toString()+'</td><td>'+d[mnh][5].toString() +'</td></tr>\
            </table><a href="https://www.google.com/maps/search/?api=1&query='+e.target.textContent+'">Get directions.'
            console.log(e)
}
function showson(){
    document.querySelector('.cvr').style.display='block';
    document.querySelector('.hdd').style.display='block';
    document.querySelector('#myMap').style.display='none';
    document.querySelector('#pseudo').style.display='block'; 
}
function cls(){
    document.querySelector('.cvr').style.display='none';
    document.querySelector('.hdd').style.display='none';
    document.querySelector('#myMap').style.display='block';
    document.querySelector('#pseudo').style.display='none'; 
}