<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>	
	<!-- HTTP 1.1 -->
    <meta http-equiv="Cache-Control" content="no-store"/>
    <!-- HTTP 1.0 -->
    <meta http-equiv="Pragma" content="no-cache"/>
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0"/>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title><decorator:title/> <fmt:message key="desglosa.title"/></title>
	<link href="<s:url value='/styles/style.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/buttonscss3.css?version=1'/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/cufon-yui.js"></script>
	<script type="text/javascript" src="js/arial.js"></script>
	<script type="text/javascript" src="js/cuf_run.js"></script>
	<script type="text/javascript" src="js/global.js?version=1"></script>
	
    <decorator:head/>
</head>
<body id="<decorator:getProperty property="body.id" />" onload="<decorator:getProperty property="body.onload" />">
    <div id="page" class="main">
		<div class="header">
			<div class="header_resize">
				<div class="logo">
					<h1><a href="index.action">des<span>gl</span>osa</a></h1>
				</div>
				<div class="menu_nav">
					<c:set var="currentMenu" scope="request">
						<decorator:getProperty property="meta.menu"/>
					</c:set>
					<ul>
						<jsp:include page="/common/menu.jsp"/>
					</ul>
					<div class="clr"></div>
				</div>
		    	<div class="clr"></div>
			    <div class="header_img"><img src="images/logo.png" alt="" width="271" height="234" />
					<h2><s:text name="desglosa.fullname" /></h2>
					<p><strong><s:text name="desglosa.tagline" /></strong><br />
					<s:text name="desglosa.description" />
					</p>
					<div class="clr"></div>
			    </div>
			</div>
		</div>

		<div class="content">
			<div class="content_resize">
				<div class="<decorator:getProperty property="body.class" />">
            		<decorator:body/>
            	</div>
            	<div class="clr"></div>
                <hr />
			</div>
		</div>
 
		<div class="fbg">
			<div class="fbg_resize">
				<div class="col c1">
					<h2><span>Escuela Superior de Informática</span></h2>
					<p>Paseo de la Universidad, 4<br />
					13071, Ciudad Real<br />
					Teléfono: 926 29 53 00<br />
					Fax: 926 29 53 54<br />
					esi@uclm.es</p>
				</div>
				<div class="col c2">
					<h2><span>Mapa del sitio</span></h2>
					<p>more subsections</p>
				</div>
				<div class="col c3">
					<h2><span>Destacados</span></h2>
					<p><a href="http://innovation-labs.com/origin/" target="_blank">Proyecto ORIGIN</a><br />
					<a href="http://www.indracompany.com/sostenibilidad-e-innovacion/proyectos-innovacion/origin-organizaciones-inteligentes-globales-innovad" target="_blank">Proyectos de Innovación</a>
				</div>
				<div class="clr"></div>
			</div>
			<div class="footer">
				<s:url id="home" action=""/>
				
				<security:authorize ifNotGranted="ROLE_ANONYMOUS">
                    <p class="lf"><fmt:message key="label.loggedin"/>: <security:authentication property="principal.username"></security:authentication></p>
                    <div class="clr"></div>
                </security:authorize>
                <p class="lf">&copy; Copyright <s:a href="%{home}">Desglosa</s:a>.</p>
				
				<p class="rf">Layout by Cool <a href="http://www.coolwebtemplates.net/">Website Templates</a></p>
				<div class="clr"></div>
			</div>
		</div>
    </div>
</body>
</html>
