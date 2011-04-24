<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Google Maps testing page</title>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAxuDgnVkR83ZsPPJVN5640BT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRwX1ZX0A76eLz5aso7mDQkPeuUnA" type="text/javascript">
	</script>
	<script type="text/javascript">
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(37.4419, -122.1419), 13);
      }
    }
    </script>
    <s:head />
</head>
<body onload="initialize()" onunload="GUnload()">
	<input type="button" name="Load" value="Load" onclick="initialize()">
	<div id="map_canvas" style="width: 500px; height: 300px"></div>
	<s:body />
</body>

</html>