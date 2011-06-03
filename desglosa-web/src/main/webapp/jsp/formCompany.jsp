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
	<s:form id="formCompany" method="post" action="%{#attr.form}">
		<c:if test="${not empty param.id}">
			<s:hidden name="company.id"/>
		</c:if>
		<s:textfield id="company.name" name="company.name" tabindex="1" label="%{getText('label.company.name')}"/>
		<s:textarea id="company.information" name="company.information" tabindex="2" cols="15" rows="3" label="%{getText('label.company.information')}"/>
		<s:submit value="%{getText(#attr.buttonLabel)}" tabindex="3"></s:submit>
	</s:form>
</body>
</html>