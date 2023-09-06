const $loginButton = document.getElementById('login');
const $welcome = document.getElementById('welcome');
const $afterLogin = document.getElementById('afterLogin');
const $welcomeNameId = document.getElementById('welcomeNameID');
const $tokenContent = document.getElementById('tokenContent');
const $userContent = document.getElementById('userContent');
const $error = document.getElementById('error');
const $userIdField = document.getElementById('userIdField');
const $form = document.getElementById('updateventi');
const $hospital = document.getElementById('hospital');
var ff;
const $hospitalname = document.getElementById('hospitalname');
const $longi = document.getElementById('longi');
const $lati = document.getElementById('lati');

const appID = new AppID();

async function onLoginButtonClick() {
	try {
        alert("If you are trying to register/signup, then please enter the hospital name in the 'First Name' and the branch name as 'Last Name'.\n For eg.\nFirst Name: Meenakshi Hospital\nLast Name: Chennai.");
		hideElement($loginButton);

		const tokens = await appID.signin();
		let userInfo = await appID.getUserInfo(tokens.accessToken);

		hideElement($welcome);
		showElement($afterLogin);

		let decodeIDToken = tokens.idTokenPayload;
        document.cookie = "sub="+ userInfo.identities[0].id.toString()+"; expires=Thu, 18 Dec 2020 12:00:00 UTC; path=/";
		$welcomeNameId.textContent = 'Hi ' + decodeIDToken.name + ', Congratulations!';
		$tokenContent.textContent = JSON.stringify(decodeIDToken);
        $userContent.textContent = JSON.stringify(userInfo);
        $userIdField.setAttribute('value',userInfo.identities[0].id.toString());
        $form.setAttribute('action','/createVentilator');
        let hospId = userInfo.sub.slice(0,4).toString() + userInfo.name.slice(0,4).toLowerCase().toString() + userInfo.identities[0].id.slice(-4).toString();
        $hospital.setAttribute('value', hospId);
        $hospitalname.setAttribute('value', decodeIDToken.name);

        

	} catch (e) {
		$error.textContent = e;
		showElement($loginButton);
	}
}

(async () => {
	try {
		await appID.init({
            clientId: "cfd0de00-b47a-47c0-9fa4-c5638875926e",
            discoveryEndpoint: "https://eu-gb.appid.cloud.ibm.com/oauth/v4/649e78bc-aeb9-49c8-a799-6e9a73109041/.well-known/openid-configuration"
        });
        showElement($loginButton);
		$loginButton.addEventListener('click', onLoginButtonClick);
	} catch (e) {
		$error.textContent = e;
	}
})();

function hideElement($element) {
	$element.classList.add('hidden');
}

function showElement($element) {
	$element.classList.remove('hidden');
}

const collaps = document.getElementsByClassName("collapsible");
for (let collapsible of collaps) {
	const btn = collapsible.getElementsByTagName("button")[0];
	btn.addEventListener("click", () => {
		collapsible.classList.toggle("active");
	});
}
var queri = document.getElementById("address");
var form = document.getElementById("form");
var iframe = document.getElementById("iframe");

form.onsubmit = function (e){
    e.preventDefault();
    var query = queri.value;
    document.getElementById("demo").innerHTML='';
    var xhttp = new XMLHttpRequest();   
        xhttp.open("GET", "https://dev.virtualearth.net/REST/v1/Locations/"+query+"?includeNeighborhood=&maxResults=10&include=&key=Av4EXrI3jTDkujMDtUrjtMrGUwuCSE52KLBcxvxbI78c3hA32ayoocYbl8MwQ5zv&output=json", true);
        xhttp.send();
        
xhttp.onreadystatechange = function() {
  if (this.readyState == 4 && this.status == 200) {
      var ures=this.responseText;
      
    for (var i in JSON.parse(ures).resourceSets[0].resources){
          console.log(i)
          var ele=document.createElement('p');
          var c = JSON.parse(ures).resourceSets[0].resources[i];
          ele.textContent = c.address.formattedAddress + ", "+ c.address.adminDistrict2+', ' +c.address.adminDistrict+", " +c.address.countryRegion ;
          ele.setAttribute('data-longi',c.geocodePoints[0].coordinates[0]);
          ele.setAttribute('data-lati',c.geocodePoints[0].coordinates[1]);
          ele.setAttribute('class','points');
          document.getElementById("demo").appendChild(ele);
      }
    let loc = document.querySelectorAll(".points");
    loc.forEach(function(elem) {
    elem.addEventListener('click',(e)=>{
	    e.stopPropagation();
        let longi=e.target.getAttribute('data-longi');
        let lati=e.target.getAttribute('data-lati');
        console.log(longi,lati)
        var map = new Microsoft.Maps.Map('#myMap', {
                credentials:'Av4EXrI3jTDkujMDtUrjtMrGUwuCSE52KLBcxvxbI78c3hA32ayoocYbl8MwQ5zv' ,
                center: new Microsoft.Maps.Location(longi,lati),
                zoom: 2,
        });
        var center = map.getCenter();
        var pin = new Microsoft.Maps.Pushpin(center, {
            title: e.target.textContent ,
            subTitle: 'Click select this area button to select',
            text: '1'
        });
        ff=[longi,lati]
        map.entities.push(pin);
    })})
  }
};
}
function selectThisArea(){
    $longi.setAttribute('value',ff[0])
    $lati.setAttribute('value',ff[1])
    alert("Done! Selected The Area")
}
function GetMap() {
            var map = new Microsoft.Maps.Map('#myMap', {
                credentials:'Av4EXrI3jTDkujMDtUrjtMrGUwuCSE52KLBcxvxbI78c3hA32ayoocYbl8MwQ5zv' ,
                center: new Microsoft.Maps.Location(25,80),
                zoom:2,
            });
            var center = map.getCenter();
            var pin = new Microsoft.Maps.Pushpin(center, {
                title: "MS" ,
                subTitle: "DEMO",
                text: '1'
            });
            map.entities.push(pin);
}