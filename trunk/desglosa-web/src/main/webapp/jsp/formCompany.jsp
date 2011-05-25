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
	<form id="formCompany" method="post" action="<c:url value="${form}"/>">
	<ul>
		<li>
			<label for="companyName"><fmt:message key="label.company.name"/></label>
			<s:textfield id="companyName" name="name" tabindex="1"/>
		</li>
		<li>
			<label for="companyInformation"><fmt:message key="label.company.information"/></label>
			<s:textarea id="companyInformation" name="information" tabindex="2" cols="15" rows="3"/>
		</li>
		<li>
			<c:if test="${not empty param.id}">
				<s:hidden name="id"/>
			</c:if>
			<input type="submit" name="addCompany" value="<fmt:message key="${buttonLabel}"/>" tabindex="3" />
		</li>
	</ul>
	</form>
</body>
</html>