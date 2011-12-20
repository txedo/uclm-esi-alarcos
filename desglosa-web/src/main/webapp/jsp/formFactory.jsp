<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageFactories"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en-US"></script>
	<script type="text/javascript" src="js/utils.js?version=1"></script>
	<script type="text/javascript">	
	/** Reset address fields.
	 */
	function resetAddrFields(){
		document.getElementById("factory.address.address").value = '';
		document.getElementById("factory.address.city").value = '';
		document.getElementById("factory.address.province").value = '';
		document.getElementById("factory.address.country").value = '';
		document.getElementById("factory.address.postalCode").value = '';
	}
	
	/** Format address fields into a long address name.
	 * @return full address name (long name).
	 */
	function getFullAddress () {
		var address = document.getElementById("factory.address.address").value;
		var city = document.getElementById("factory.address.city").value;
		var province = document.getElementById("factory.address.province").value;
		var country = document.getElementById("factory.address.country").value;
		var postalCode = document.getElementById("factory.address.postalCode").value;
		
		return (address + ", " + postalCode + " " + city + ", " + province + ", " + country);
	}
	
	// Page-scope global objects used by Google maps functions.
	var map;
	var geocoder;
	var marker;
	var isLocationSet; // boolean used as control variable.

	

	/** Place a Google marker in Google map (global variable).
	 * @param latLng Google LatLng object which specifies latitude and longitude.
	 * @param guessAddress boolean which evaluates if the LatLng object must be coded as address.
	 */
	function placeMark(latLng, guessAddress) {
		// Create a new Google marker into the map
		marker = new google.maps.Marker({
		    map: map, 
		    position: latLng
		});
		// Move to the new marker location
        map.panTo(latLng);
		// Set the control variable as true, indicating that the location has been set
        isLocationSet = true;
		// Set latitude and longitude fields
        document.getElementById('factory.location.latitude').value = latLng.lat();
        document.getElementById('factory.location.longitude').value = latLng.lng();
        // Code latitude and longitude to address name if needed
        if (guessAddress) codeLatLng(latLng);
	}
	
	/** Code a full address into LatLng object in order to create a marker.
	 * @param fullAddress A string containing the full address that will be coded into a LatLng object.
	 * @param infoDiv Optional param. This is a HTML div ID in which feedback information will be printed in.
	 */
	function codeAddress (fullAddress, infoDiv) {
		// Set a default latlng (Madrid, km. 0) that will be used if geocoder can find a latlng value for the address
		var defaultLatLng = new google.maps.LatLng(40.41663944983577,-3.703686048961572);
		// if a marker is already set, remove it. This will allow only one marker in the map because of configuration reasons.
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
	
	/** Code a Google LatLng object into an address and places its values in HTML fields.
	 * @param latlng Google LatLng object that will be coded into an address.
	 */
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
	
	/** Initialization function called by searchAddress and showCurrentLocation functions. Developers must NOT call this function.
	 * It initiates the page-scope global variables mentioned before and add an event to the map that will allow to place a marker by clicking in it.
	 */
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
	
	/** This function initiates Google objects, prints feedback and places a marker by coding the address fields.
	 */
	function searchAddress() {
		document.getElementById('map_info').innerHTML="Initializing maps...";
		document.getElementById('map_canvas').style.display='';
		initializeGMaps();
		document.getElementById('map_info').innerHTML="Map initialized.";
		var fullAddress = getFullAddress();
		document.getElementById('map_info').innerHTML="Searching address...";
		codeAddress(fullAddress, 'map_info');
	}
	
	/** This function initiates Google objects and places a marker if a latitude and longitude values are set.
	 */
	function showCurrentLocation() {
		initializeGMaps();
		var latlng = new google.maps.LatLng(document.getElementById('factory.location.latitude').value,document.getElementById('factory.location.longitude').value);
		map.setCenter(latlng);
        placeMark(latlng, false);
        return latlng
	}
	
	/** This function initiates Google objects, shows current location if latitude and longitude values are set and code them into an address given by Google.
	 */
	function locate () {
		searchAddress();
		var latlng = showCurrentLocation();
		codeLatLng(latlng);
	}
	</script>
</head>
<body>
	<h1><s:text name="management.factory.add.title" /></h1>
	<p><s:text name="management.factory.add.text" /></p>
	
	<s:actionerror />
	<s:fielderror><s:param>error.required_fields</s:param></s:fielderror>
	
	<c:set var="form" value="/editFactory"/>
	<c:set var="buttonLabel" value="button.edit_factory"/>
	<c:if test="${empty param.id and not fn:endsWith(header.referer,'/editFactory') and not fn:contains(header.referer,'id=')}">
		<c:set var="form" value="/saveFactory"/>
		<c:set var="buttonLabel" value="button.add_factory"/>
	</c:if>

	<form id="formFactory" class="form" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
		<s:hidden name="factory.id"/>
		
		<s:div id="fillFactoryData" cssStyle="display: ">
		  <fieldset class="formfieldset">
		    <h2><s:text name="label.configure.factory.company"/></h2>
			<s:fielderror><s:param>error.company_mandatory</s:param></s:fielderror>
			<s:text name="label.configure.factory.choose_company"/>
			
			<s:set name="companies" value="companies" scope="request"/>  
			<s:set name="factory" value="factory" scope="request"/>
			<display:table name="companies" id="company" cellspacing="0" cellpadding="0" defaultsort="1" pagesize="10" requestURI="showFactoryForm.action">
				<display:column style="width: 5%">
					<c:choose>
						<c:when test="${factory.company.id == company.id or company.id == param.companyId}">
							<input type="radio" name="factory.company.id" value="${company.id}" checked/>
						</c:when>
						<c:otherwise>
							<input type="radio" name="factory.company.id" value="${company.id}" />
						</c:otherwise>
					</c:choose>
				</display:column>
			    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
			    <display:column property="information" escapeXml="true" style="width: 55%" titleKey="table.header.company.information" sortable="false"/>
			    
			    <display:setProperty name="paging.banner.placement" value="bottom"/>
			    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
			    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
			    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
			    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
			    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
			    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
			</display:table>
		  </fieldset>
		  
		  <fieldset class="formfieldset">
			<h2><s:text name="label.configure.factory.data"/></h2>
            <ul>
                <li>
		            <label for="factory.name"><s:text name="label.configure.factory.data.name"/></label>
		            <s:textfield id="factory.name" name="factory.name" tabindex="1"/>
		            <s:fielderror><s:param>error.factory.name</s:param></s:fielderror>
	            </li>
                <li>
		            <label for="factory.information"><s:text name="label.configure.factory.data.information"/></label>
		            <s:textfield id="factory.information" name="factory.information" tabindex="2"/>
		            <s:fielderror><s:param>error.factory.information</s:param></s:fielderror>
	            </li>
                <li>
		            <label for="factory.email"><s:text name="label.configure.factory.data.email"/></label>
		            <s:textfield id="factory.email" name="factory.email" tabindex="3"/>
		            <s:fielderror><s:param>error.factory.email</s:param></s:fielderror>
	            </li>
                <li>
		            <label for="factory.employees"><s:text name="label.configure.factory.data.employees"/></label>
		            <s:textfield id="factory.employees" name="factory.employees" tabindex="4"/>
		            <s:fielderror><s:param>error.factory.employees</s:param></s:fielderror>
	            </li>
            </ul>
		  </fieldset>
          <div class="clear"></div>
          <div class="wizardSteps">
              <a href="javascript:void(0)" class="right" onclick="swapDivVisibility('#fillFactoryData','#fillDirector')"><s:text name="label.next"/> &gt;</a>
          </div>
          <div class="clear"></div>
		</s:div>
		
		<s:div id="fillDirector" cssStyle="display: none">
		  <fieldset class="formfieldset">
			<h2><s:text name="label.configure.director"/></h2>
            <ul>
                <li>
					<label for="factory.director.name"><s:text name="label.configure.director.name"/></label>
					<s:textfield id="factory.director.name" name="factory.director.name" tabindex="1"/>
					<s:fielderror><s:param>error.director.name</s:param></s:fielderror>
                </li>
                <li>
					<label for="factory.director.lastName"><s:text name="label.configure.director.last_name"/></label>
					<s:textfield id="factory.director.lastName" name="factory.director.lastName" tabindex="2"/>
					<s:fielderror><s:param>error.director.lastName</s:param></s:fielderror>
					<s:hidden id="factory.director.imagePath" name="factory.director.imagePath"/>
				</li>
				<!-- factory var is already set at the beggining of this form -->
				<c:if test="${not empty factory.director.imagePath}">
				<li>
					<label for="factory.director.image"><s:text name="label.configure.director.current_image"/></label>
					<img src="<s:text name='factory.director.imagePath'/>" width="128" height="128" alt="%{getText('label.configure.director.current_image')}"/>
			    </li>
			    </c:if>
                <li>
					<label for="factory.director.image"><s:text name="label.configure.director.image"/></label>
					<s:file id="factory.director.image" name="upload"></s:file>
					<s:fielderror><s:param>error.director.image</s:param></s:fielderror>
                </li>
            </ul>
          </fieldset>
          <div class="clear"></div>
          <div class="wizardSteps">
			<a href="javascript:void(0)" class="left" onclick="swapDivVisibility('#fillDirector','#fillFactoryData')">&lt; <s:text name="label.back"/></a>
			<c:choose>
				<c:when test="${not empty factory.location.latitude && not empty factory.location.longitude}">
					<a href="javascript:void(0)" class="right" onclick="swapDivVisibility('#fillDirector','#fillAddress');showCurrentLocation();"><s:text name="label.next"/> &gt;</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0)" class="right" onclick="swapDivVisibility('#fillDirector','#fillAddress');searchAddress();"><s:text name="label.next"/> &gt;</a>
				</c:otherwise>
			</c:choose>
          </div>
          <div class="clear"></div>
		</s:div>
		
		<s:div id="fillAddress" cssStyle="display: none">
		  <fieldset class="formfieldset">
			<h2><s:text name="label.configure.factory.address"/></h2>
            <ul>
                <li>
					<label for="factory.address.address"><s:text name="label.configure.factory.address.address"/></label>
					<s:textfield id="factory.address.address" name="factory.address.address" tabindex="1"/>
					<s:fielderror><s:param>error.factory.address.address</s:param></s:fielderror>
                </li>
                <li>
					<label for="factory.address.city"><s:text name="label.configure.factory.address.city"/></label>
					<s:textfield id="factory.address.city" name="factory.address.city" tabindex="2"/>
					<s:fielderror><s:param>error.factory.address.city</s:param></s:fielderror>
                </li>
                <li>
					<label for="factory.address.province"><s:text name="label.configure.factory.address.province"/></label>
					<s:textfield id="factory.address.province" name="factory.address.province" tabindex="3"/>
					<s:fielderror><s:param>error.factory.address.province</s:param></s:fielderror>
                </li>
                <li>
					<label for="factory.address.country"><s:text name="label.configure.factory.address.country"/></label>
					<s:textfield id="factory.address.country" name="factory.address.country" tabindex="4"/>
					<s:fielderror><s:param>error.factory.address.country</s:param></s:fielderror>
                </li>
                <li>
					<label for="factory.address.postalCode"><s:text name="label.configure.factory.address.postal_code"/></label>
					<s:textfield id="factory.address.postalCode" name="factory.address.postalCode" tabindex="5"/>
					<s:fielderror><s:param>error.factory.address.postalCode</s:param></s:fielderror>
		          
					<!-- address, number, postal_code city, province, country -->
					<s:hidden id="factory.location.latitude" name="factory.location.latitude"/>
					<s:hidden id="factory.location.longitude" name="factory.location.longitude"/>
					<s:fielderror><s:param>error.factory.location</s:param></s:fielderror>
                </li>
                <li>
					<button class="minimal" onclick="locate();return false;"><s:text name="label.locate"/></button>
					<button class="minimal" onclick="resetAddrFields();return false;"><s:text name="label.reset"/></button>
                </li>
                <li>
				    <s:div id="map_info"></s:div>
				    <s:div id="map_canvas" cssStyle="width: 600px; height: 400px; display: ;"></s:div>
			    </li>
            </ul>
          </fieldset>
          <div class="clear"></div>
          <div class="wizardSteps">
			<a href="javascript:void(0)" class="left" onclick="swapDivVisibility('#fillAddress','#fillDirector')">&lt; <s:text name="label.back"/></a>
			<s:submit value="%{getText(#attr.buttonLabel)}"/>
          </div>
          <div class="clear"></div>
		</s:div>
	</form>
</body>
</html>