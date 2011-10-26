<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/jsp/dialogs.jsp' %>

<html lang="en">
<head>
	<sj:head jqueryui="true"/>
	
	<!-- These variables are defined in the head of the html document in order to localize JavaScript messages 
	by using <c:out value='${varName}'/> -->
	<fmt:message key="message.no_profiles" var="noProfiles"/>
	<fmt:message key="label.company" var="labelCompany"/>
	<fmt:message key="label.factory" var="labelFactory"/>
	<fmt:message key="label.project" var="labelProject"/>
	<fmt:message key="label.market" var="labelMarket"/>
	<fmt:message key="label.no_group_by" var="labelNoGroupBy"/>
	<fmt:message key="label.available_profiles" var="availableProfiles"/>
	<fmt:message key="label.group_by_option" var="groupByOption"/>
	<fmt:message key="error.profile_selection_error" var="profileSelectionError"/>
	<fmt:message key="error.json_string_webapp_applet_malformed" var="malformedJSONStrnig"/>
	<fmt:message key="error.general" var="generalError"/>
	<fmt:message key="label.global_info" var="globalInformation"/>
	<fmt:message key="label.show_global_info" var="showGlobalInformation"/>
	<fmt:message key="label.companies" var="companyInformation"/>
	<fmt:message key="label.factories" var="factoryInformation"/>
	<fmt:message key="label.projects" var="projectInformation"/>
	<fmt:message key="label.subprojects" var="subprojectInformation"/>
	
	<meta name="menu" content="Visualization"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<link href="<s:url value='/styles/buttons.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/visualization.css?version=1'/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
	<script type="text/javascript" src="js/desglosa-facade.js?version=1"></script>

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
		
		// show graphics engine
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
		var filename = $("#profileChooserDialogBody").children('ul').children('li.ui-selected').attr('id');
		if (visualizationGroupBy != null && filename != undefined) {
			showLoadingIndicator(true);
			// http://viralpatel.net/blogs/2009/01/calling-javascript-function-from-string.html
			var funcCall = visualizationCallback + "(" + visualizationEntityId + ",\"" + visualizationGroupBy + "\",\"" + filename + "\")";
			$("#profileChooserDialog").dialog('close');
			showLoadingIndicator(false);
			eval(funcCall);
		} else {
			if (noProfilesConfigured) {
				$("#profileChooserDialog").dialog('close');
			} else {
				// Error: The user must select a group by option and a profile
				$("#profileChooserDialogMessages").html("<p class='error'><c:out value='${profileSelectionError}'/></p>");
			}
		}
	};
	
	function openDialog(callback, entity, id, groupByCompany, groupByFactory, groupByProject, groupByMarket, noGroupBy) {
		visualizationCallback = callback;
		visualizationGroupBy = null;
		visualizationEntityId = id;
		showLoadingIndicator(true);
		$("#profileChooserDialogMessages").html("");
		$("#profileChooserDialogBody").html("");
		$("#profibleChooserDialogGroupBy").html("");
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
							// Available profiles for selected entity
							$("#profileChooserDialogBody").append("<p><c:out value='${availableProfiles}'/>:</p>");
							$("#profileChooserDialogBody").append("<ul>");
							$.each(data.profileNames, function(filename, description) {
								var parts = filename.split('-');
								$("#profileChooserDialogBody ul:last").append("<li id='" + filename + "' class='selectablelist ui-corner-all' title='" + description + "'>" + parts[1] + "</li>");
							});
							$("#profileChooserDialogBody").append("</ul>");
							// Group by option (default is noGroupBy)
							$("#profibleChooserDialogGroupBy").append("<p><c:out value='${groupByOption}'/>:</p>");
							$("#profibleChooserDialogGroupBy").append("<ul class='groupBy'>");
							if (groupByCompany)
								$("#profibleChooserDialogGroupBy ul.groupBy").append("<li><input type='radio' name='showGroupBy' value='company'/><c:out value='${labelCompany}'/></li>");
							if (groupByFactory)
								$("#profibleChooserDialogGroupBy ul.groupBy").append("<li><input type='radio' name='showGroupBy' value='factory'/><c:out value='${labelFactory}'/></li>");
							if (groupByProject)
								$("#profibleChooserDialogGroupBy ul.groupBy").append("<li><input type='radio' name='showGroupBy' value='project'/><c:out value='${labelProject}'/></li>");
							if (groupByMarket)
								$("#profibleChooserDialogGroupBy ul.groupBy").append("<li><input type='radio' name='showGroupBy' value='market'/><c:out value='${labelMarket}'/></li>");
							if (noGroupBy)
								$("#profibleChooserDialogGroupBy ul.groupBy").append("<li><input type='radio' name='showGroupBy' value='' checked/><c:out value='${labelNoGroupBy}'/></li>");
							$("#profibleChooserDialogGroupBy").append("</ul>");
							$("#profileChooserDialog").dialog('open');
						} else {
							// Error: No profiles for selected entity
							noProfilesConfigured = true;
							$("#profileChooserDialogMessages").html("<p class='error'><c:out value='${noProfiles}'/></p>");
							$('#profileChooserDialog').dialog('open');
						}
					} else {
						$('#errorDialogBody').html("<p class='error'><c:out value='${generalError}'/></p>");
						$('#errorDialog').dialog('open');
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
	
	function initializeGeneralTab() {
		$("#generalInformation").html("<p class='label'><c:out value='${showGlobalInformation}'/>:</p>");
		$("#generalInformation").append("<ul>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showCompaniesById\",\"company\", 0, false, true, true, true, true)'><c:out value='${companyInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesById\",\"factory\", 0, true, false, true, true, true)'><c:out value='${factoryInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsById\",\"project\", 0, true, true, false, true, true)'><c:out value='${projectInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsById\",\"subproject\", 0, true, true, true, false, true)'><c:out value='${subprojectInformation}'/></a></li>");
		$("#generalInformation").append("</ul>");
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
		initializeGeneralTab();
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
			if ($("#factorySelect").hasClass('disabled')) $("#factorySelect").removeClass('disabled');
			if ($("#factoryProjectSelect").hasClass('disabled')) {
				$("#factoryProjectSelect").attr('disabled','');
				$("#factoryProjectSelect").removeClass('disabled');
			}
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

	</script>
</head>
<body>    
	<!-- This div will be hidden by default and will appear in the middle of the screen
	in order to allow the user to choose a profile
	-->	
	<sj:dialog id="profileChooserDialog" 
		       buttons="{'OK':function() { chooseProfile(); }}"
        	   autoOpen="false"
        	   modal="true"
        	   closeOnEscape="true"
        	   showEffect="fold"
        	   hideEffect="scale"
        	   draggable="false"
        	   title="%{getText('label.dialog.title.chooseProfile')}"
        	   onOpenTopics="loadProfiles">
       	<div id="profileChooserDialogMessages"></div>
    	<sj:div id='profileChooserDialogBody' selectable='true' selectableFilter='li'></sj:div>
    	<div id='profibleChooserDialogGroupBy'></div>
    </sj:dialog>
    
	<div id="messagesAndErrors">
		<s:actionerror />
	</div>
	
	<div id="filter">
		<fieldset>
			<legend><s:text name="label.filter.corporative"/>:</legend>
			
			<div id="companyFilter" class="filter form">
				<span>
				<s:label for="companySelect" value="%{getText('label.select.company')}:"/>
				<select id="companySelect">
					<option value="-1" disabled="disabled">-- <fmt:message key="label.select.choose_company"/> --</option>
					<option value="0"><fmt:message key="label.all_female"/></option>
					<s:iterator var="company" value="companies">
						<option value="<s:property value='id'/>"><s:property value="name"/></option>
					</s:iterator>
				</select>
				</span>
				<span>
				<s:label for="companyProjectSelect" value="%{getText('label.select.project')}:"/>
				<s:select id="companyProjectSelect" name="companyProjectSelect" listKey="id" list="projects" size="5"></s:select>
				</span>
			</div>
			
			<div id="factoryFilter" class="filter form">
				<span>
				<s:label for="factorySelect" value="%{getText('label.select.factory')}:"/>
				<select id="factorySelect" disabled="disabled" class="disabled"></select>
				</span>
				<span>
				<s:label for="factoryProjectSelect" value="%{getText('label.select.project')}:"/>
				<s:select id="factoryProjectSelect" name="factoryProjectSelect" listKey="id" list="projects" size="5" cssClass="disabled"></s:select>
				</span>
			</div>
			
			<div id="legend">
				<p class="label"><s:text name="label.mark.global_project"/></p>
			</div>
		</fieldset>
	</div>
	
	<div class="clear"></div>
	
	<div id="workingArea">
		<div id="map_canvas" class="canvas" style="display: ;"></div>
		
		<!-- Using fixed JOGL, Gluegen, Applet Lanucher and JNLP versions from webapp directory.
		If you want to use current development versions, add the following prefix to the url paths:
		http://jogamp.org/deployment/jogamp-current/
		 -->
		<div id="jogl_canvas" class="canvas" style="display: none;">
			<applet code="org.jdesktop.applet.util.JNLPAppletLauncher" 
				codebase="./" 
				id="DesglosaApplet"
				alt="Check your browser configuration to allow java applets." 
			    width=600
			    height=400
			    archive="jar/applet-launcher.jar,
			             jar/jogl.all.jar,
			             jar/gluegen-rt.jar,
			             applet/desglosa.jar">
			   <param name="codebase_lookup" value="false"/>
			   <param name="subapplet.classname" value="presentation.AppletMain"/>
			   <param name="subapplet.displayname" value="Desglosa Applet"/>
				   <param name="noddraw.check" value="true"/>
				   <param name="progressbar" value="true"/>
				   <param name="jnlpNumExtensions" value="1"/>
				   <param name="jnlpExtension1"
				          value="applet/jogl-all-awt.jnlp"/>
				   <param name="java_arguments" value="-Dsun.java2d.noddraw=true"/>
				   <param name="jnlp_href" value="applet/applet-desglosa.jnlp"/>
				   <img src="images/gtk-cancel.png" alt="<s:text name="label.error"/>" title="<s:text name="label.error"/>" width="32" height="32"/><br />
				   <s:text name="error.no_JRE"/>
			</applet>
			
			<a href="javascript:void(0)" onclick="$('#jogl_canvas').css('display','none');$('#map_canvas').css('display','');"><s:text name="label.back_to_map"/></a>
			
			<sj:accordion id="joglCanvasControls" autoHeight="false" collapsible="true" active="false">
				<sj:accordionItem title="%{getText('label.navigation_controls')}" >
					<table class="default">
						<thead>
							<tr class="header">
								<th><s:text name="label.keyboard"/></th>
								<th class="header"><s:text name="label.mouse"/></th>
								<th class="header"><s:text name="desglosa.action"/></th>
							</tr>
						</thead>
						<tbody>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.w"/></td>
								<td><s:text name="desglosa.hold_left"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_up"/></td>
								<td><s:text name="desglosa.move_forward"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.e"/></td>
								<td><s:text name="desglosa.hold_left"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_right"/></td>
								<td><s:text name="desglosa.move_right"/></td>
							</tr>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.s"/></td>
								<td><s:text name="desglosa.hold_left"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_down"/></td>
								<td><s:text name="desglosa.move_backward"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.q"/></td>
								<td><s:text name="desglosa.hold_left"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_left"/></td>
								<td><s:text name="desglosa.move_left"/></td>
							</tr>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.pageUp"/></td>
								<td><s:text name="desglosa.hold_right"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_up"/></td>
								<td><s:text name="desglosa.rotate_up"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.d"/></td>
								<td><s:text name="desglosa.hold_right"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_right"/></td>
								<td><s:text name="desglosa.rotate_right"/></td>
							</tr>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.pageDown"/></td>
								<td><s:text name="desglosa.hold_right"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_down"/></td>
								<td><s:text name="desglosa.rotate_down"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.a"/></td>
								<td><s:text name="desglosa.hold_right"/> <s:text name="desglosa.plus"/> <s:text name="desglosa.drag_left"/></td>
								<td><s:text name="desglosa.rotate_left"/></td>
							</tr>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.c"/></td>
								<td></td>
								<td><s:text name="desglosa.descend"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.space"/></td>
								<td></td>
								<td><s:text name="desglosa.raise"/></td>
							</tr>
							<tr class="odd">
								<td class="text-centering"><s:text name="desglosa.keyboard.f11"/></td>
								<td></td>
								<td><s:text name="desglosa.switch_shadows"/></td>
							</tr>
							<tr>
								<td class="text-centering"><s:text name="desglosa.keyboard.f12"/></td>
								<td></td>
								<td><s:text name="desglosa.debug"/></td>
							</tr>
							<tr class="odd">
								<td></td>
								<td><s:text name="desglosa.mouse.left.click"/></td>
								<td><s:text name="desglosa.pick"/></td>
							</tr>
							<tr>
								<td></td>
								<td><s:text name="desglosa.mouse.left.doubleClick"/></td>
								<td><s:text name="desglosa.goDeep"/></td>
							</tr>
						</tbody>
					</table>
				</sj:accordionItem>
			</sj:accordion>
		</div>
		
		<div id="charts"></div>
	
		<div id="tabs">
			<s:text name='label.detailed_info'/>:
			<sj:tabbedpanel id="infoTabs" animate="true">
				<sj:tab id="generalInfoTab" target="generalInformation" label="%{getText('label.global_info')}"/>
				<sj:tab id="companyInfoTab" target="companyInformation" label="%{getText('label.company')}"/>
				<sj:tab id="factoryInfoTab" target="factoryInformation" label="%{getText('label.factory')}"/>
				<sj:tab id="projectInfoTab" target="projectInformation" label="%{getText('label.project')}"/>
				<sj:tab id="subprojectInfoTab" target="subprojectInformation" label="%{getText('label.subproject')}"/>
				<div id="generalInformation" class="default"></div>
				<div id="companyInformation" class="default"></div>
				<div id="factoryInformation" class="default"></div>
				<div id="projectInformation" class="default"></div>
				<div id="subprojectInformation" class="default"></div>
			</sj:tabbedpanel>
			
			<div id="infoPanelDiv" class="ui-widget ui-widget-content ui-corner-all container">
				<div id="infoPanelDivHeader" class="ui-widget ui-corner-all ui-widget-header default container-header">header test</div>
				<div id="infoPanelDivContent" class="default container-content">content test</div>
			</div>
			
			<div id="indicatorDiv">
				<img id="indicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none"/>
			</div>
		</div>
		
		<div class="clear"></div>
	</div>	
	<div class="clear"></div>
</body>
</html>
