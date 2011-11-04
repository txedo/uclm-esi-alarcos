<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageCompanies"/>
	<link href="<s:url value='/styles/knowledgeForms.css?version=1'/>" rel="stylesheet" type="text/css" />
</head>
<body id="showCompany" class="">
	<div>
		<h1><s:text name="menu.admin.companies" /></h1>
		
		<s:actionerror />
		<s:actionmessage />
		
		<c:set var="form" value="/editCompany"/>
		<c:set var="buttonLabel"><s:text name="button.edit_company"></s:text></c:set>
		<c:if test="${empty param.id}">
			<c:set var="form" value="/saveCompany"/>
			<c:set var="buttonLabel"><s:text name="button.add_company"></s:text></c:set>
		</c:if>
		<form id="formCompany" class="form" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
			<c:if test="${not empty param.id}">
				<s:hidden name="company.id"/>
			</c:if>
			
			<fieldset id="companyForm">
				<legend><s:text name="label.configure.company"/></legend>
				<ul>
					<li>
						<label for="company.name"><s:text name="label.company.name"/></label>
						<s:textfield id="company.name" name="company.name" tabindex="1"/>
						<s:fielderror><s:param>error.company.name</s:param></s:fielderror>
					</li>
					<li>
						<label for="company.information"><s:text name="label.company.information"/></label>
						<s:textarea id="company.information" name="company.information" tabindex="2" cols="15" rows="3"/>
						<s:fielderror><s:param>error.company.information</s:param></s:fielderror>
					</li>
				</ul>
			</fieldset>
			
			<fieldset id="directorForm">
				<legend><s:text name="label.configure.director"/></legend>
				<ul>
					<li>
						<label for="company.director.name"><s:text name="label.configure.director.name"/></label>
						<s:textfield id="company.director.name" name="company.director.name" tabindex="3"/>
						<s:fielderror><s:param>error.director.name</s:param></s:fielderror>
					</li>
					<li>
						<label for="company.director.lastName"><s:text name="label.configure.director.last_name"/></label>
						<s:textfield id="company.director.lastName" name="company.director.lastName" tabindex="4"/>
						<s:fielderror><s:param>error.director.lastName</s:param></s:fielderror>
						
						<s:hidden id="company.director.imagePath" name="company.director.imagePath"/>
						<s:set name="company" value="company" scope="request"/>
						<c:if test="${not empty company.director.imagePath}">
							<li>
								<label for="company.director.image"><s:text name="label.configure.director.current_image"/></label>
								<img src="<s:text name='company.director.imagePath'/>" width="128" height="128" title="<s:text name='label.configure.director.current_image'/>"/>
							</li>
						</c:if>
					</li>
					<li>
						<label for="company.director.image"><s:text name="label.configure.director.image"/></label>
						<s:file id="company.director.image" name="upload"></s:file>
						<s:fielderror><s:param>error.director.image</s:param></s:fielderror>
					</li>
				</ul>
			</fieldset>
			
			<s:submit id="submit" value="%{getText(#attr.buttonLabel)}" tabindex="3"></s:submit>
		</form>
	</div>
</body>
</html>