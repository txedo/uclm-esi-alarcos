function selectTower(id) {
	alert('Selected tower: ' + id);
}

function selectFactory(id) {
	alert('Selected factory: ' + id);
}

function selectProject(id) {
	alert('Selected project: ' + id);
}

function desglosa_showFactoriesFromCompany(id) {
	$.getJSON("/desglosa-web/getFactoriesFromCompanyJSON.action",
			{id: id},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Configure items
					var factories = new Array();
					var neighborhood = 0;
					var factory;
					$.each(data.factories, function (i, item) {
						factory = new Object();
						factory.neighborhood = neighborhood;
						factory.id = item.id;
						factory.employees = item.employees;
						factory.projects = 1;
						factories.push(factory);
					});
					// Convert factory array to JSON format
					var JSONtext = JSON.stringify(factories);
					// Change active view
					document.DesglosaApplet.visualizeFactories(JSONtext);
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
	});
}

function foo () {
	document.DesglosaApplet.foo();
}

function desglosa_showAllFactories() {
	alert ('Se van a mostrar todas las factorias de todas las compañías.');
}

function desglosa_showFactories(ids) {
	alert ('Se van a mostrar las factorias de la compañía: ' + id);
}