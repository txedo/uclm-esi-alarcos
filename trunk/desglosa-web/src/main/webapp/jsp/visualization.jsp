<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="Visualization"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
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
	
	function getFactoryLocation(idFactory) {
		showLoadingIndicator(true);
		if (!idFactory) var idFactory = "0";
		else var idFactory = idFactory+"";
		$.getJSON("/desglosa-web/json_factoryById.action",
				{ id: idFactory	},
				function (data, status) {
					if (status == "success") {
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
		if (!idCompany) var idCompany = "0";
		else var idCompany = idCompany+"";
		$.getJSON("/desglosa-web/json_factoriesByCompanyId.action",
				{ id: idCompany },
				function (data, status) {
					if (status == "success") {
						if (fillFactorySelect) {
							$("#factorySelect").attr("disabled", "");
							$("#factorySelect").empty();
							$('#factorySelect').append($("<option></option>").attr("value", 0).text("Todas"));
							$("#factorySelect option:first").attr('selected','selected');
							$("#factorySelect").trigger('change');
						}
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow);
							if (fillFactorySelect) {
								$('#factorySelect').append($("<option></option>").attr("value", item.id).text(item.name));
							}
						});
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function getCompany(idCompany) {
		showLoadingIndicator(true);
		if (!idCompany) var idCompany = "0";
		else var idCompany = idCompany+"";
		$.getJSON("/desglosa-web/json_companyById.action",
				{ id: idCompany	},
				function (data, status) {
					if (status == "success") {
						$("#companyInformation").text("");
						$.each(data.companies, function (i, item) {
							$("#companyInformation").append(formatCompanyInformation(item));
						});
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function configureProjectsByCompanyId(idCompany){
		showLoadingIndicator(true);
		var idCompany = idCompany+"";
		$.getJSON("/desglosa-web/json_projectsByCompanyId.action",
				{ id: idCompany },
				function (data, status) {
					if (status == "success") {
						$("#companyProjectSelect").html("");
						$.each(data.projects, function (i, project) {
							var foo = "";
							if (project.subprojects.length > 1) foo = " *";
							$("<option value='"+project.id+"'>"+project.name+foo+"</option>").appendTo("#companyProjectSelect");
						});
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function getProjectsByFactoryId(idFactory){
		showLoadingIndicator(true);
		var idFactory = idFactory+"";
		$.getJSON("/desglosa-web/json_projectsByFactoryId.action",
				{ id: idFactory },
				function (data, status) {
					if (status == "success") {
						$("#factoryProjectSelect").html("");
						$.each(data.projects, function (i, project) {
							var foo = "";
							if (project.subprojects.length > 1) foo = " *";
							$("<option value='"+project.id+"'>"+project.name+foo+"</option>").appendTo("#factoryProjectSelect");
						});
					}
					else alert('An error has occurred while trying to retrieve company information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function getProject(idProject) {
		showLoadingIndicator(true);
		if (!idProject) var idProject = "0";
		else var idProject = idProject+"";
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
	
	function formatCompanyInformation(companyJSON) {
		var result = "<b>" + companyJSON.name + "</b><br />";
		result += "<i>" + companyJSON.information + "</i><br />";
		result += "<br />";
		result += formatDirectorInformation(companyJSON.director);
		result += "<br /><a href='#' onclick='javascript:desglosa_showFactoriesByCompanyId(" + companyJSON.id + ")'><s:text name='label.show_more'/></a>";
		return result;
	}
	
	function formatFactoryInformation(factoryJSON) {
		var result = "<b>" + factoryJSON.name + "</b> (" + factoryJSON.company.name + ")<br />";
		result += "<i>" + factoryJSON.information + "</i><br />";
		result += "Número de empleados: " + factoryJSON.employees + "<br />"
		result += "<br />";
		result += formatDirectorInformation(factoryJSON.director);
		result += "<br /><a href='#' onclick='javascript:desglosa_showFactoriesById(" + factoryJSON.id + ")'><s:text name='label.show_more'/></a>";
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
		result += "<br /><a href='#' onclick='javascript:desglosa_showProjectsById(" + projectJSON.id + ")'><s:text name='label.show_more'/></a>";
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
			configureProjectsByCompanyId($("#companySelect").val()); // fill projectSelect in
			if ($("#companySelect").val() == 0) {
				// reset company info div
				initializeCompanyTab();
			}
			else {
				// show info in company info div
				getCompany($("#companySelect").val());
				// Indicator will be hidden inside getCompany() when the action finishes.
			}
		});
		
		$("#factorySelect").change( function() {
			clearAllMarkers();
			$("#factoryProjectSelect").attr('disabled','');
			$("#factoryProjectSelect").val(-1);
			if ($("#factorySelect").val() == 0) {
				// reset factory info div
				initializeFactoryTab();
				// If "all factories" is selected, place company factories location
				configureFactoriesByCompanyId($("#companySelect").val(), false); // Place map locations and fill factorySelect in
			}
			else {
				// show info in factory info div
				$("#factoryInformation").text("");
				getFactoryLocation($("#factorySelect").val());
				// Indicator will be hidden inside getFactoryLocation() when the action finishes.
			}
			getProjectsByFactoryId($("#factorySelect").val());
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
				getProject($("#companyProjectSelect").val());
				// Indicator will be hidden inside getFactoryLocation() when the action finishes.
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
				getProject($("#factoryProjectSelect").val());
				// Indicator will be hidden inside getFactoryLocation() when the action finishes.
			}
		});
		
		initializeTabs();
	});

///////////////////////////////////////////////////////
////////// BEGINING OF DESGLOSA-FACADE.JS /////////////
///////////////////////////////////////////////////////

var depth_level; // Global variable to control in which tower level the user is surfing around
var queriedProjects = new Array();
var queriedSubprojects = new Array();
var queriedFactories = new Array();

function selectTower(id, clickCount) {
	switch (depth_level) {
		case 1:
			showLoadingIndicator(true);
			if (typeof(queriedSubprojects) == 'undefined' || queriedSubprojects == null || queriedSubprojects.length == 0) {
				queriedSubprojects = new Array();
				$.getJSON("/desglosa-web/json_subprojectById.action",
						{id: id},
						function (data, status) {
							if (status == "success") {
								queriedSubprojects = data.subprojects;
								handleTowerEvent(clickCount);
							}
							else alert('An error has occurred while trying to retrieve factory information: ' + status);
							showLoadingIndicator(false);
				});
			} else {
				handleTowerEvent(clickCount);
				showLoadingIndicator(false);
			}
			break;
		default:
			break;
	}
}

function handleTowerEvent(clickCount) {
	if (typeof(queriedSubprojects) != 'undefined' && queriedSubprojects != null && queriedSubprojects.length > 0) {
		$.each(queriedSubprojects, function (i, subproject) {
			switch (clickCount) {
				case 1:
					// set div innerHTML with subproject info
					var fmtSp = formatSubprojectInformation(subproject);
					$("#projectInformation").append("<br />" + fmtSp);
					break;
				case 2:
					// draw subproject profile views
					
					// Increment depth level view
					depth_level++;
					break;
				default:
					break;
			}
		});
	}
}

function selectFactory(id, clickCount) {
	alert('Selected factory: ' + id + ' - clicks: ' + clickCount);
}

function selectProject(id, clickCount) {
	depth_level = 1;
	showLoadingIndicator(true);
	queriedSubprojects = new Array();
	// TODO aqui ya no vamos a volver a llamar a la acción.
	// Vamos a utilizar el array queriedProjects que tiene toda la información que necesitamos
	// one project -> one neighborhood
	var chart;
	var city = new City();
	var neighborhood;
	// Buscamos el proyecto en el array queriedProjects
	$.each(queriedProjects, function (i, project) {
		neighborhood = new Neighborhood();
		// one project -> one neighborhood
		// one subproject -> one flat in a neighborhood
		// In first versions, only one project chart is permitted. So, XML profile must have only one view
		chart = new Object();
		chart.name = project.profile.views[0].chart.name;
		chart.type = project.profile.views[0].chart.type;
		// set innerHTML for view.name
		if (project.profile.views[0].chart.maxCols < project.profile.views[0].dimensions.length) {
			alert ('Hay mas atributos que columnas, se ignoraran los sobrantes.');
		}
		if (chart.name == "towers") {
			$.each(project.subprojects, function(j, subproject) {
				var tower = new Object();
				tower = configureTower(project.profile.color, project.profile.views[j].dimensions);
				tower.id = subproject.id;
				neighborhood.flats.push(tower);
			});
		}
		city.neighborhoods.push(neighborhood);
	});
	// Convert project array to JSON format
	var JSONtext = JSON.stringify(city);
	// Una vez configurado el diagrama, lo mostramos
	if (chart.name == "towers") {
		// Change active view
		document.DesglosaApplet.visualizeTowers(JSONtext);
	}
	showLoadingIndicator(false);
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
			if (item.value <= item.measure.medium * item.measure.lowOffset) tower.color = color.nonAcceptable;
			else if (item.value < item.measure.medium * item.measure.lowOffset && item.value > item.measure.medium * item.measure.highOffset) tower.color = color.peripheral;
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
	desglosa_showFactories("/desglosa-web/json_factoriesByCompanyId.action", id);
}

function desglosa_showFactoriesById(id) {
	desglosa_showFactories("/desglosa-web/json_factoryById.action", id);
}

function desglosa_showFactories(action, id, groupBy) {
	queriedFactories = new Array();
	showLoadingIndicator(true);
	$.getJSON(action,
			{
				id: id,
				generateGLObjects: true,
				groupBy: groupBy
			},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Change active view
					var city = JSON.stringify(data.city);
					document.DesglosaApplet.visualizeFactories(city);
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
				showLoadingIndicator(false);
	});
}

function desglosa_showProjectsById(id, groupBy) {
	queriedProjects = new Array();
	queriedSubprojects = new Array();
	showLoadingIndicator(true);
	$.getJSON("/desglosa-web/json_projectById.action",
			{
				id: id,
				generateGLObjects: true,
				groupBy: groupBy
			},
			function (data, status) {
				if (status == "success") {
					// Hide map canvas
					$('#map_canvas').css('display','none');
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Change active view
					var city = JSON.stringify(data.city);
					document.DesglosaApplet.visualizeProjects(city);
				}
				else alert('An error has occurred while trying to retrieve factory information: ' + status);
				showLoadingIndicator(false);
	});
}


///////////////////////////////////////////////////////
//////////// END OF DESGLOSA-FACADE.JS ////////////////
///////////////////////////////////////////////////////

	</script>
</head>
<body>
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
	
	<div id="groupByDiv">
		<s:text name="label.group_by"/><br />
		<input type="radio" name="groupByRB" value="company"/><s:text name="label.company"/><br />
		<input type="radio" name="groupByRB" value="factory"/><s:text name="label.factory"/><br />
		<input type="radio" name="groupByRB" value="market"/><s:text name="label.market"/><br />
		<input type="radio" name="groupByRB" value="project"/><s:text name="label.project"/><br />
	</div>
	
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
				<sj:tab id="companyInfoTab" target="companyInformation" label="%{getText('label.company')}"/>
				<sj:tab id="factoryInfoTab" target="factoryInformation" label="%{getText('label.factory')}"/>
				<sj:tab id="projectInfoTab" target="projectInformation" label="%{getText('label.project')}"/>
				<div id="companyInformation"></div>
				<div id="factoryInformation"></div>
				<div id="projectInformation"></div>
			</sj:tabbedpanel>
			
			<div id="indicatorDiv">
				<img id="indicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none"/>
			</div>
		</div>
	</div>
	
</body>
</html>
