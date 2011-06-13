function selectTower(id) {
	alert('Selected tower: ' + id);
}

function selectFactory(id) {
	alert('Selected factory: ' + id);
}

function selectProject(id) {
	alert('Selected project: ' + id);
}

function selectFactoryByLocation(id) {
	alert('Selected factory by location: ' + id);
}

var applet = document.DesglosaApplet;

function startJSDesglosa() {
	var value = -1;
    value = applet.js2java("holaaaaaaaa");
    alert(value);
}

function desglosa_showFactoriesFromCompany(id) {
	alert ('Se van a mostrar las factorias de la compañía: ' + id);
}