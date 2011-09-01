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
						$(infoSelector).append("<s:text name='label.show_more_about_projects'/>:<br />");
						$(infoSelector).append(createRadioButtonGroup("projectRBG", $("#companySelect").val(), "desglosa_showProjectsByCompanyId", true, true, false, true));
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
							$(infoSelector).append("<s:text name='label.show_more_about_factories'/>:<br />");
							createOptionGroup(infoSelector, 0, "desglosa_showFactoriesByCompanyId", true, false, true, true);
// 							$(infoSelector).append(createRadioButtonGroup("factoryRBG", 0, "desglosa_showFactoriesByCompanyId", true, false, true, true));
							$(infoSelector).append("<s:text name='label.show_more_about_projects'/>:<br />");
							$(infoSelector).append(createRadioButtonGroup("projectRBG", 0, "desglosa_showProjectsByCompanyId", true, true, false, true));
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
	
	function createRadioButtonGroup(name, id, callback, company, factory, project, market) {
		var result = "";
		if (company)
			result += "<input type='radio' name='" + name + "' onclick='javascript:" + callback + "(" + id + ", \"company\")'/><s:text name='label.company'/><br />";
		if (factory)
			result += "<input type='radio' name='" + name + "' onclick='javascript:" + callback + "(" + id + ", \"factory\")'/><s:text name='label.factory'/><br />";
		if (project)
			result += "<input type='radio' name='" + name + "' onclick='javascript:" + callback + "(" + id + ", \"project\")'/><s:text name='label.project'/><br />";
		if (market)
			result += "<input type='radio' name='" + name + "' onclick='javascript:" + callback + "(" + id + ", \"market\")'/><s:text name='label.market'/><br />";
		result += "<input type='radio' name='" + name + "' onclick='javascript:" + callback + "(" + id + ", \"\")'/><s:text name='label.no_group_by'/><br />";
		return result;
	}
	
	function openDialog(callback, entity, id) {
		showLoadingIndicator(true);
		// read entity profiles
		$.getJSON('/desglosa-web/json_p_get',
				{
					entity: entity
				},
				function (data, status) {
					if (status == "success") {
						$.each(data.profileNames, function(i, item) {
							$("#profileChooser").append(item);
						});
						$("#profileChooser").dialog('open');
					} else {
						alert('An error has occurred while trying to retrieve visualization profiles: ' + status);
					}
					showLoadingIndicator(false);
		});
	}
	
	function createOptionGroup(parentSelector, id, callback, company, factory, project, market) {
		$(parentSelector).append("<div id='option_group'>");
		$("#option_group:last").append("<ul>");
		if (company)
			$("#option_group:last > ul").append("<li><a href='javascript:openDialog(" + callback + ",\"company\"," + id + ")'><s:text name='label.company'/></a></li>");
		if (factory)
			$("#option_group:last > ul").append("<li><a href='javascript:openDialog(" + callback + ",\"factory\"," + id + ")'><s:text name='label.factory'/></a></li>");
		if (project)
			$("#option_group:last > ul").append("<li><a href='javascript:openDialog(" + callback + ",\"project\"," + id + ")'><s:text name='label.project'/></a></li>");
		if (market)
			$("#option_group:last > ul").append("<li><a href='javascript:openDialog(" + callback + ",\"market\"," + id + ")'><s:text name='label.market'/></a></li>");
		$("#option_group:last > ul").append("<li><a href='javascript:openDialog(" + callback + ",\"\"," + id + ")'><s:text name='label.no_group_by'/></a></li>");
		$("#option_group:last").append("</ul>");
		$(parentSelector).append("</div>");
	}
	
	function formatCompanyInformation(companyJSON) {
		var result = "";
		result += "<b>" + companyJSON.name + "</b><br />";
		result += "<i>" + companyJSON.information + "</i><br />";
		result += "<br />";
		result += formatDirectorInformation(companyJSON.director);
		result += "<s:text name='label.show_more_about_factories'/>:<br />";
		result += createRadioButtonGroup("companyRBG", companyJSON.id, "desglosa_showFactoriesByCompanyId", true, false, true, true);
		result += "<s:text name='label.show_more_about_projects'/>:<br />";
		result += createRadioButtonGroup("projectRBG", companyJSON.id, "desglosa_showProjectsByCompanyId", true, true, false, true);
		return result;
	}
	
	function formatFactoryInformation(factoryJSON) {
		var result = "<b>" + factoryJSON.name + "</b> (" + factoryJSON.company.name + ")<br />";
		result += "<i>" + factoryJSON.information + "</i><br />";
		result += "Número de empleados: " + factoryJSON.employees + "<br />"
		result += "<br />";
		result += formatDirectorInformation(factoryJSON.director);
		result += "<s:text name='label.show_more_about_projects'/>:<br />";
		result += createRadioButtonGroup("projectRBG", factoryJSON.id, "desglosa_showProjectsByFactoryId", true, true, false, true);
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
	
	function chooseProfile() {
		alert('OK Button pressed!');
	};

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

function selectTower(id, clickCount) {
	handleSelectionEvent(id, clickCount);
}

function selectBuilding(id, clickCount) {
	handleSelectionEvent(id, clickCount);
}

function selectAntennaBall(id, clickCount) {
	handleSelectionEvent(id, clickCount);
}

function selectionError(message) {
	alert(message);
}

function handleSelectionEvent(id, clickCount) {
	// This function will handle the selection event on any 3D model, so it will handle navigation too
}

function desglosa_showFactoriesByCompanyId(id, groupBy) {
	desglosa_showFactories("/desglosa-web/json_factoriesByCompanyId.action", id, groupBy);
}

function desglosa_showFactoriesById(id, groupBy) {
	desglosa_showFactories("/desglosa-web/json_factoryById.action", id, groupBy);
}

function desglosa_showFactories(action, id, groupBy) {
	queriedFactories = new Array();
	showLoadingIndicator(true);
	// Hide map canvas
	if (document.getElementById("map_canvas").style.display == '') $('#map_canvas').css('display','none');
	// Hide jogl canvas if it is shown
	if (document.getElementById("jogl_canvas").style.display == '') $('#jogl_canvas').css('display','none');
	$.getJSON(action,
			{
				id: id,
				generateGLObjects: true,
				groupBy: groupBy
			},
			function (data, status) {
				if (status == "success") {
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Change active view
					var city = JSON.stringify(data.city);
					alert(city);
					document.DesglosaApplet.visualizeBuildings(city);
				} else {
					$('#jogl_canvas').css('display','none');
					$('#map_canvas').css('display','');
					alert('An error has occurred while trying to retrieve factory information: ' + status);
				}
				showLoadingIndicator(false);
	});
}

function desglosa_showProjectsByCompanyId(id, groupBy) {
	desglosa_showProjects("/desglosa-web/json_projectsByCompanyId.action", id, groupBy);
}

function desglosa_showProjectsByFactoryId(id, groupBy) {
	desglosa_showProjects("/desglosa-web/json_projectsByFactoryId.action", id, groupBy);
}

function desglosa_showProjectById(id, groupBy) {
	desglosa_showProjects("/desglosa-web/json_projectById.action", id, groupBy);
}

function desglosa_showProjects (action, id, groupBy) {
	showLoadingIndicator(true);
	// Hide map canvas
	if (document.getElementById("map_canvas").style.display == '') $('#map_canvas').css('display','none');
	// Hide jogl canvas if it is shown
	if (document.getElementById("jogl_canvas").style.display == '') $('#jogl_canvas').css('display','none');
	$.getJSON(action,
			{
				id: id,
				generateGLObjects: true,
				groupBy: groupBy
			},
			function (data, status) {
				if (status == "success") {
					// Show jogl canvas
					$('#jogl_canvas').css('display','');
					// Change active view
					var city = JSON.stringify(data.city);
					document.DesglosaApplet.visualizeAntennaBalls(city);
				} else {
					$('#jogl_canvas').css('display','none');
					$('#map_canvas').css('display','');
					alert('An error has occurred while trying to retrieve factory information: ' + status);
				}
				showLoadingIndicator(false);
	});
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
        	    onOpenTopics="loadProfiles"/>
    
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
		<div style="clear:both;"></div>
	</div>
	
</body>
</html>
