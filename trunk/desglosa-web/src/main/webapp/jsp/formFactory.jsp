<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageFactories"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
	<script type="text/javascript">
	function swapDivVisibility (toHide, toShow) {
		document.getElementById(toHide).style.display='none';
		document.getElementById(toShow).style.display='';
	}
	
	function resetAddrFields(){
		document.getElementById("factory.address.address").value = '';
		document.getElementById("factory.address.city").value = '';
		document.getElementById("factory.address.province").value = '';
		document.getElementById("factory.address.country").value = '';
		document.getElementById("factory.address.postalCode").value = '';
	}
	
	var map;
	var geocoder;
	var isLocationSet;
	var marker;
	
	function getFullAddress () {
		var address = document.getElementById("factory.address.address").value;
		var city = document.getElementById("factory.address.city").value;
		var province = document.getElementById("factory.address.province").value;
		var country = document.getElementById("factory.address.country").value;
		var postalCode = document.getElementById("factory.address.postalCode").value;
		
		return (address + ", " + postalCode + " " + city + ", " + province + ", " + country);
	}
	
	function placeMark(latLng) {
        marker = new google.maps.Marker({
            map: map, 
            position: latLng
        });
        map.panTo(latLng);
        isLocationSet = true;
        document.getElementById('factory.location.latitude').value = latLng.lat();
        document.getElementById('factory.location.longitude').value = latLng.lng();
	}
	
	function codeAddress (fullAddress, infoDiv) {
		var defaultLatLng = new google.maps.LatLng(40.445837+0.000797,-3.610146+0.001206);
		if (marker != null) marker.setMap(null);
		var status = -1;
		if (geocoder) {
	        geocoder.geocode( { 'address': fullAddress}, function(results, status) {
	        	switch(status)
	        	{
	        	case google.maps.GeocoderStatus.OK:
	        		if (infoDiv != null) document.getElementById(infoDiv).innerHTML="Address found.";
		            map.setCenter(results[0].geometry.location);
		            placeMark(results[0].geometry.location);
	        		break;
	        	case google.maps.GeocoderStatus.ZERO_RESULTS:
	        		var message = "Could not find specified address. Please, check that the address data is correct or click on the map.";
	        		if (infoDiv != null) document.getElementById(infoDiv).innerHTML=message;
	        		map.setZoom(1);
	        		map.setCenter(defaultLatLng);
	        		break;
	        	case google.maps.GeocoderStatus.INVALID_REQUEST:
	        		alert("Geocode INVALID_REQUEST: not implemented yet. " + status);
	        		break;
	        	case google.maps.GeocoderStatus.OVER_QUERY_LIMIT:
	        		alert("Geocode OVER_QUERY_LIMIT: not implemented yet. " + status);
	        		break;
	        	case google.maps.GeocoderStatus.REQUEST_DENIED:
	        		alert("Geocode REQUEST_DENIED: not implemented yet. " + status);
	        		break;
	        	default:
	        		alert("Geocode was not successful for the following reason: " + status);
	        		break;
	        	}
	        });
	      }
	}
	
	function codeLatLng(latlng) {
		var street_number;
		var street_address;
		var city;
		var province;
		var country;
		var postal_code;
		
		if(geocoder) {
		      geocoder.geocode({'latLng': latlng}, function(results, status) {
		          if (status == google.maps.GeocoderStatus.OK) {
		            if (results[0]) {
		            	var i;
		            	var j;
		            	for (i = 0; i < results[0].address_components.length; i++) {
		            		var component = results[0].address_components[i];
		            		for (j = 0; j < component.types.length; j++) {
		            			var type = component.types[j];
		            			if (type == "street_number") street_number = component.long_name;
		            			if (type == "route") street_address = component.long_name;
		            			if (type == "locality") city = component.long_name;
		            			if (type == "administrative_area_level_2") province = component.long_name;
		            			if (type == "country") country = component.long_name;
		            			if (type == "postal_code") postal_code = component.long_name;
		            		}
		            	}
	            		document.getElementById("factory.address.address").value = street_address + ", " + street_number;
	            		document.getElementById("factory.address.city").value = city;
	            		document.getElementById("factory.address.province").value = province;
	            		document.getElementById("factory.address.country").value = country;
	            		document.getElementById("factory.address.postalCode").value = postal_code;
		            }
		          } else {
		            alert("Geocoder failed due to: " + status);
		          }
		        });

		}
	}
	
	function initializeGMaps() {
	    var myOptions = {
	    	      zoom: 13,
	    	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    	    };
	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	    geocoder = new google.maps.Geocoder();
	    isLocationSet = false;
	    marker = null;
		google.maps.event.addListener(map, 'click', function(event) {
			if (isLocationSet) {
				marker.setMap(null);
				isLocationSet = false;
			}
			placeMark(event.latLng);
	        codeLatLng(event.latLng);
	    });
	}
	
	function searchAddress() {
		document.getElementById('map_info').innerHTML="Initializing maps...";
		document.getElementById('map_canvas').style.display='';
		initializeGMaps();
		document.getElementById('map_info').innerHTML="Map initialized.";
		var fullAddress = getFullAddress();
		document.getElementById('map_info').innerHTML="Searching address...";
		codeAddress(fullAddress, 'map_info');
	}
	
	function showCurrentLocation() {
		initializeGMaps();
		var latlng = new google.maps.LatLng(document.getElementById('factory.location.latitude').value,document.getElementById('factory.location.longitude').value);
		map.setCenter(latlng);
        placeMark(latlng);
	}
	</script>
</head>
<body>
	<s:text name="menu.admin.factories" />
	<s:actionerror />
	<s:fielderror />
	<c:set var="form" value="/editFactory.action"/>
	<c:set var="buttonLabel" value="button.edit_factory"/>
	<c:if test="${empty param.id}">
		<c:set var="form" value="/saveFactory.action"/>
		<c:set var="buttonLabel" value="button.add_factory"/>
	</c:if>
	<form id="formFactory" method="post" action="<c:url value="${form}"/>">
		<c:if test="${not empty param.id}">
			<s:hidden name="factory.id"/>
		</c:if>
		<div id="chooseCompany" style="display:">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.choose_company"/>
				</li>
				<li>
					<c:set var="companyChecked" value="false"/>
					<display:table name="requestScope.companies" id="company" cellspacing="0" cellpadding="0" defaultsort="1" class="" pagesize="50" requestURI="">
						<display:column style="width: 5%">
							<c:choose>
								<c:when test="${factory.company.id == company.id}">
									<c:set var="companyChecked" value="true"/>
									<input type="radio" name="factory.company.id" value="${company.id}" onclick="document.getElementById('gotoSecondStep').disabled=''" checked/>
								</c:when>
								<c:otherwise>
									<input type="radio" name="factory.company.id" value="${company.id}" onclick="document.getElementById('gotoSecondStep').disabled=''"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
					    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.company.information" sortable="false"/>
					    
					    <display:setProperty name="paging.banner.placement" value="bottom"/>
					    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
					    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
					    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
					</display:table>
				</li>
				<li>
					<input type="button" value="< Back" disabled="disabled" style="visibility: hidden"/>
					<c:choose>
						<c:when test="${companyChecked == 'false'}">
							<input type="button" id="gotoSecondStep" name="gotoSecondStep" value="Next >" disabled="disabled" onclick="swapDivVisibility('chooseCompany','fillFactoryData')"/>
						</c:when>
						<c:otherwise>
							<input type="button" id="gotoSecondStep" name="gotoSecondStep" value="Next >" onclick="swapDivVisibility('chooseCompany','fillFactoryData')"/>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
		<div id="fillFactoryData" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.data"/>
				</li>
				<li>
					<label for="factory.name"><fmt:message key="label.configure.factory.data.name"></fmt:message></label>
					<s:textfield id="factory.name" name="factory.name" tabindex="1"/>
				</li>
				<li>
					<label for="factory.information"><fmt:message key="label.configure.factory.data.information"></fmt:message></label>
					<s:textfield id="factory.information" name="factory.information" tabindex="2"/>
				</li>
				<li>
					<label for="factory.email"><fmt:message key="label.configure.factory.data.email"></fmt:message></label>
					<s:textfield id="factory.email" name="factory.email" tabindex="3"/>
				</li>
				<li>
					<label for="factory.employees"><fmt:message key="label.configure.factory.data.employees"></fmt:message></label>
					<s:textfield id="factory.employees" name="factory.employees" tabindex="4"/>
				</li>
				<li>
					<input type="button" id="gotoSecondStep" name="gotoSecondStep" value="< Back" onclick="swapDivVisibility('fillFactoryData','chooseCompany')"/>
					<input type="button" id="gotoThirdStep" name="gotoThirdStep" value="Next >" onclick="swapDivVisibility('fillFactoryData','fillDirector')"/>
				</li>
			</ul>
		</div>
		<div id="fillDirector" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.director"/>
				</li>
				<li>
					<label for="factory.director.name"><fmt:message key="label.configure.factory.director.name"></fmt:message></label>
					<s:textfield id="factory.director.name" name="factory.director.name" tabindex="1"/>
				</li>
				<li>
					<label for="factory.director.firstSurname"><fmt:message key="label.configure.factory.director.first_surname"></fmt:message></label>
					<s:textfield id="factory.director.firstSurname" name="factory.director.firstSurname" tabindex="2"/>
				</li>
				<li>
					<label for="factory.director.lastSurname"><fmt:message key="label.configure.factory.director.last_surname"></fmt:message></label>
					<s:textfield id="factory.director.lastSurname" name="factory.director.lastSurname" tabindex="3"/>
				</li>
				<li>
					image
				</li>
				<li>
					<input type="button" id="gotoThirdStep" name="gotoThirdStep" value="< Back" onclick="swapDivVisibility('fillDirector','fillFactoryData')"/>
					<c:choose>
						<c:when test="${not empty factory.location.latitude && not empty factory.location.longitude}">
							<input type="button" id="gotoFourthStep" name="gotoFourthStep" value="Next >" onclick="swapDivVisibility('fillDirector','fillAddress');showCurrentLocation();"/>
						</c:when>
						<c:otherwise>
							<input type="button" id="gotoFourthStep" name="gotoFourthStep" value="Next >" onclick="swapDivVisibility('fillDirector','fillAddress');searchAddress();"/>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
		<div id="fillAddress" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.address"/>
				</li>
				<li>
					<label for="factory.address.address"><fmt:message key="label.configure.factory.address.address"></fmt:message></label>
					<s:textfield id="factory.address.address" name="factory.address.address" tabindex="1"/>
				</li>
				<li>
					<label for="factory.address.city"><fmt:message key="label.configure.factory.address.city"></fmt:message></label>
					<s:textfield id="factory.address.city" name="factory.address.city" tabindex="2"/>
				</li>
				<li>
					<label for="factory.address.province"><fmt:message key="label.configure.factory.address.province"></fmt:message></label>
					<s:textfield id="factory.address.province" name="factory.address.province" tabindex="3"/>
				</li>
				<li>
					<label for="factory.address.country"><fmt:message key="label.configure.factory.address.country"></fmt:message></label>
					<s:textfield id="factory.address.country" name="factory.address.country" tabindex="4"/>
				</li>
				<li>
					<label for="factory.address.postalCode"><fmt:message key="label.configure.factory.address.postal_code"></fmt:message></label>
					<s:textfield id="factory.address.postalCode" name="factory.address.postalCode" tabindex="5"/>
				</li>				
				<li>
					<!-- address, number, postal_code city, province, country -->
					<s:hidden id="factory.location.latitude" name="factory.location.latitude"/>
					<s:hidden id="factory.location.longitude" name="factory.location.longitude"/>
					<input type="button" id="searchLocation" name="searchLocation" value="Locate" onclick="searchAddress();codeLatLng(new google.maps.LatLng(document.getElementById('factory.location.latitude').value,document.getElementById('factory.location.longitude').value));"/>
					<input type="button" id="resetAddressFields" name="resetAddressFields" value="Reset fields" onclick="resetAddrFields()"/>
					<div id="map_info"></div>
					<div id="map_canvas" style="width: 600px; height: 400px; display: ;">
					</div>
				</li>
				<li>
					<input type="button" id="gotoFourthStep" name="gotoFourthStep" value="< Back" onclick="swapDivVisibility('fillAddress','fillDirector')"/>
					<input type="submit" id="submit" name="submit" value="Finish"/>
				</li>
			</ul>
		</div>
	</form>
</body>
</html>