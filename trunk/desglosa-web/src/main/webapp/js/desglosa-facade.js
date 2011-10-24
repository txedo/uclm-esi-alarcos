///////////////////////////////////////////////////////
////////// BEGINING OF DESGLOSA-FACADE.JS /////////////
///////////////////////////////////////////////////////

var currentEntity = null;
var currentEntityId;

function getNextLevel () {
	var nextLevel = null;
	if (currentEntity == null) nextLevel = "company";
	else if (currentEntity == "company") nextLevel = "factory";
	else if (currentEntity == "factory") nextLevel = "project";
	else if (currentEntity == "project") nextLevel = "subproject";
	else if (currentEntity == "subproject") nextLevel = null;
	return nextLevel;
}

function selectTower(id, clickButton, clickCount) {
	handleSelectionEvent(id, clickButton, clickCount);
}

function selectBuilding(id, clickButton, clickCount) {
	handleSelectionEvent(id, clickButton, clickCount);
}

function selectAntennaBall(id, clickButton, clickCount) {
	handleSelectionEvent(id, clickButton, clickCount);
}

function selectionError(message) {
	alert(message);
}

function handleSelectionEvent(id, clickButton, clickCount) {
	// This function will handle the selection event on any 3D model, so it will handle navigation too
	currentEntityId = id;
	// Show popup to allow groupBy and next level profile selection
	switch (clickButton) {
		case 1:		// Left button
			switch (clickCount) {
				case 1:		// Click
					$('#infoDialogBody').html("Load " + currentEntity + " (" + currentEntityId + ") information.");
					$('#infoDialog').dialog('open');
					break;
				case 2:		// Double click
					$('#profileChooserDialogMessages').html("Navigate to " + getNextLevel() + " from " + currentEntity + " (" + currentEntityId + ") information.");
					$('#profileChooserDialog').dialog('open');
					var nextLevel = getNextLevel();
					if (currentEntity == "company" && nextLevel == "factory") {
						// load factory profiles
						openDialog("desglosa_showFactoriesByCompanyId", nextLevel, id, true, false, true, true, true);
					} else if (currentEntity == "factory" && nextLevel == "project") {
						// load project profiles
						openDialog("desglosa_showProjectsByFactoryId", nextLevel, id, true, true, false, true, true);
					} else if (currentEntity == "project" && nextLevel == "subproject") {
						// load subproject profiles
						openDialog("desglosa_showSubprojectsByProjectId", nextLevel, id, true, true, true, false, true);
					} else {
						// No further navigation
					}
					break;
				default:	// Ignore multiple clicks but double click
					break;
			}
			break;
		case 2:		// Middle button
			break;
		case 3:		// Right button
			break;
		default:	// Any other button
			break;
	}
}

function desglosa_showCompaniesById(id, groupBy, profileFilename){
	currentEntity = "company";
	desglosa_launchDesglosaEngine("/desglosa-web/json_companyById.action", id, groupBy, profileFilename);
}

function desglosa_showFactoriesByCompanyId(id, groupBy, profileFilename) {
	currentEntity = "factory";
	desglosa_launchDesglosaEngine("/desglosa-web/json_factoriesByCompanyId.action", id, groupBy, profileFilename);
}

function desglosa_showFactoriesById(id, groupBy, profileFilename) {
	currentEntity = "factory";
	desglosa_launchDesglosaEngine("/desglosa-web/json_factoryById.action", id, groupBy, profileFilename);
}

function desglosa_showProjectsByCompanyId(id, groupBy, profileFilename) {
	currentEntity = "project";
	desglosa_launchDesglosaEngine("/desglosa-web/json_projectsByCompanyId.action", id, groupBy, profileFilename);
}

function desglosa_showProjectsByFactoryId(id, groupBy, profileFilename) {
	currentEntity = "project";
	desglosa_launchDesglosaEngine("/desglosa-web/json_projectsByFactoryId.action", id, groupBy, profileFilename);
}

function desglosa_showProjectsById(id, groupBy, profileFilename) {
	currentEntity = "project";
	desglosa_launchDesglosaEngine("/desglosa-web/json_projectById.action", id, groupBy, profileFilename);
}

function desglosa_showSubprojectsByCompanyId(id, groupBy, profileFilename) {
	currentEntity = "subproject";
	desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectsByCompanyId.action", id, groupBy, profileFilename);
}

function desglosa_showSubprojectsByFactoryId(id, groupBy, profileFilename) {
	currentEntity = "subproject";
	desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectsByFactoryId.action", id, groupBy, profileFilename);
}

function desglosa_showSubprojectsByProjectId(id, groupBy, profileFilename) {
	currentEntity = "subproject";
	desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectsByFactoryId.action", id, groupBy, profileFilename);
}

function desglosa_showSubprojectsById(id, groupBy, profileFilename) {
	currentEntity = "subproject";
	desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectById.action", id, groupBy, profileFilename);
}

function desglosa_launchDesglosaEngine (action, id, groupBy, filename) {
	showLoadingIndicator(true);
	// Hide map canvas
	if (document.getElementById("map_canvas").style.display == '') $('#map_canvas').css('display','none');
	// Hide jogl canvas if it is shown
	if (document.getElementById("jogl_canvas").style.display == '') $('#jogl_canvas').css('display','none');
	$.getJSON(action,
			{
				id: id,
				generateGLObjects: true,
				groupBy: groupBy,
				profileFileName: filename
			},
			function (data, status) {
				if (status == "success") {
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Change active view
					var city = JSON.stringify(data.city);
					if (city != "null") {
						desglosa_handleVisualization(data.city.model, city);
					} else {
						$('#jogl_canvas').css('display','none');
						$('#map_canvas').css('display','')
						$('#errorDialogBody').html("<p class='error'><c:out value='${malformedJSONStrnig}'/></p>");
						$('#errorDialog').dialog('open');
					}
				} else {
					$('#jogl_canvas').css('display','none');
					$('#map_canvas').css('display','');
					$('#errorDialogBody').html("<p class='error'><c:out value='${generalError}'/></p>");
					$('#errorDialog').dialog('open');
				}
				showLoadingIndicator(false);
	});
}

function desglosa_handleVisualization(model, city) {
	if (model == "model.gl.knowledge.GLTower") {
		document.DesglosaApplet.visualizeTowers(city);
	} else if (model == "model.gl.knowledge.GLAntennaBall") {
		document.DesglosaApplet.visualizeAntennaBalls(city);
	} else if (model == "model.gl.knowledge.GLFactory") {
		document.DesglosaApplet.visualizeBuildings(city);
	}
}


///////////////////////////////////////////////////////
//////////// END OF DESGLOSA-FACADE.JS ////////////////
///////////////////////////////////////////////////////