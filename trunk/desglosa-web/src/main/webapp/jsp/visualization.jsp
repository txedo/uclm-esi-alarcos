<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
	<!-- http://docs.jquery.com/Using_jQuery_with_Other_Libraries -->
	<!-- This is done to be able to work with both versions of jQuery:
		* a custom one, via $j(...)
		* the one included in struts2-jquery-plugin, via $(...)
	 -->
	<script type="text/javascript" src="js/jquery-1.7.js?version=1"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js?version=1"></script>
	<script>
     var $j = jQuery.noConflict();
   	</script>
	<sj:head jqueryui="true"/>
	
	<!-- These variables are defined in the head of the html document in order to localize JavaScript messages 
	by using <c:out value='${varName}'/> -->
	<fmt:message key="message.no_profiles" var="noProfiles"/>
	<fmt:message key="label.Company" var="labelCompany"/>
	<fmt:message key="label.Factory" var="labelFactory"/>
	<fmt:message key="label.Project" var="labelProject"/>
	<fmt:message key="label.Market" var="labelMarket"/>
	<fmt:message key="label.no_group_by" var="labelNoGroupBy"/>
	<fmt:message key="label.available_profiles" var="availableProfiles"/>
	<fmt:message key="label.group_by_option" var="groupByOption"/>
	<fmt:message key="error.profile_selection_error" var="profileSelectionError"/>
	<fmt:message key="error.json_string_webapp_applet_malformed" var="malformedJSONString"/>
	<fmt:message key="error.outdated_profile" var="outdatedProfile"/>
	<fmt:message key="error.general" var="generalError"/>
	<fmt:message key="label.global_info" var="globalInformation"/>
	<fmt:message key="label.show_global_info" var="showGlobalInformation"/>
	<fmt:message key="label.Companies" var="companyInformation"/>
	<fmt:message key="label.Factories" var="factoryInformation"/>
	<fmt:message key="label.Projects" var="projectInformation"/>
	<fmt:message key="label.Subprojects" var="subprojectInformation"/>
	<fmt:message key="label.further_info_about_company" var="companyFurtherInformation"/>
	<fmt:message key="label.further_info_about_factory" var="factoryFurtherInformation"/>
	<fmt:message key="label.further_info_about_project" var="projectFurtherInformation"/>
	<fmt:message key="label.further_info_about_subproject" var="subprojectFurtherInformation"/>
	<fmt:message key="label.numberOfEmployees" var="numberOfEmployees"/>
	<fmt:message key="label.numberOfLeadingProjects" var="numberOfLeadingProjects"/>
	<fmt:message key="label.numberOfDevelopingSubprojects" var="numberOfDevelopingSubprojects"/>
	<fmt:message key="label.factory.email" var="factoryEmail"/>
	<fmt:message key="label.all_female" var="allFemale"/>
	<!-- Loading spinner messages -->
	<fmt:message key="loading.fetching_factory_information" var="fetchingFactoryInformation"/>
	<fmt:message key="loading.placing_location_marks" var="placingLocationMarks"/>
	<fmt:message key="loading.working" var="working"/>
	<fmt:message key="loading.fetching_company_projects" var="fetchingCompanyProjects"/>
	<fmt:message key="loading.fetching_factory_projects" var="fetchingFactoryProjects"/>
	<fmt:message key="loading.fetching_project_subprojects" var="fetchingProjectSubprojects"/>
	<fmt:message key="loading.fetching_visualization_profiles" var="fetchingVisualizationProfiles"/>
	<fmt:message key="loading.fetching_entity_information" var="fetchingEntityInformation"/>
	<fmt:message key="loading.handling_selection_event" var="handlingSelectionEvent"/>
	<fmt:message key="loading.generating_3d_graphics" var="generating3dGraphics"/>
	
	<meta name="menu" content="Visualization"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<link href="<s:url value='/styles/buttons.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/visualization.css?version=1'/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>

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
		var resultHTML = "<b>" + factoryJSON.name + "</b><br />";
		resultHTML += "<i>" + factoryJSON.information + "</i><br />";
		resultHTML += "<c:out value='${labelCompany}'/>" + ": " + factoryJSON.company.name + "<br />";
		resultHTML += "<c:out value='${factoryEmail}'/>" + ": " + factoryJSON.email + "<br /><br />";
		resultHTML += "<c:out value='${numberOfEmployees}'/>" + ": " + factoryJSON.employees + "<br />";
		resultHTML += "<c:out value='${numberOfLeadingProjects}'/>" + ": " + factoryJSON.numberOfLeadingProjects + "<br />";
		resultHTML += "<c:out value='${numberOfDevelopingSubprojects}'/>" + ": " + factoryJSON.numberOfDevelopingSubprojects + "<br />";

		var infoWindow = new google.maps.InfoWindow();
		infoWindow.setContent(resultHTML);
		
		infoWindows.push(infoWindow);
		return infoWindow;
	}
	
	function addMarkerEvents(marker, infoWindow, factoryId) {
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
			showLoadingIndicator(true, "<c:out value='${fetchingFactoryInformation}'/>");
			$("#infoPanelDiv").css('display', 'none');
			setTitleToInfoPanel("<c:out value='${factoryFurtherInformation}'/>");
            $("#infoPanelDivContent").load("/desglosa-web/getFactoryPlainReport?id=" + factoryId + " #plainReport", function() {
                $('#infoPanelDiv').css('display','');
                showLoadingIndicator(false);
            });
	    });
	}
	
	function configureFactoryById(idFactory) {
		showLoadingIndicator(true, "<c:out value='${placingLocationMarks}'/>");
		$.getJSON("/desglosa-web/json_factoryById.action",
				{ id: idFactory	},
				function (data, status) {
					showLoadingIndicator(false);
					if (status == "success") {
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow, item.id);
						});
					}
					else {
						alert('An error has occurred while trying to retrieve factory information: ' + status);
					}
					
		}),
        formatEntityInformation("factory", idFactory, "#factoryInformation");
	}
	
	function configureFactoriesByCompanyId(idCompany, fillFactorySelect) {
		showLoadingIndicator(true, "<c:out value='${placingLocationMarks}'/>");
		$.getJSON("/desglosa-web/json_factoriesByCompanyId.action",
				{ id: idCompany },
				function (data, status) {
					showLoadingIndicator(false);
					if (status == "success") {
						var selector = "#factorySelect";
					    if (fillFactorySelect) {
							$(selector).empty();
							$(selector).append($("<option></option>").attr("value", 0).text("<c:out value='${allFemale}'/>"));
							$(selector + " option:first").attr('selected','selected');
							$(selector).trigger('change');
						}
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow, item.id);
							if (fillFactorySelect) {
								$(selector).append($("<option></option>").attr("value", item.id).text(item.name));
							}
						});
					}
					else {
						alert('An error has occurred while trying to retrieve factory information: ' + status);
					}
					
		}),
		formatEntityInformation("factory", 0, "#factoryInformation");
	}
	
	function configureCompanyById(idCompany) {
		formatEntityInformation("company", idCompany, "#companyInformation");
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
		// Configure companyProjectSelect or factoryProjectSelect, whatever is selectElement
		var waitingMessage = "<c:out value='${working}'/>";
		if (selectElement == "#companyProjectSelect") {
			waitingMessage = "<c:out value='${fetchingCompanyProjects}'/>";
		} else if (selectElement == "#factoryProjectSelect"){
			waitingMessage = "<c:out value='${fetchingFactoryProjects}'/>";
		}
		showLoadingIndicator(true, waitingMessage);
		$.getJSON(action,
				{ id: id },
				function (data, status) {
					showLoadingIndicator(false);
					if (status == "success") {
						// Clear both selects
						$(selectElement).html("");
						$.each(data.projects, function (i, project) {
							var foo = "";
							if (project.subprojects.length > 1) foo = " *";
							$("<option value='"+project.id+"'>"+project.name+foo+"</option>").appendTo(selectElement);
						});
					}
					else {
						alert('An error has occurred while trying to retrieve project information: ' + status);
					}
		});
	}
	
	function configureSubprojectsByProjectId(idProject) {
	      // Configure companyProjectSelect or factoryProjectSelect, whatever is selectElement
        showLoadingIndicator(true, "<c:out value='${fetchingProjectSubprojects}'/>");
        $.getJSON("/desglosa-web/json_subprojectsByProjectId.action",
                { id: idProject },
                function (data, status) {
                	showLoadingIndicator(false);
                    if (status == "success") {
                        // Clear both selects
                        $("#projectSubprojectSelect").html("");
                        $.each(data.subprojects, function (i, subproject) {
                            $("<option value='"+subproject.id+"'>"+subproject.name+"</option>").appendTo("#projectSubprojectSelect");
                        });
                    }
                    else {
                    	alert('An error has occurred while trying to retrieve subproject information: ' + status);
                    }
        });
	}
	
	function configureProjectById(idProject) {
		showLoadingIndicator(true, "<c:out value='${placingLocationMarks}'/>");
		$.getJSON("/desglosa-web/json_projectById.action",
				{ id: idProject	},
				function (data, status) {
					showLoadingIndicator(false);
					if (status == "success") {
						// for idProject == 0, do nothing
						if (idProject > 0) {
							// A fixed project
							// this bucle will iterate only once
							$.each(data.projects, function (i, item) {
								// this bucle will iterate as many times as subprojects under the selected project
								$.each(item.subprojects, function (j, subproject) {
									var marker = placeMarker(subproject.factory.location.latitude, subproject.factory.location.longitude);
									marker.setTitle(subproject.factory.name);
                                    var infoWindow = createInfoWindow(subproject.factory);
                                    addMarkerEvents(marker, infoWindow, subproject.factory.id);
								});
							});
						}
					}
					else {
						alert('An error has occurred while trying to retrieve project information: ' + status);
					}
		}),
		formatEntityInformation("project", idProject, "#projectInformation");
	}
	
	function configureSubprojectById(idSubproject) {
        showLoadingIndicator(true, "<c:out value='${placingLocationMarks}'/>");
        $.getJSON("/desglosa-web/json_subprojectById.action",
                { id: idSubproject },
                function (data, status) {
                    showLoadingIndicator(false);
                    if (status == "success") {
                    	// for idSubproject == 0, do nothing
                        if (idSubproject > 0) {
                            // A fixed subproject
                            // this bucle will iterate only once
                            $.each(data.subprojects, function (i, item) {
                                var marker = placeMarker(item.factory.location.latitude, item.factory.location.longitude);
                                marker.setTitle(item.factory.name);
                                var infoWindow = createInfoWindow(item.factory);
                                addMarkerEvents(marker, infoWindow, item.factory.id);
                            });
                        }
                    }
                    else {
                    	alert('An error has occurred while trying to retrieve subproject information: ' + status);
                    }
        }),
        formatEntityInformation("subproject", idSubproject, "#subprojectInformation");
	}
	
	var visualizationCallback = null;
	var visualizationGroupBy = null;
	var visualizationEntityId = null;
	var noProfilesConfigured = false;
	
	function chooseProfile() {
		visualizationGroupBy = $("input:radio[name=showGroupBy]:checked").val();
		var filename = $("#profileChooserDialogBody").children('ul').children('li.ui-selected').attr('id');
		if (visualizationGroupBy != null && filename != undefined) {
			// http://viralpatel.net/blogs/2009/01/calling-javascript-function-from-string.html
			var funcCall = visualizationCallback + "(" + visualizationEntityId + ",\"" + visualizationGroupBy + "\",\"" + filename + "\")";
			$("#profileChooserDialog").dialog('close');
			currentEntityBackup = currentEntity;
			eval(funcCall);
		} else {
			if (noProfilesConfigured) {
				$("#profileChooserDialog").dialog('close');
			} else {
				// Error: The user must select a group by option and a profile
				$("#profileChooserDialogMessages").html("<p class='messageBox error'><c:out value='${profileSelectionError}'/></p>");
			}
		}
	};
	
	function openDialog(callback, entity, id, groupByCompany, groupByFactory, groupByProject, groupByMarket, noGroupBy) {
		visualizationCallback = callback;
		visualizationGroupBy = null;
		visualizationEntityId = id;
		showLoadingIndicator(true, "<c:out value='${fetchingVisualizationProfiles}'/>");
		$("#profileChooserDialogMessages").html("");
		$("#profileChooserDialogBody").html("");
		$("#profibleChooserDialogGroupBy").html("");
		// read entity profiles
		$.getJSON('/desglosa-web/json_p_get',
				{
					entity: entity
				},
				function (data, status) {
					showLoadingIndicator(false);
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
							$("#profileChooserDialogMessages").html("<p class='messageBox error'><c:out value='${noProfiles}'/></p>");
							$('#profileChooserDialog').dialog('open');
						}
					} else {
						$('#errorDialogBody').html("<p class='messageBox error'><c:out value='${generalError}'/></p>");
						$('#errorDialog').dialog('open');
					}
		});
	}
	
	function getPlainReportGenerationAction(entity) {
		var action = null;
		if (entity == "company") {
			action = '/desglosa-web/getCompanyPlainReport';
		} else if (entity == "factory") {
			action = '/desglosa-web/getFactoryPlainReport';
	    } else if (entity == "project") {
	    	action = '/desglosa-web/getProjectPlainReport';
	    } else if (entity == "subproject") {
	    	action = '/desglosa-web/getSubprojectPlainReport';
	    }
		return action;
	}
	
	function formatEntityInformation(entity, entityId, selector) {
		if (entityId > 0) {
			// show loading indicator
		    showLoadingIndicator(true, "<c:out value='${fetchingEntityInformation}'/>");
			// fill the content of div content 1
            $(selector +  "> div.content1").html("");
            if (entity == "company") {
            	$(selector +  "> div.content1").append("<s:text name='label.company_selected'/><br />");
                $(selector +  "> div.content1").append("<ul>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showCompaniesById\",\"company\"," + entityId + ", false, false, false, false, true)'><s:text name='label.visualization_over_company'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesByCompanyId\",\"factory\"," + entityId + ", true, false, true, true, true)'><s:text name='label.visualization_over_company_factories'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsByCompanyId\",\"project\"," + entityId + ", true, true, false, true, true)'><s:text name='label.visualization_over_company_projects'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsByCompanyId\",\"subproject\"," + entityId + ", true, true, true, true, true)'><s:text name='label.visualization_over_company_subprojects'/></a></li>");
                $(selector +  "> div.content1").append("</ul>");
            } else if (entity == "factory") {
                $(selector +  "> div.content1").append("<s:text name='label.factory_selected'/><br />");
                $(selector +  "> div.content1").append("<ul>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesById\",\"factory\"," + entityId + ", true, false, true, true, true)'><s:text name='label.visualization_over_factory'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsByFactoryId\",\"project\"," + entityId + ", true, true, false, true, true)'><s:text name='label.visualization_over_factory_projects'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsByFactoryId\",\"subproject\"," + entityId + ", true, true, true, true, true)'><s:text name='label.visualization_over_factory_subprojects'/></a></li>");
                $(selector +  "> div.content1").append("</ul>");
            } else if (entity == "project") {
                $(selector +  "> div.content1").append("<s:text name='label.project_selected'/><br />");
                $(selector +  "> div.content1").append("<ul>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesByProjectId\",\"factory\"," + entityId + ", true, false, true, true, true)'><s:text name='label.visualization_factories_by_project'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsById\",\"project\"," + entityId + ", true, true, false, true, true)'><s:text name='label.visualization_over_project'/></a></li>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsByProjectId\",\"subproject\"," + entityId + ", true, true, true, true, true)'><s:text name='label.visualization_over_project_subprojects'/></a></li>");
                $(selector +  "> div.content1").append("</ul>");
            } else if (entity == "subproject") {
                $(selector +  "> div.content1").append("<s:text name='label.subproject_selected'/><br />");
                $(selector +  "> div.content1").append("<ul>");
                $(selector +  "> div.content1" + " ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsById\",\"subproject\"," + entityId + ", true, true, true, true, true)'><s:text name='label.visualization_over_subproject'/></a></li>");
                $(selector +  "> div.content1").append("</ul>");
            }
            // get plain report to fill div content 2
	        var action = getPlainReportGenerationAction(entity);
	        if (action != null) {
	            $(selector + " > div.content2").html("");
	            $(selector + " > div.content2").load(action + "?id=" + entityId + " #plainReport", function() {
	                showLoadingIndicator(false);
	            });
	        } else {
	            $('#errorDialogBody').html("<p class='messageBox error'><c:out value='${generalError}'/></p>");
	            $('#errorDialog').dialog('open');
	            showLoadingIndicator(false);
	        }
		} else if (entityId <= 0) {
			// Initialize info tabs in content 1 and clean content 2
			$(selector +  "> div.content1").html("");
            if (entity == "company") {
            	initializeCompanyTab();
            } else if (entity == "factory") {
            	initializeFactoryTab();
            } else if (entity == "project") {
            	initializeProjectTab();
            } else if (entity == "subproject") {
            	initializeSubprojectTab();
            } 
            $(selector +  "> div.content2").html("");
		}
	}
	
	var operationPool = new Array();
	function showLoadingIndicator(value, title) {
		var auxTitle = "";
		if (title != undefined) {
			auxTitle = title;
		}
		if (value) {
			// add the element to the beginning of the array
			if (operationPool.unshift(auxTitle) == 1) {
				// if it is the first element, display the spinner
				$("#indicator").css("display","");
				// and the text just added
				$("#indicatorText").text(auxTitle);
			}
		} else {
			// Remove last element of the array
			operationPool.pop();
			if (operationPool.length == 0) {
				// if the array is empty, hide the spinner
				$("#indicator").css("display","none");	
				$("#indicatorText").text("");
			} else {
				// update the feedback text
				$("#indicatorText").text(operationPool[operationPool.length-1]);
			}
		}
	}
	
	function initializeGeneralTab() {
		$("#generalInformation").html("<p class='label'><c:out value='${showGlobalInformation}'/>:</p>");
		$("#generalInformation").append("<ul>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showCompaniesById\",\"company\", 0, false, true, true, true, true)'><c:out value='${companyInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showFactoriesById\",\"factory\", 0, true, false, true, true, true)'><c:out value='${factoryInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showProjectsById\",\"project\", 0, true, true, false, true, true)'><c:out value='${projectInformation}'/></a></li>");
		$("#generalInformation ul").append("<li><a href='javascript:openDialog(\"desglosa_showSubprojectsById\",\"subproject\", 0, true, true, true, true, true)'><c:out value='${subprojectInformation}'/></a></li>");
		$("#generalInformation").append("</ul>");
	}
	
	function initializeCompanyTab() {
		$("#companyInformation div.content1").html("<i><s:text name='message.select_company'/></i>");
		$("#companyInformation div.content2").html("");
	}
	
	function initializeFactoryTab() {
		$("#factoryInformation div.content1").html("<i><s:text name='message.select_factory'/></i>");
		$("#factoryInformation div.content2").html("");
	}
	
	function initializeProjectTab() {
		$("#projectInformation div.content1").html("<i><s:text name='message.select_project'/></i>");
		$("#projectInformation div.content2").html("");
	}
	
	function initializeSubprojectTab() {
        $("#subprojectInformation div.content1").html("<i><s:text name='message.select_subproject'/></i>");
        $("#subprojectInformation div.content2").html("");
    }
	
	function initializeTabs(){
		initializeGeneralTab();
		initializeCompanyTab();
		initializeFactoryTab();
		initializeProjectTab();
		initializeSubprojectTab();
	}

	$(document).ready(function() {    	
		// Initialize Google Maps canvas
		initializeMap();
		
		// Initialize companySelect select
		$("#companySelect").change( function() {
			// When a new company is selected
			clearAllMarkers(), // clear all markers in the map
			$("#companyProjectSelect").val(-1),
			$("#factoryProjectSelect").val(-1), // unselect any selected project from factory filter
			$("#projectSubprojectSelect").val(-1), // unselect any selected subproject from project filter
			// concurrent functions
			configureFactoriesByCompanyId($("#companySelect").val(), true), // Place map locations and fill factorySelect in
			configureProjectsByCompanyId($("#companySelect").val()), // fill companyProjectSelect in
			// show info in company info div
			configureCompanyById($("#companySelect").val());
			// Indicator will be hidden inside configureCompanyById() when the action finishes.
		});
		
		$("#factorySelect").change( function() {
			clearAllMarkers(),
			$("#companyProjectSelect").val(-1),
			$("#factoryProjectSelect").val(-1),
			$("#projectSubprojectSelect").val(-1);
			if ($("#factorySelect").val() < 0 || $("#factorySelect").val() == null) {
				// reset factory info div
                initializeFactoryTab();
			} else if ($("#factorySelect").val() == 0) {
				// concurrent functions
				// If "all factories" is selected, place company factories location
				configureFactoriesByCompanyId($("#companySelect").val(), false), // Place map locations and fill factorySelect in
				configureProjectsByFactoryId($("#factorySelect").val()); // fill factoryProjectSelect in
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			} else {
				// concurrent functions
				// show info in factory info div
				configureFactoryById($("#factorySelect").val()), // place map locations
				configureProjectsByFactoryId($("#factorySelect").val()); // fill factoryProjectSelect in
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			}
		});		
		
		$("#projectSelect").change( function() {
            clearAllMarkers();
            $("#companyProjectSelect").val(-1),
            $("#factoryProjectSelect").val(-1),
            $("#projectSubprojectSelect").val(-1),
            $("#projectSubprojectSelect").trigger('change');
            if ($("#projectSelect").val() < 0) {
            	// reset project info div
                initializeProjectTab();
            } else {
                // concurrent functions
                // If "all projects" is selected, show all subprojects in projectSubprojectSelect
                // else show project subprojects in projectSubprojectSelect
                configureSubprojectsByProjectId($("#projectSelect").val()),
                // show info in project info div
                // place factory locations in which subprojects are being developed
                // show info in project info div
                configureProjectById($("#projectSelect").val());
                // Indicator will be hidden inside configureSubprojectsByProjectId() when the action finishes.
            }
        });
		
		$("#companyProjectSelect").change( function() {
			clearAllMarkers(),
			$("#factoryProjectSelect").val(-1),
			$("#projectSubprojectSelect").val(-1);
			if ($("#companyProjectSelect").val() < 0 || $("#companyProjectSelect").val() == null) {
				// reset project info div
				initializeProjectTab();
			} else {
				// show info in project info div
				configureProjectById($("#companyProjectSelect").val());
				// Indicator will be hidden inside configureFactoryById() when the action finishes.
			}
		});
		
		$("#factoryProjectSelect").change( function() {
			clearAllMarkers(),
			$("#companyProjectSelect").val(-1),
			$("#projectSubprojectSelect").val(-1);
			if ($("#factoryProjectSelect").val() < 0 || $("#factoryProjectSelect").val() == null) {
				// reset project info div
				initializeProjectTab();
			} else {
				// show info in project info div
				configureProjectById($("#factoryProjectSelect").val());
				// Indicator will be hidden inside configureProjectById() when the action finishes.
			}
		});
		
		$("#projectSubprojectSelect").change( function() {
            clearAllMarkers(),
            $("#companyProjectSelect").val(-1),
            $("#factoryProjectSelect").val(-1);
            if ($("#projectSubprojectSelect").val() < 0 || $("#projectSubprojectSelect").val() == null) {
                // reset project info div
                initializeSubprojectTab();
            } else {
                // show info in project info div
                configureSubprojectById($("#projectSubprojectSelect").val());
                // Indicator will be hidden inside configureSubprojectById() when the action finishes.
            }
        });
		
		initializeTabs();
		
		// Initialize control accordion
		$j("#joglCanvasControls").accordion();
		$j("#joglCanvasControls").accordion({
			collapsible: true,
			active: true,
			autoHeight: false,
			navigation: true,
			changestart: function(event, ui) {
				ui.newContent.css('height', 'auto');
			}
		});
		$j("#joglCanvasControls").children().eq(1).css('height', 'auto'); 
	});
	
	function setTitleToInfoPanel(title) {
		$("#infoPanelDivHeader").html(title);
		$("#infoPanelDivHeader").append("<img id='closeIcon' src='images/close-icon.png' height='18' style='float:right;cursor:pointer;'/>");
		$("#closeIcon").attr("title", "<s:text name='widget.close'/>");
		$("#closeIcon").click(function() {
			$("#infoPanelDiv").css("display", "none");
		});
	}
	
///////////////////////////////////////////////////////
//////////BEGINING OF DESGLOSA-FACADE.JS /////////////
///////////////////////////////////////////////////////

var currentEntity = null;
var currentEntityBackup = null;
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
	 case 1:     // Left button
	     switch (clickCount) {
	         case 1:     // Click
	            // Hide infoDiv Panel if visible (it will be shown when new data is loaded)
	        	$('#infoPanelDiv').css('display','none');
	        	showLoadingIndicator(true, "<c:out value='${fetchingEntityInformation}'/>");
	        	var action = getPlainReportGenerationAction(currentEntity);
	        	if (action != null) {
	        		var title = "";
	        	    if (currentEntity == "company") {
	        	        title = "<c:out value='${companyFurtherInformation}'/>";
	        	    } else if (currentEntity == "factory") {
	        	    	title = "<c:out value='${factoryFurtherInformation}'/>";
	        	    } else if (currentEntity == "project") {
	        	    	title = "<c:out value='${projectFurtherInformation}'/>";
	        	    } else if (currentEntity == "subproject") {
	        	    	title = "<c:out value='${subprojectFurtherInformation}'/>";
	        	    }
	        	    setTitleToInfoPanel(title);
					$("#infoPanelDivContent").load(action + "?id=" + currentEntityId + " #plainReport", function() {
						$('#infoPanelDiv').css('display','');
						showLoadingIndicator(false);
					});
				} else {
					$('#errorDialogBody').html("<p class='messageBox error'><c:out value='${generalError}'/></p>");
					$('#errorDialog').dialog('open');
					showLoadingIndicator(false);
				}
				break;
	         case 2:     // Double click
	        	 // Hide infoDiv Panel
	        	 $('#infoPanelDiv').css('display','none');
	             // Show profile chooser dialog
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
	         default:    // Ignore multiple clicks but double click
	             break;
	     }
	     break;
	 case 2:     // Middle button
	     break;
	 case 3:     // Right button
	     break;
	 default:    // Any other button
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

function desglosa_showFactoriesByProjectId(id, groupBy, profileFilename) {
    currentEntity = "factory";
    desglosa_launchDesglosaEngine("/desglosa-web/json_factoriesByProjectId.action", id, groupBy, profileFilename);
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
    desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectsByProjectId.action", id, groupBy, profileFilename);
}

function desglosa_showSubprojectsById(id, groupBy, profileFilename) {
    currentEntity = "subproject";
    desglosa_launchDesglosaEngine("/desglosa-web/json_subprojectById.action", id, groupBy, profileFilename);
}

function desglosa_launchDesglosaEngine (action, id, groupBy, filename) {
	$("#infoPanelDiv").css('display', 'none');
    showLoadingIndicator(true, "<c:out value='${generating3dGraphics}'/>");
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
        	 showLoadingIndicator(false);
	         if (status == "success") {
	             var city = JSON.stringify(data.city);
	             if (city != "null" && city != undefined) {
	            	 $('#jogl_canvas').css('display','');
	                 desglosa_handleVisualization(data.city.model, city);
	             } else if (city == "null") {
	            	 currentEntity = currentEntityBackup;
	                 $('#jogl_canvas').css('display','none');
	                 $('#map_canvas').css('display','');
	                 $('#errorDialogBody').html("<p class='messageBox error'><c:out value='${malformedJSONString}'/></p>");
	                 $('#errorDialog').dialog('open');
	             } else if (city == undefined) {
	            	 currentEntity = currentEntityBackup;
	                 $('#errorDialogBody').html("<p class='messageBox error'><c:out value='${outdatedProfile}'/></p>");
	                 $('#errorDialog').dialog('open');
	             }
	         } else {
	             $('#jogl_canvas').css('display','none');
	             $('#map_canvas').css('display','');
	             $('#errorDialogBody').html("<p class='messageBox error'><c:out value='${generalError}'/></p>");
	             $('#errorDialog').dialog('open');
	         }
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
////////////END OF DESGLOSA-FACADE.JS ////////////////
///////////////////////////////////////////////////////

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
				<span style="margin-left: 15px;">
					<s:label for="companySelect" value="%{getText('label.select.company')}:"/>
					<select id="companySelect">
						<option value="-1" disabled="disabled">-- <fmt:message key="label.select.choose_company"/> --</option>
						<option value="0" selected="selected"><fmt:message key="label.all_female"/></option>
						<s:iterator var="company" value="companies">
							<option value="<s:property value='id'/>"><s:property value="name"/></option>
						</s:iterator>
					</select>
				</span>
				<span style="margin-left: 15px;">
					<s:label for="companyProjectSelect" value="%{getText('label.select.project')}:"/>
					<s:select id="companyProjectSelect" name="companyProjectSelect" listKey="id" list="projects" size="5"></s:select>
				</span>
			</div>
			
			<div id="factoryFilter" class="filter form">
				<span style="margin-left: 15px;">
				    <s:label for="factorySelect" value="%{getText('label.select.factory')}:"/>
				    <select id="factorySelect">
				        <option value="-1" disabled="disabled">-- <fmt:message key="label.select.choose_factory"/> --</option>
                        <option value="0" selected="selected"><fmt:message key="label.all_female"/></option>
                        <s:iterator var="factory" value="factories">
                            <option value="<s:property value='id'/>"><s:property value="name"/></option>
                        </s:iterator>
				    </select>
				</span>
				<span style="margin-left: 15px;">
				    <s:label for="factoryProjectSelect" value="%{getText('label.select.project')}:"/>
				    <s:select id="factoryProjectSelect" name="factoryProjectSelect" listKey="id" list="projects" size="5"></s:select>
				</span>
			</div>
			
			<div id="projectFilter" class="filter form">
                <span style="margin-left: 15px;">
                    <s:label for="projectSelect" value="%{getText('label.select.project')}:"/>
                    <select id="projectSelect">
                        <option value="-1" disabled="disabled">-- <fmt:message key="label.select.choose_project"/> --</option>
                        <option value="0" selected="selected"><fmt:message key="label.all_male"/></option>
                        <s:iterator var="project" value="projects">
                            <option value="<s:property value='id'/>"><s:property value="name"/></option>
                        </s:iterator>
                    </select>
                </span>
                <span style="margin-left: 15px;">
                    <s:label for="projectSubprojectSelect" value="%{getText('label.select.subproject')}:"/>
                    <s:select id="projectSubprojectSelect" name="projectSubprojectSelect" listKey="id" list="subprojects" size="5"></s:select>
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
			
			<a href="javascript:void(0)" onclick="$('#infoPanelDiv').css('display','none');$('#jogl_canvas').css('display','none');$('#map_canvas').css('display','');"><s:text name="label.back_to_map"/></a>
			
			<div id="joglCanvasControls">
				<h3><a href="#"><s:text name="label.navigation_controls"/></a></h3>
				<div>
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
				</div>
			</div>
		</div>

		<div id="tabs">
			<s:text name='label.detailed_info'/>:
			<sj:tabbedpanel id="infoTabs" animate="true">
				<sj:tab id="generalInfoTab" target="generalInformation" label="%{getText('label.global_info')}"/>
				<sj:tab id="companyInfoTab" target="companyInformation" label="%{getText('label.Company')}"/>
				<sj:tab id="factoryInfoTab" target="factoryInformation" label="%{getText('label.Factory')}"/>
				<sj:tab id="projectInfoTab" target="projectInformation" label="%{getText('label.Project')}"/>
				<sj:tab id="subprojectInfoTab" target="subprojectInformation" label="%{getText('label.Subproject')}"/>
				<div id="generalInformation" class="default"></div>
				<div id="companyInformation" class="default">
				    <img src="images/clean-icon.png" height="18" title="<s:text name='widget.clean'/>" style="float:right; cursor:pointer;" onclick="javascript:initializeCompanyTab()"/>
					<div class="content1"></div>
					<div class="content2"></div>
				</div>
				<div id="factoryInformation" class="default">
				    <img src="images/clean-icon.png" height="18" title="<s:text name='widget.clean'/>" style="float:right; cursor:pointer;" onclick="javascript:initializeFactoryTab()"/>
					<div class="content1"></div>
					<div class="content2"></div>
				</div>
				<div id="projectInformation" class="default">
				    <img src="images/clean-icon.png" height="18" title="<s:text name='widget.clean'/>" style="float:right; cursor:pointer;" onclick="javascript:initializeProjectTab()"/>
					<div class="content1"></div>
					<div class="content2"></div>
				</div>
				<div id="subprojectInformation" class="default">
				    <img src="images/clean-icon.png" height="18" title="<s:text name='widget.clean'/>" style="float:right; cursor:pointer;" onclick="javascript:initializeSubprojectTab()"/>
					<div class="content1"></div>
					<div class="content2"></div>
				</div>
			</sj:tabbedpanel>
			
			<div id="infoPanelDiv" style="display:none;" class="ui-widget ui-widget-content ui-corner-all container">
				<div id="infoPanelDivHeader" class="ui-widget ui-corner-all ui-widget-header default container-header">header test</div>
				<div id="infoPanelDivContent" class="default container-content">content test</div>
			</div>
			
			<div id="indicatorDiv">
				<img id="indicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none; margin-top:10px;"/><span id="indicatorText" style="margin-left:10px; position:relative; top:-10px;"></span>
			</div>
		</div>
		
		<div class="clear"></div>
	</div>	
	<div class="clear"></div>
</body>
</html>
