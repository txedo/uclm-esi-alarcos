<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="Desglosa Visualization System"/></title>
	<link href="<s:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/cufon-yui.js"></script>
	<script type="text/javascript" src="js/arial.js"></script>
	<script type="text/javascript" src="js/cuf_run.js"></script>
    <!-- <link href="<s:url value='/styles/main.css'/>" rel="stylesheet" type="text/css" media="all"/>  -->
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css'/>" rel="stylesheet" type="text/css" media="print"/>
    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js'/>"></script>
	<script language="JavaScript" type="text/javascript">
        window.onload = function(){
            if(!NiftyCheck()) {
                return;
            }
            // perform niftycorners rounding
            // eg.
            // Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
        }
    </script>
    <decorator:head/>
</head>
<body id="page-home">
    <div id="page" class="main">
		<div class="header">
			<div class="header_resize">
				<div class="logo">
					<h1><a href="index.action">des<span>gl</span>osa</a></h1>
				</div>
				<div class="menu_nav">
					<ul>
						<li class="active"><a href="index.action"><span>Home</span></a></li>
						<li><a href="support.html"><span>My Account</span></a></li>
						<li><a href="startVisualization.action"><span>Visualization</span></a></li>
						<li><a href="contact.html"><span>Contact Us</span></a></li>
					</ul>
					<div class="clr"></div>
				</div>
		
		    	<div class="clr"></div>
			    <div class="header_img"><img src="images/logo.png" alt="" width="271" height="234" />
					<h2>desglosa visualization engine</h2>
					<p><strong>a tagline </strong><br />
					a description about desglosa
					</p>
					<div class="clr"></div>
			    </div>
			</div>
		</div>

		<div class="content">
			<div class="content_resize">
            	<decorator:body/>
                <hr />
			</div>
		</div>
 
		<div class="fbg">
			<div class="fbg_resize">
				<div class="col c1">
					<h2><span>Sitemap section 1</span></h2>
					<p>subsections</p>
				</div>
				<div class="col c2">
					<h2><span>Sitemap section 2</span></h2>
					<p>more subsections</p>
				</div>
				<div class="col c3">
					<h2><span>Sitemap section 3</span></h2>
					<p>... and more subsections</p>
				</div>
				<div class="clr"></div>
			</div>
			<div class="footer">
				<p class="lf">&copy; Copyright <a href="#">Desglosa</a>.</p>
				
				<p class="rf">Layout by Cool <a href="http://www.coolwebtemplates.net/">Website Templates</a></p>
				<div class="clr"></div>
			</div>
		</div>
    </div>
</body>
</html>
