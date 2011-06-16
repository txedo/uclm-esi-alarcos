function selectTower(id) {
	alert('Selected tower: ' + id);
}

function selectFactory(id) {
	alert('Selected factory: ' + id);
}

function selectProject(id) {
	alert('Selected project: ' + id);
}

function City () {
	this.neighborhoods = new Array();
}

function Neighborhood () {
	this.flats = new Array();
}

function desglosa_showFactoriesFromCompany(id) {
	showLoadingIndicator(true);
	$.getJSON("/desglosa-web/getFactoriesFromCompanyJSON.action",
			{id: id},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Configure items
					var factory;
					var neighborhood = new Neighborhood();;
					$.each(data.factories, function (i, item) {
						factory = new Object();
						factory.id = item.id;
						factory.employees = item.employees;
						factory.projects = 1;
						neighborhood.flats.push(factory);
					});
					var city = new City();
					city.neighborhoods.push(neighborhood);
					// Convert factory array to JSON format
					var JSONtext = JSON.stringify(city);
					//alert (JSONtext);
					// Change active view
					document.DesglosaApplet.visualizeFactories(JSONtext);
					alert('done');
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
				showLoadingIndicator(false);
	});
}

function desglosa_showAllFactories() {
	alert ('Se van a mostrar todas las factorias de todas las compañías.');
}

function desglosa_showFactories(ids) {
	alert ('Se van a mostrar las factorias de la compañía: ' + id);
}