<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Measure"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageMeasures"/>
	<link href="<s:url value='/styles/knowledgeForms.css?version=1'/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/utils.js?version=1"></script>
</head>
<body id="showCompany" class="">
    <a href="javascript:void(0)" onclick="javascript:goBack()" title="<s:text name='label.go_back'/>">&lt; <s:text name='label.go_back'/></a>
    
	<div>
		<h1><s:text name="management.measure.add.title" /></h1>
		<p><s:text name="management.measure.add.text" /></p>
		
		<s:actionerror />
		<s:actionmessage />
		
		<c:set var="form" value="/saveMeasure"/>
		<c:set var="buttonLabel"><s:text name="button.add_measure"></s:text></c:set>

		<form id="formMeasure" class="form" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
			<fieldset id="measureForm" class="formfieldset">
				<h2><s:text name="label.configure.measure"/></h2>
				<ul>
					<li>
						<label for="measure.entity"><s:text name="label.measure.entity"/> (*)</label>
						<s:select id="measure.entity" list="entities" name="measure.entity" tabindex="1"></s:select>
						<s:fielderror><s:param>error.measure.entity</s:param></s:fielderror>
					</li>
					<li>
						<label for="measure.name"><s:text name="label.measure.name"/> (*)</label>
						<s:textfield id="measure.name" name="measure.name" tabindex="2"/>
						<s:fielderror><s:param>error.measure.name</s:param></s:fielderror>
					</li>
					<li>
						<label for="measure.type"><s:text name="label.measure.type"/> (*)</label>
						<s:select id="measure.type" list="types" name="measure.type" tabindex="3"/>
						<s:fielderror><s:param>error.measure.type</s:param></s:fielderror>
					</li>
				</ul>
			</fieldset>

			<s:submit id="submit" value="%{getText(#attr.buttonLabel)}" tabindex="3"></s:submit>
		</form>
	</div>
</body>
</html>