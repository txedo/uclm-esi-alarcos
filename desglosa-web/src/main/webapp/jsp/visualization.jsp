<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta name="menu" content="Visualization"/>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAxuDgnVkR83ZsPPJVN5640BT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRwX1ZX0A76eLz5aso7mDQkPeuUnA" type="text/javascript"></script>
	<script src="js/googlemaps.js" type="text/javascript"></script>
	<script src="js/desglosa-facade.js" type="text/javascript"></script>
</head>
<body>
	<div id="visualization_canvas">
		<div id="map_canvas" style="width: 600px; height: 400px">
			<script type="text/javascript">initialize();</script>
		</div>
		<div id="jogl_canvas">
			<applet code="org.jdesktop.applet.util.JNLPAppletLauncher" 
				codebase="./" 
				ID="DesglosaApplet"
				alt="Check your browser configuration to allow java applets." 
			    width=600
			    height=400
			    archive="http://jogamp.org/deployment/util/applet-launcher.jar,
			             http://jogamp.org/deployment/webstart/newt.all.jar,
			             http://jogamp.org/deployment/webstart/nativewindow.all.jar,
			             http://jogamp.org/deployment/webstart/jogl.all.jar,
			             http://jogamp.org/deployment/webstart/gluegen-rt.jar,
			             applet/desglosa.jar">
			   <param name="codebase_lookup" value="false">
			   <param name="subapplet.classname" value="presentation.AppletMain">
			   <param name="subapplet.displayname" value="Desglosa Applet">
			   <param name="noddraw.check" value="true">
			   <param name="progressbar" value="true">
			   <param name="jnlpNumExtensions" value="1">
			   <param name="jnlpExtension1"
			          value="http://jogamp.org/deployment/webstart/jogl-core.jnlp">
			   <param name="java_arguments" value="-Dsun.java2d.noddraw=true">
			   <param name="jnlp_href" value="applet/applet-desglosa.jnlp">
			   You have no JRE installed.<br>
			   Visit <a href="http://www.java.com/download/index.jsp" alt="Java.com homepage" target="_blank">Java download page</a> to verify and/or install a JRE.
			</applet>
		</div>
		<br>
		<input type="button" name="Button1" value="Start" onClick="javascript:startJSDesglosa()">
	</div>
</body>
</html>
