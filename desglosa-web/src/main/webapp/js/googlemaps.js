function initialize() {
  if (GBrowserIsCompatible()) {
    var map = new GMap2(document.getElementById("map_canvas"));
    if (!map.draggingEnabled()) map.enableDragging(); // Permite arrastrar el mapa (activado de forma predeterminada).
    if (!map.infoWindowEnabled()) { map.enableInfoWindow(); } // Activa el uso de ventanas de información en el mapa (activado de forma predeterminada).
    if (!map.doubleClickZoomEnabled()) { map.enableDoubleClickZoom(); } // Habilita el doble clic para acercar y alejar la imagen (habilitado de forma predeterminada).
    if (!map.continuousZoomEnabled()) { map.enableContinuousZoom(); } // Permite acercar y alejar la imagen de forma continua y gradual en navegadores capacitados para ello (desactivado de forma predeterminada).
    if (!map.scrollWheelZoomEnabled()) { map.enableScrollWheelZoom(); } // Permite acercar y alejar la imagen con una rueda de desplazamiento del ratón. Nota: la posibilidad de acercar y alejar la imagen con la rueda de desplazamiento está desactivada de forma predeterminada.
    // Añadimos una serie de controles al mapa (más info en http://code.google.com/intl/es-ES/apis/maps/documentation/javascript/v2/reference.html#GControl)
    map.addControl(new GLargeMapControl()); // Crea un control con botones para hacer desplazamientos en cuatro direcciones y para acercar y alejar la imagen, así como una barra deslizante para usar el acercamiento.
    map.addControl(new GMenuMapTypeControl()); // Crea un control de tipo de mapa desplegable para pasar de un tipo de mapa admitido a otro.
    map.addControl(new GScaleControl()); // Crea un control que muestra la escala del mapa.
    // Establecemos el centro del mapa. Para movernos en código podemos usar las funciones zoomIn() y panTo()
    map.setCenter(new GLatLng(37.4419, -122.1419), 13);
    // G_NORMAL_MAP: la vista predeterminada.
	// G_SATELLITE_MAP: imágenes de satélite de Google Earth.
	// G_HYBRID_MAP: mezcla de vistas normales y de satélite.
	// G_DEFAULT_MAP_TYPES: una mezcla de estos tres tipos, útil para un procesamiento repetitivo
    map.setMapType(G_NORMAL_MAP);
    // Configuramos un nuevo punto en base a su latitud y longitud
    var point = new GLatLng(37.4419, -122.1419);
    // Creamos un nuevo icono para el marker
    var factoryIcon = new GIcon(G_DEFAULT_ICON);
    //factoryIcon.image = "icons/Factory-Yellow-icon.png";
    //factoryIcon.iconSize = new GSize(40, 40);
    // Configuramos un objeto markerOptions
    markerOptions = { icon:factoryIcon, draggable:false };
    // Con ese punto creamos un nuevo marker que no se puede arrastrar
    var marker = new GMarker(point, markerOptions);
    // Añadimos el marker al mapa
    map.addOverlay(marker);
    // Configuramos pestañas para mostrar informacion
    var tabCompany = new GInfoWindowTab("Company info", "<u>info</u> de la company");
    var tabFactory = new GInfoWindowTab("Factory info", "<b>info</b> de la factory");
    var tabDirector = new GInfoWindowTab("Director info", "<i>info</i> del director");
    var infoTabs = [tabCompany, tabFactory, tabDirector];
    // Asociamos las pestañas al marker
    marker.bindInfoWindowTabsHtml(infoTabs);
    GEvent.addListener(marker, "dblclick", function() {
    	alert("You double-clicked a marker!");
    });
    GEvent.addListener(map, "click", function(overlay, latlng) {
   		if (latlng) { 
			var myHtml = "The GPoint value is (" + latlng.lat() + ", " + latlng.lng() + ")";
			alert(myHtml);
		}
    });
  }
}