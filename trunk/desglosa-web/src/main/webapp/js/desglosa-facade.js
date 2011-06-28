function selectTower(id) {
	alert('Selected tower: ' + id);
}

function selectFactory(id) {
	alert('Selected factory: ' + id);
}

function selectProject(id) {
	showLoadingIndicator(true);
	$.getJSON("/desglosa-web/getSubprojectsAndProfilesByProjectIdJSON.action",
			{ id: id },
			function (data, status) {
				if (status == "success") {
					// one project -> one neightborhood
					var tower;
					var neighborhood = new Neighborhood();
					$.each(data.subprojects, function (i, subproject) {
						// one subproject -> one flat
						var color = subproject.profile.color;
						$.each(subproject.profile.views, function (i, view) {
							// set innerHTML for view.name
							if (view.chart.maxCols < view.dimensions.length) {
								alert ('Hay mas atributos que columnas, se ignoraran los sobrantes.');
							}
							if (view.chart.name == "towers") {
								tower = new Object();
								tower = configureTower(color, view.dimensions);
								tower.id = subproject.id;
								neighborhood.flats.push(tower);
							}
						});
					});
					var city = new City();
					city.neighborhoods.push(neighborhood);
					// Convert project array to JSON format
					var JSONtext = JSON.stringify(city);
					// Change active view
					document.DesglosaApplet.visualizeTowers(JSONtext);
				}
				else alert('An error has occurred while trying to retrieve company information: ' + status);
				showLoadingIndicator(false);
	});
}

function configureTower(color, dimensions) {
	var MAX_DEPTH = 3.0;
	var MAX_WIDTH = 3.0;
	var MAX_HEIGHT = 12.0;
	var tower = new Object();
	$.each(dimensions, function (i, item) {
		if (item.attr == "width") tower.width = item.value*MAX_WIDTH/item.measure.high;
		else if (item.attr == "height") tower.height = item.value*MAX_HEIGHT/item.measure.high;
		else if (item.attr == "depth") tower.depth = item.value*MAX_DEPTH/item.measure.high;
		else if (item.attr == "color") {
			if (item.value <= item.measure.medium * 0.90) tower.color = color.nonAcceptable;
			else if (item.value < item.measure.medium * 0.90 && item.value > item.measure.medium * 1.10) tower.color = color.peripheral;
			else tower.color = color.acceptable;
		}
		else if (item.attr == "fill") tower.fill = item.value*MAX_HEIGHT/item.measure.high;
		else ;
	});
	return tower;
}

function City () {
	this.neighborhoods = new Array();
}

function Neighborhood () {
	this.flats = new Array();
}

function desglosa_showFactoriesByCompanyId(id) {
	desglosa_showFactories("/desglosa-web/getFactoriesFromCompanyJSON.action", id);
}

function desglosa_showFactoriesById(id) {
	desglosa_showFactories("/desglosa-web/getFactoriesJSON.action", id);
}

function desglosa_showFactories(action, id) {
	showLoadingIndicator(true);
	$.getJSON(action,
			{id: id},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Configure items
					var factory;
					var neighborhood = new Neighborhood();
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
					// Change active view
					document.DesglosaApplet.visualizeFactories(JSONtext);
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
				showLoadingIndicator(false);
	});
}

function desglosa_showProjectsById(id) {
	showLoadingIndicator(true);
	$.getJSON("/desglosa-web/getProjectsJSON.action",
			{id: id},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Configure items
					var project;
					var neighborhood = new Neighborhood();
					$.each(data.projects, function (i, item) {
						project = new Object();
						project.id = item.id;
						project.name = item.name;
						project.audited = item.audited;
						project.size = item.size;
						project.totalIncidences = item.totalIncidences;
						project.repairedIncidences = item.repairedIncidences;
						project.market = item.market.name;
						project.color = item.market.color;
						neighborhood.flats.push(project);
					});
					var city = new City();
					city.neighborhoods.push(neighborhood);
					// Convert project array to JSON format
					var JSONtext = JSON.stringify(city);
					// Change active view
					document.DesglosaApplet.visualizeProjects(JSONtext);
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
				showLoadingIndicator(false);
	});
}
