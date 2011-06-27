function selectTower(id) {
	alert('Selected tower: ' + id);
}

function selectFactory(id) {
	alert('Selected factory: ' + id);
}

function selectProject(id) {
	showLoadingIndicator(true);
	$.getJSON("/desglosa-web/getSubprojectsAndProfilesByProjectIdJSON.action",
			{ id: id+""	},
			function (data, status) {
				if (status == "success") {
//					// one project -> one neightborhood
//					var tower;
//					var neighborhood = new Neighborhood();
//					$.each(data.subprojects, function (i, subproject) {
//						// one subproject -> one flat
//						$.each(subproject.profile.views, function (i, view) {
//							// set innerHTML for view.name
//							if (view.chart == "towers") {
//								tower = new Object();
//								tower = configureTower(view.dimensions, csvData);
//								neighborhood.flats.push(tower);
//							}
//						)};
//					});
				}
				else alert('An error has occurred while trying to retrieve company information: ' + status);
				showLoadingIndicator(false);
	});
}

//function configureTower(dimensions, csvData) {
//	var tower = new Object();
//	$.each(dimensions, function (i, item) {
//		var data = csvData[item.csvCol-1];
//		if (item.attr == "width") tower.width = data;
//		else if (item.attr == "height") tower.height = data;
//		else if (item.attr == "depth") tower.depth = data;
//		else if (item.attr == "color") tower.color = data;
//		else if (item.attr == "fill") tower.fill = data;
//	)};
//	return tower;
//}

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
