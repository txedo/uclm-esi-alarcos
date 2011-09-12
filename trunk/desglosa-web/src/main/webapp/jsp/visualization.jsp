<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="Visualization"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<link href="<s:url value='/styles/buttons.css'/>" rel="stylesheet" type="text/css" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<!-- <script type="text/javascript" src="js/desglosa-facade.js"></script> -->

  
	<script type="text/javascript">
	var map;
	var geocoder;
	var markers;
	var infoWindows;
	
	function initializeMap() {
		var latlng = new google.maps.LatLng(-34.397, 150.644);
	    var myOptions = {
	    	      zoom: 1,
	    	      center: latlng,
	    	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    	    };
	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	    geocoder = new google.maps.Geocoder();
	    markers = new Array();
	    infoWindows = new Array();
	}
	
	function createFactory (id, lat, lng) {
		var factory = new Object();
		factory.id = id;
		factory.location = new google.maps.LatLng(lat,lng);
		factories.push(factory);
	}
	
	function placeMarker(lat, lng) {
		var latLng = new google.maps.LatLng(lat,lng);
		marker = new google.maps.Marker({
		    map: map, 
		    position: latLng
		});
		markers.push(marker);
		return marker;
	}
	
	function clearAllMarkers() {
		var i = 0;
		for (i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = new Array();
		infoWindows = new Array();
	}
	
	function closeAllInfoWindows() {
		var i = 0;
		for (i = 0; i < infoWindows.length; i++) {
			infoWindows[i].close();
		}
	}
	
	function createInfoWindow(factoryJSON) {
		var resultHTML = "<b>" + factoryJSON.name + "</b> (" + factoryJSON.company.name + ")<br />";
		resultHTML += "<i>" + factoryJSON.company.information + "</i><br />";

		var infoWindow = new google.maps.InfoWindow();
		infoWindow.setContent(resultHTML);
		
		infoWindows.push(infoWindow);
		return infoWindow;
	}
	
	function addMarkerEvents(marker, infoWindow) {
		// show infowindow
		google.maps.event.addListener(marker, 'click', function(event) {
			closeAllInfoWindows();
			infoWindow.open(map, marker);
	    });
		
		google.maps.event.addListener(map, "click", function(){
			  infoWindow.close();
		});
		
		// show graphic engine
		google.maps.event.addListener(marker, 'dblclick', function(event) {
			$("#map_canvas").css("display", "none");
			$("#jogl_canvas").css("display", "");
	    });
	}
	
	function configureFactoryById(idFactory) {
		showLoadingIndicator(true);
		$.getJSON("/desglosa-web/json_factoryById.action",
				{ id: idFactory	},
				function (data, status) {
					if (status == "success") {
						$("#factoryInformation").text("");
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow);
							$("#factoryInformation").append(formatFactoryInformation(item));
						});
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function configureFactoriesByCompanyId(idCompany, fillFactorySelect) {
		showLoadingIndicator(true);
		$.getJSON("/desglosa-web/json_factoriesByCompanyId.action",
				{ id: idCompany },
				function (data, status) {
					if (status == "success") {
						var infoSelector = "#factoryInformation";
						var selector = "#factorySelect";
						$(infoSelector).text("");
						$(infoSelector).append("<s:text name='label.generic_info_about_factories'/><br />");
						// The user has selected all factories of a given company (this company must have already been selected)
						$(infoSelector).append("<ul><li><a href='javascript:openDialog(\"desglosa_showProjectsByCompanyId\",\"project\"," + $("#companySelect").val() + ", true, true, false, true, true)'><s:text name='label.show_more_about_projects'/></a></li></ul>");
						if (fillFactorySelect) {
							$(selector).attr("disabled", "");
							$(selector).empty();
							$(selector).append($("<option></option>").attr("value", 0).text("Todas"));
							$(selector + " option:first").attr('selected','selected');
							$(selector).trigger('change');
						}
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow);
							if (fillFactorySelect) {
								$(selector).append($("<option></option>").attr("value", item.id).text(item.name));
							}
						});
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function configureCompanyById(idCompany) {
		showLoadingIndicator(true);
		$.getJSON("/desglosa-web/json_companyById.action",
				{ id: idCompany	},
				function (data, status) {
					if (status == "success") {
						var infoSelector = "#companyInformation";
						$(infoSelector).text("");
						if (idCompany == 0) {
							$(infoSelector).append("<s:text name='label.generic_info_about_companies'/><br />");
							$(infoSelector).append("<ul>");
							$(infoSelector + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesByCompanyId\",\"factory\"," + idCompany + ", true, false, true, true, true)'><s:text name='label.show_more_about_factories'/></a></li>");
							$(infoSelector + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsByCompanyId\",\"project\"," + idCompany + ", true, true, false, true, true)'><s:text name='label.show_more_about_projects'/></a></li>");
							$(infoSelector).append("</ul>");
						} else {
							// This bucle only iterates once
							$.each(data.companies, function (i, item) {
								$(infoSelector).append(formatCompanyInformation(item));
							});
						}
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function configureProjectsByCompanyId(idCompany){
		configureProjectSelect("/desglosa-web/json_projectsByCompanyId.action", idCompany, "#companyProjectSelect");
	}
	
	function configureProjectsByFactoryId(idFactory){
		// First, the user must choose a company, then a factory
		// if the user select all factories, it means all factories of a given company
		// so we show "company projects" as "all factory projects"
		if (idFactory == 0) {
			configureProjectSelect("/desglosa-web/json_projectsByCompanyId.action", $("#companySelect").val(), "#factoryProjectSelect");
		} else {
			configureProjectSelect("/desglosa-web/json_projectsByFactoryId.action", idFactory, "#factoryProjectSelect");
		}
	}
	
	function configureProjectSelect(action, id, selectElement) {
		showLoadingIndicator(true);
		$.getJSON(action,
				{ id: id },
				function (data, status) {
					if (status == "success") {
						$(selectElement).html("");
						$.each(data.projects, function (i, project) {
							var foo = "";
							if (project.subprojects.length > 1) foo = " *";
							$("<option value='"+project.id+"'>"+project.name+foo+"</option>").appendTo(selectElement);
						});
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function configureProjectById(idProject) {
		showLoadingIndicator(true);
		$.getJSON("/desglosa-web/json_projectById.action",
				{ id: idProject	},
				function (data, status) {
					if (status == "success") {
						if (idProject == 0) {
							// All projects
							$.each(data.projects, function (i, item) {
								$("#projectInformation").html("");
								$("#projectInformation").append("<br /><a href='#' onclick='javascript:desglosa_showProjectsById(0)'><s:text name='label.show_more'/></a>");
								$.each(item.subprojects, function (j, subproject) {
									placeMarker(subproject.factory.location.latitude, subproject.factory.location.longitude);
								});
							});
						} else {
							// A fixed project
							$.each(data.projects, function (i, item) {
								$("#projectInformation").append(formatProjectInformation(item));
								$.each(item.subprojects, function (j, subproject) {
									placeMarker(subproject.factory.location.latitude, subproject.factory.location.longitude);
								});
							});
						}
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	var visualizationCallback = null;
	var visualizationGroupBy = null;
	var visualizationEntityId = null;
	var noProfilesConfigured = false;
	
	function chooseProfile() {
		visualizationGroupBy = $("input:radio[name=showGroupBy]:checked").val();
		var filename = $("#profileChooserDiv").children('ul').children('li.ui-selected').attr('id');
		if (visualizationGroupBy != null && filename != undefined) {
			showLoadingIndicator(true);
			// http://viralpatel.net/blogs/2009/01/calling-javascript-function-from-string.html
			var funcCall = visualizationCallback + "(" + visualizationEntityId + ",\"" + visualizationGroupBy + "\",\"" + filename + "\")";
			$("#profileChooser").dialog('close');
			showLoadingIndicator(false);
			eval(funcCall);
		} else {
			if (noProfilesConfigured) {
				$("#profileChooser").dialog('close');
			} else {
				// TODO show warning. The user must select a group by option and a profile
			}
		}
	};
	
	function openDialog(callback, entity, id, groupByCompany, groupByFactory, groupByProject, groupByMarket, noGroupBy) {
		visualizationCallback = callback;
		visualizationGroupBy = null;
		visualizationEntityId = id;
		showLoadingIndicator(true);
		// read entity profiles
		$.getJSON('/desglosa-web/json_p_get',
				{
					entity: entity
				},
				function (data, status) {
					if (status == "success") {
						var mapSize = Object.keys(data.profileNames).length;
						if (mapSize > 0) {
							noProfilesConfigured = false;
							$("#profileChooserDiv").html("");
							$("#profileChooserDiv").append("<ul>");
							if (groupByCompany)
								$("#profileChooserDiv ul").append("<li class='option_group'><input type='radio' name='showGroupBy' value='company'/>label.company</li>");
							if (groupByFactory)
								$("#profileChooserDiv ul").append("<li class='option_group'><input type='radio' name='showGroupBy' value='factory'/>label.factory </li>");
							if (groupByProject)
								$("#profileChooserDiv ul").append("<li class='option_group'><input type='radio' name='showGroupBy' value='project'/>label.project</li>");
							if (groupByMarket)
								$("#profileChooserDiv ul").append("<li class='option_group'><input type='radio' name='showGroupBy' value='market'/>label.market</li>");
							if (noGroupBy)
								$("#profileChooserDiv ul").append("<li class='option_group'><input type='radio' name='showGroupBy' value=''/>label.no_group_by</li>");
							$("#profileChooserDiv").append("</ul>");
							$("#profileChooserDiv").append("<ul>");
							$.each(data.profileNames, function(filename, description) {
								var parts = filename.split('-');
								$("#profileChooserDiv ul:last").append("<li id='" + filename + "' class='selectablelist ui-corner-all' title='" + description + "'>" + parts[1] + "</li>");
							});
							$("#profileChooserDiv").append("</ul>");
						} else {
							// TODO empty array
							noProfilesConfigured = true;
							$("#profileChooserDiv").html("empty array");
						}
						$("#profileChooser").dialog('open');
					} else {
						$("#profileChooserDiv").html("");
						alert('An error has occurred while trying to retrieve visualization profiles: ' + status);
					}
					showLoadingIndicator(false);
		});
	}
	
	function formatCompanyInformation(companyJSON) {
		var result = "";
		result += "<b>" + companyJSON.name + "</b><br />";
		result += "<i>" + companyJSON.information + "</i><br />";
// 		result += "<br />";
// 		result += formatDirectorInformation(companyJSON.director);
		return result;
	}
	
	function formatFactoryInformation(factoryJSON) {
		var result = "<b>" + factoryJSON.name + "</b> (" + factoryJSON.company.name + ")<br />";
		result += "<i>" + factoryJSON.information + "</i><br />";
		result += "Número de empleados: " + factoryJSON.employees + "<br />"
// 		result += "<br />";
// 		result += formatDirectorInformation(factoryJSON.director);
		return result;
	}
	
	function formatDirectorInformation(directorJSON) {
		var result = directorJSON.lastName + ", " + directorJSON.name + "<br />";
		if (directorJSON.imagePath != null)
			result += "<img src='" + directorJSON.imagePath + "' width='64' height='64'/><br />";
		return result;
	}
	
	function formatProjectInformation(projectJSON) {
		var result = "";
		result += "<br />";
		return result;
	}
	
	function formatSubprojectInformation(subprojectJSON) {
		var result = "";
		result += subprojectJSON.name;
		return result;
	}
	
	var operationPool = new Array();
	function showLoadingIndicator(value) {
		if (value) {
			if (operationPool.length == 0) $("#indicator").css("display","");
			operationPool.push(1);
		} else {
			operationPool.pop();
			if (operationPool.length == 0) $("#indicator").css("display","none");	
		}
	}
	
	function initializeCompanyTab() {
		$("#companyInformation").html("<i><s:text name='message.select_company'/></i>");
	}
	
	function initializeFactoryTab() {
		$("#factoryInformation").html("<i><s:text name='message.select_factory'/></i>");
	}
	
	function initializeProjectTab() {
		$("#projectInformation").html("<i><s:text name='message.select_project'/></i>");
	}
	
	function initializeTabs(){
		initializeCompanyTab();
		initializeFactoryTab();
		initializeProjectTab();
	}

	$(document).ready(function() {
		// Initialize Google Maps canvas
		initializeMap();
		
		// Initialize companySelect select
		$("#companySelect option:first").attr('selected','selected');
		$("#companySelect").change( function() {
			// When a new company is selected
			clearAllMarkers(); // clear all markers in the map
			$("#factoryProjectSelect").val(-1); // unselect any selected factory
			// concurrent functions
			configureFactoriesByCompanyId($("#companySelect").val(), true), // Place map locations and fill factorySelect in
			configureProjectsByCompanyId($("#companySelect").val()), // fill projectSelect in
			// show info in company info div
			configureCompanyById($("#companySelect").val());
			// Indicator will be hidden inside configureCompanyById() when the action finishes.
		});
		
		$("#factorySelect").change( function() {
			clearAllMarkers();
			$("#factoryProjectSelect").attr('disabled','');
			$("#factoryProjectSelect").val(-1);
			if ($("#factorySelect").val() == 0) {
				// concurrent functions
				// If "all factories" is selected, place company factories location
				configureFactoriesByCompanyId($("#companySelect").val(), false), // Place map locations and fill factorySelect in
				configureProjectsByFactoryId($("#factorySelect").val());
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			} else {
				// concurrent functions
				// show info in factory info div
				configureFactoryById($("#factorySelect").val()),
				configureProjectsByFactoryId($("#factorySelect").val());
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			}
		});
		
		$("#companyProjectSelect").change( function() {
			clearAllMarkers();
			$("#factoryProjectSelect").val(-1);
			if ($("#companyProjectSelect").val() < 0) {
				// reset project info div
				initializeProjectTab();
			} else {
				// show info in project info div
				$("#projectInformation").text("");
				configureProjectById($("#companyProjectSelect").val());
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			}
		});
		
		$("#factoryProjectSelect").html("");
		$("#factoryProjectSelect").attr('disabled','disabled');
		$("#factoryProjectSelect").change( function() {
			clearAllMarkers();
			$("#companyProjectSelect").val(-1);
			if ($("#factoryProjectSelect").val() < 0) {
				// reset project info div
				initializeProjectTab();
			} else {
				// show info in project info div
				$("#projectInformation").text("");
				configureProjectById($("#factoryProjectSelect").val());
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			}
		});
		
		initializeTabs();
	});

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
					alert ("Load " + currentEntity + " (" + currentEntityId + ") information.");
					break;
				case 2:		// Double click
					alert ("Navigate to " + getNextLevel() + " from " + currentEntity + " (" + currentEntityId + ") information.");
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

function desglosa_showProjectById(id, groupBy, profileFilename) {
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

function desglosa_showSubprojectById(id, groupBy, profileFilename) {
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
					alert(city);
					desglosa_handleVisualization(data.city.model, city);
				} else {
					$('#jogl_canvas').css('display','none');
					$('#map_canvas').css('display','');
					alert('An error has occurred while trying to retrieve factory information: ' + status);
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

	</script>
</head>
<body>
	<!-- This div will be hidden by default and will appear in the middle of the screen
	in order to allow the user to choose a profile
	-->	
	<sj:dialog id="profileChooser" 
		       buttons="{'OK':function() { chooseProfile(); }}"
        	   autoOpen="false"
        	   modal="true"
        	   closeOnEscape="true"
        	   showEffect="fold"
        	   hideEffect="scale"
        	   draggable="false"
        	   title="Elige un perfil"
        	   onOpenTopics="loadProfiles">
    	<sj:div id='profileChooserDiv' selectable='true' selectableFilter='li'></sj:div>
    </sj:dialog>
    
	<div id="messagesAndErrors">
		<s:actionerror />
	</div>
	
	<div id="filter">
		<fieldset>
			<legend><s:text name="label.filter.corporative"/>:</legend>
			
			<div id="companyFilter" style="float:left;">
				<s:label for="companySelect" value="%{getText('label.select.company')}:"/>
				<select id="companySelect" >
					<option value="-1" disabled="disabled">-- <fmt:message key="label.select.choose_company"/> --</option>
					<option value="0"><fmt:message key="label.all_female"/></option>
					<s:iterator var="company" value="companies">
						<option value="<s:property value='id'/>"><s:property value="name"/></option>
					</s:iterator>
				</select>
				<br />
				<s:label for="companyProjectSelect" value="%{getText('label.select.project')}:"/>
				<s:select id="companyProjectSelect" name="companyProjectSelect" listKey="id" list="projects" size="5"></s:select>
			</div>
			
			<div id="factoryFilter" style="float:left;">
				<s:label for="factorySelect" value="%{getText('label.select.factory')}:"/>
				<select id="factorySelect" disabled="disabled"></select>
				<br />
				<s:label for="factoryProjectSelect" value="%{getText('label.select.project')}:"/>
				<s:select id="factoryProjectSelect" name="factoryProjectSelect" listKey="id" list="projects" size="5"></s:select>
			</div>
			
			<div id="legend" style="clear:left; float:right;">
				<s:text name="label.mark.global_project"></s:text>
			</div>
		</fieldset>
	</div>
	
	<div style="clear:both;"/>
	
	<div id="workingArea" style="position:relative; height:450px">
		<div id="map_canvas" style="position:relative; top:0; left:0; width: 600px; height: 400px; display: ;"></div>
		
		<div id="jogl_canvas" style="position:relative; top:0; left:0; width: 600px; height: 400px; display: none;">
			<applet code="org.jdesktop.applet.util.JNLPAppletLauncher" 
				codebase="./" 
				id="DesglosaApplet"
				alt="Check your browser configuration to allow java applets." 
			    width=600
			    height=400
			    archive="http://jogamp.org/deployment/util/applet-launcher.jar,
			             http://jogamp.org/deployment/webstart/newt.all.jar,
			             http://jogamp.org/deployment/webstart/nativewindow.all.jar,
			             http://jogamp.org/deployment/webstart/jogl.all.jar,
			             http://jogamp.org/deployment/webstart/gluegen-rt.jar,
			             applet/desglosa.jar">
			   <param name="codebase_lookup" value="false"/>
			   <param name="subapplet.classname" value="presentation.AppletMain"/>
			   <param name="subapplet.displayname" value="Desglosa Applet"/>
				   <param name="noddraw.check" value="true"/>
				   <param name="progressbar" value="true"/>
				   <param name="jnlpNumExtensions" value="1"/>
				   <param name="jnlpExtension1"
				          value="http://jogamp.org/deployment/webstart/jogl-core.jnlp"/>
				   <param name="java_arguments" value="-Dsun.java2d.noddraw=true"/>
				   <param name="jnlp_href" value="applet/applet-desglosa.jnlp"/>
				   <img src="images/gtk-cancel.png" alt="<s:text name="label.error"/>" title="<s:text name="label.error"/>" width="32" height="32"/><br />
				   <s:text name="error.no_JRE"/>
			</applet>
			<a href="#" onclick="$('#jogl_canvas').css('display','none');$('#map_canvas').css('display','');depth_level=0;"><s:text name="label.back_to_map"/></a>
		</div>
		
		<div id="charts" style="position:relative"></div>
	
		<div id="tabs" style="position:absolute; top:0; right:0; width:350px">
			<s:label value="%{getText('label.detailed_info')}:"/>
			<sj:tabbedpanel id="infoTabs" animate="true">
				<sj:tab id="generalInfoTab" target="generalInformation" label="%{getText('label.general')}"/>
				<sj:tab id="companyInfoTab" target="companyInformation" label="%{getText('label.company')}"/>
				<sj:tab id="factoryInfoTab" target="factoryInformation" label="%{getText('label.factory')}"/>
				<sj:tab id="projectInfoTab" target="projectInformation" label="%{getText('label.project')}"/>
				<div id="generalInformation"></div>
				<div id="companyInformation"></div>
				<div id="factoryInformation"></div>
				<div id="projectInformation"></div>
			</sj:tabbedpanel>
			
			<div id="indicatorDiv">
				<img id="indicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none"/>
			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
	
</body>
</html>
