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
	
	function placeMark(latLng, guessAddress) {
        marker = new google.maps.Marker({
            map: map, 
            position: latLng
        });
        map.panTo(latLng);
        isLocationSet = true;
        document.getElementById('factory.location.latitude').value = latLng.lat();
        document.getElementById('factory.location.longitude').value = latLng.lng();
        if (guessAddress) codeLatLng(latLng);
	}
	
	function codeAddress (fullAddress, infoDiv) {
		var defaultLatLng = new google.maps.LatLng(40.41663944983577,-3.703686048961572);
		if (marker != null) marker.setMap(null);
		var status = -1;
		if (geocoder) {
	        geocoder.geocode( { 'address': fullAddress}, function(results, status) {
	        	switch(status)
	        	{
	        	case google.maps.GeocoderStatus.OK:
	        		if (infoDiv != null) document.getElementById(infoDiv).innerHTML="Address found.";
		            map.setCenter(results[0].geometry.location);
		            placeMark(results[0].geometry.location, true);
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
		        	  var message = "Could not find specified address. Please, check that the address data is correct or click on the map.";
		        	  document.getElementById(infoDiv).innerHTML=message;
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
			placeMark(event.latLng, true);
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
        placeMark(latlng, false);
        return latlng
	}
	
	function foo () {
		searchAddress();
		var latlng = showCurrentLocation();
		codeLatLng(latlng);
		
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
		
		<s:div id="fillFactoryData" cssStyle="display: ">
			<s:text name="%{getText('label.configure.factory.choose_company')}:"/>
			
			<display:table name="requestScope.companies" id="company" cellspacing="0" cellpadding="0" defaultsort="1" pagesize="10">
				<display:column style="width: 5%">
					<c:choose>
						<c:when test="${factory.company.id == company.id}">
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
			
			<br /><s:text name="%{getText('label.configure.factory.data')}:"/>

			<br /><s:label for="factory.name" value="%{getText('label.configure.factory.data.name')}:"/>
			<s:textfield id="factory.name" name="factory.name" tabindex="1"/>
			<br /><s:label for="factory.information" value="%{getText('label.configure.factory.data.information')}:"/>
			<s:textfield id="factory.information" name="factory.information" tabindex="2"/>
			<br /><s:label for="factory.email" value="%{getText('label.configure.factory.data.email')}:"/>
			<s:textfield id="factory.email" name="factory.email" tabindex="3"/>
			<br /><s:label for="factory.employees" value="%{getText('label.configure.factory.data.employees')}:"/>
			<s:textfield id="factory.employees" name="factory.employees" tabindex="4"/>
			
			<br /><s:a href="#" onclick="swapDivVisibility('fillFactoryData','fillDirector')">Next &gt;</s:a>
		</s:div>
		
		<s:div id="fillDirector" cssStyle="display: none">
			<s:text name="%{getText('label.configure.factory.director')}:"/>

			<br /><s:label for="factory.director.name" value="%{getText('label.configure.factory.director.name')}:"/>
			<s:textfield id="factory.director.name" name="factory.director.name" tabindex="1"/>
			<br /><s:label for="factory.director.firstSurname" value="%{getText('label.configure.factory.director.first_surname')}:"/>
			<s:textfield id="factory.director.firstSurname" name="factory.director.firstSurname" tabindex="2"/>
			<br /><s:label for="factory.director.lastSurname" value="%{getText('label.configure.factory.director.last_surname')}:"/>
			<s:textfield id="factory.director.lastSurname" name="factory.director.lastSurname" tabindex="3"/>
			<br />image

			<br /><s:a href="#" onclick="swapDivVisibility('fillDirector','fillFactoryData')">&lt; Back</s:a>
			<c:choose>
				<c:when test="${not empty factory.location.latitude && not empty factory.location.longitude}">
					<s:a href="#" onclick="swapDivVisibility('fillDirector','fillAddress');showCurrentLocation();">Next &gt;</s:a>
				</c:when>
				<c:otherwise>
					<s:a href="#" onclick="swapDivVisibility('fillDirector','fillAddress');searchAddress();">Next &gt;</s:a>
				</c:otherwise>
			</c:choose>
		</s:div>
		
		<s:div id="fillAddress" cssStyle="display: none">
			<s:text name="%{getText('label.configure.factory.address')}:"/>

			<br /><s:label for="factory.address.address" value="%{getText('label.configure.factory.address.address')}:"/>
			<s:textfield id="factory.address.address" name="factory.address.address" tabindex="1"/>
			<br /><s:label for="factory.address.city" value="%{getText('label.configure.factory.address.city')}:"/>
			<s:textfield id="factory.address.city" name="factory.address.city" tabindex="2"/>
			<br /><s:label for="factory.address.province" value="%{getText('label.configure.factory.address.province')}:"/>
			<s:textfield id="factory.address.province" name="factory.address.province" tabindex="3"/>
			<br /><s:label for="factory.address.country" value="%{getText('label.configure.factory.address.country')}:"/>
			<s:textfield id="factory.address.country" name="factory.address.country" tabindex="4"/>
			<br /><s:label for="factory.address.postalCode" value="%{getText('label.configure.factory.address.postal_code')}:"/>
			<s:textfield id="factory.address.postalCode" name="factory.address.postalCode" tabindex="5"/>

			<!-- address, number, postal_code city, province, country -->
			<s:hidden id="factory.location.latitude" name="factory.location.latitude"/>
			<s:hidden id="factory.location.longitude" name="factory.location.longitude"/>
			<br /><s:a href="#" onclick="foo()">Locate</s:a>
			<s:a href="#" onclick="resetAddrFields()">Reset</s:a>
			<br /><s:div id="map_info"></s:div>
			<br /><s:div id="map_canvas" cssStyle="width: 600px; height: 400px; display: ;"></s:div>

			<br /><s:a href="#" onclick="swapDivVisibility('fillAddress','fillDirector')">&lt; Back</s:a>
			<s:submit value="%{getText(#attr.buttonLabel)}"/>
		</s:div>
	</form>
</body>
</html>