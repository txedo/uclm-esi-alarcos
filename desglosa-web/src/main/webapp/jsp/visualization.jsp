<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="Visualization"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/desglosa-facade.js"></script>
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
		$.getJSON("/desglosa-web/getFactoriesJSON.action",
				{
					id: idFactory
				},
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
	
	function getFactoryLocationFromCompany(idCompany) {
		showLoadingIndicator(true);
		if (!idCompany) var idCompany = "0";
		else var idCompany = idCompany+"";
		$.getJSON("/desglosa-web/getFactoriesFromCompanyJSON.action",
				{
					id: idCompany
				},
				function (data, status) {
					if (status == "success") {
						$("#factorySelect").attr("disabled", "");
						$("#factorySelect").empty();
						$.each(data.factories, function (i, item) {
							var marker = placeMarker(item.location.latitude, item.location.longitude);
							marker.setTitle(item.name);
							var infoWindow = createInfoWindow(item);
							addMarkerEvents(marker, infoWindow);
							$('#factorySelect').append($("<option></option>").attr("value", item.id).text(item.name));
						});
						$('#factorySelect').append($("<option></option>").attr("value", 0).text("Todas"));
						$("#factorySelect option:last").attr('selected','selected');
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
					showLoadingIndicator(false);
		});
	}
	
	function getCompany(idCompany) {
		showLoadingIndicator(true);
		if (!idCompany) var idCompany = "0";
		else var idCompany = idCompany+"";
		$.getJSON("/desglosa-web/getCompaniesJSON.action",
				{
					id: idCompany
				},
				function (data, status) {
					if (status == "success") {
						$.each(data.companies, function (i, item) {
							$("#companyInformation").append(formatCompanyInformation(item));
						});
						showLoadingIndicator(false);
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
			result += "<img src='" + directorJSON.imagePath + "' width='128' height='128'/><br />";
		return result;
	}
	
	function showLoadingIndicator(value) {
		if (value)
			$("#indicator").css("display","");
		else
			$("#indicator").css("display","none");
	}
	
	$(document).ready(function() {
		// Initialize Google Maps canvas
		initializeMap();
		
		$("#companySelect option:first").attr('selected','selected');
		$("#companySelect").change( function() {
			clearAllMarkers();
			getFactoryLocationFromCompany($("#companySelect").val());
			if ($("#companySelect").val() == 0) {
				// reset company info div
				$("#companyInformation").text("");
			}
			else {
				// show info in company info div
				$("#companyInformation").text("");
				getCompany($("#companySelect").val());
				// Indicator will be hidden inside getCompany() when the action finishes.
			}
		});
		
		$("#factorySelect").change( function() {
			clearAllMarkers();
			if ($("#factorySelect").val() == 0) {
				// reset factory info div
				$("#factoryInformation").text("");
				getFactoryLocationFromCompany($("#companySelect").val());
			}
			else {
				// show info in factory info div
				$("#factoryInformation").text("");
				getFactoryLocation($("#factorySelect").val());
				// Indicator will be hidden inside getFactoryLocation() when the action finishes.
			}
		});
		
		// Add the "all projects" option to the combobox
		//$("<option value='-1'><s:text name='label.none_male'/></option>").appendTo("#projectSelect");
		// Define and set its onChange function
		$("#projectSelect").change( function() {
			//alert('before');
			//if ($("#projectSelect").val() == $("#prevSelectedProject").val())
				alert($("#prevSelectedProject").val());
				$("#projectSelect").val(-1);
			//$("#prevSelectedProject").val($("#projectSelect").val());
			//alert('after');
		});
		
		// Select "all projects" option by default
		//$("#projectSelect").val(-1);
	});

	</script>
</head>
<body>
	<div id="filter">
		<fieldset>
			<legend><s:text name="label.filter.corporative"/>:</legend>
			<s:label for="companySelect" value="%{getText('label.select.company')}:"/>
			<select id="companySelect" >
				<option value="" disabled="disabled">-- <fmt:message key="label.select.choose_company"/> --</option>
				<s:iterator var="company" value="companies">
					<option value="<s:property value='id'/>"><s:property value="name"/></option>
				</s:iterator>
				<option value="0"><fmt:message key="label.all_female"/></option>
			</select>
			
			<s:label for="factorySelect" value="%{getText('label.select.factory')}:"/>
			<select id="factorySelect" disabled="disabled"></select>
			
			<s:label for="projectSelect" value="%{getText('label.select.project')}:"/>
			<s:select id="projectSelect" name="projectSelect" listKey="id" list="projects" size="5"></s:select>
			<input type="hidden" name="prevSelectedProject" value="-1"/>
		</fieldset>
	</div>
	
	<div style="clear:both;"></div>
	
	<div id="workingArea" style="position:relative; height:450px">
		<div id="map_canvas" style="position:absolute; top:0; left:0; width: 600px; height: 400px; display: ;"></div>
		
		<div id="jogl_canvas" style="position:absolute; top:0; left:0; width: 600px; height: 400px; display: none;">
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
			<a href="#" onclick="$('#jogl_canvas').css('display','none');$('#map_canvas').css('display','');"><s:text name="label.back_to_map"/></a>
		</div>
	
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
