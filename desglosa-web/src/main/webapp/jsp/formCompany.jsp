<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageCompanies"/>
</head>
<body>
	<s:text name="menu.admin.companies" />
	
	<s:actionerror />
	
	<c:set var="form" value="/editCompany.action"/>
	<c:set var="buttonLabel" value="button.edit_company"/>
	<c:if test="${empty param.id}">
		<c:set var="form" value="/saveCompany.action"/>
		<c:set var="buttonLabel" value="button.add_company"/>
	</c:if>
	<form id="formCompany" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
		<c:if test="${not empty param.id}">
			<s:hidden name="company.id"/>
		</c:if>
		<br /><s:label for="company.name" value="%{getText('label.company.name')}:"/>
		<s:textfield id="company.name" name="company.name" tabindex="1"/>
		<s:fielderror><s:param>error.company.name</s:param></s:fielderror>
		
		<br /><s:label for="company.information" value="%{getText('label.company.information')}:"/>
		<s:textarea id="company.information" name="company.information" tabindex="2" cols="15" rows="3"/>
		<s:fielderror><s:param>error.company.information</s:param></s:fielderror>
		
		<br />
		<br /><s:text name="%{getText('label.configure.director')}:"/>

		<br /><s:label for="company.director.name" value="%{getText('label.configure.director.name')}:"/>
		<s:textfield id="company.director.name" name="company.director.name" tabindex="3"/>
		<s:fielderror><s:param>error.director.name</s:param></s:fielderror>
		<br /><s:label for="company.director.lastName" value="%{getText('label.configure.director.last_name')}:"/>
		<s:textfield id="company.director.lastName" name="company.director.lastName" tabindex="4"/>
		<s:fielderror><s:param>error.director.lastName</s:param></s:fielderror>
		<s:set name="company" value="company" scope="request"/>
		<c:if test="${not empty company}">
			<c:if test="${not empty company.director.imagePath}">
				<br /><s:label for="company.director.image" value="%{getText('label.configure.director.current_image')}:"/>
				<img src="<s:text name='company.director.imagePath'/>" width="128" height="128" alt="%{getText('label.configure.director.current_image')}"/>
			</c:if>
		</c:if>
		<br /><s:label for="company.director.image" value="%{getText('label.configure.director.image')}:"/>
		<s:file id="company.director.image" name="upload"></s:file>
		<s:fielderror><s:param>error.director.image</s:param></s:fielderror>
		
		<br /><s:submit value="%{getText(#attr.buttonLabel)}" tabindex="3"></s:submit>
	</form>
</body>
</html>