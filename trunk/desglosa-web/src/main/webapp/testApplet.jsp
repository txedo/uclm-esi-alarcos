<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Index</title>
	<s:head />
</head>
<body>
	Insert your applet code here.<br>
	<applet code="org.jdesktop.applet.util.JNLPAppletLauncher"
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
	</applet>
</body>
</html>
	