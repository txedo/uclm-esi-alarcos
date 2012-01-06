<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>	
    <sj:head jqueryui="true"/>
    
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
                    <h2><span>Información de contacto</span></h2>
                    <p>Jose Domingo L&oacute;pez L&oacute;pez<br />
                    Ingeniero Técnico en Informática de Sistemas<br />
                    <br />
					Grupo de Investigación Alarcos<br />
					Escuela Superior de Informática. Ciudad Real<br />
					Teléfono:  (+34) 926 295300 ext.6480<br />
					lopez.josedomingo [AT] gmail [DOT] com
					</p>
				</div>
				<div class="col c2">
                    <h2><span>Escuela Superior de Informática</span></h2>
                    <p>Paseo de la Universidad, 4<br />
                    13071, Ciudad Real<br />
                    Teléfono: 926 29 53 00<br />
                    Fax: 926 29 53 54<br />
                    esi [AT] uclm [DOT] es</p>
				</div>
				<div class="col c3">
					<h2><span>Destacados</span></h2>
					<p><a href="http://innovation-labs.com/origin/" target="_blank">Proyecto ORIGIN</a><br />
					<a href="http://www.indracompany.com/sostenibilidad-e-innovacion/proyectos-innovacion/origin-organizaciones-inteligentes-globales-innovad" target="_blank">Proyectos de Innovación</a><br />
					<a href="http://alarcos.uclm.es" target="_blank">Grupo de investigación Alarcos</a><br />
					<a href="http://www.esi.uclm.es" target="_blank">Escuela Superior de Informática (UCLM)</a><br />
					<a href="http://www.uclm.es" target="_blank">Universidad de Castilla-La Mancha</a><br />
					</p>
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
