<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
	<sj:head jqueryui="true"/>
	
	<meta name="menu" content="ManageCompanies"/>
	
	<fmt:message key="error.company_not_selected" var="noCompanySelected"/>
	
	<SCRIPT type="text/javascript">
		function getSelectedRadioButton() {
			return $("input:radio[name=companyIds]:checked").val();
		}
		
		function call(urlAction,selectionRequired) {
			if (!selectionRequired) {
				$(location).attr('href',urlAction);
			} else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
				$("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noCompanySelected}'/></p>");
				$("#errorDialog").dialog("open");
			} else {
				var url = urlAction+"?id="+getSelectedRadioButton();
				$(location).attr('href',url);
			}
		}
	</SCRIPT>
</head>
<body id="viewCompanies">
	<h1><s:text name="management.company.list.title" /></h1>
	<p><s:text name="management.company.list.text" /></p>
	<s:actionerror  />
	<s:actionmessage  />
	
	<div class="displaytagTable">
		<s:set name="companies" value="companies" scope="request"/>  
		<display:table name="companies" uid="company" defaultsort="1" class="" pagesize="10" requestURI="">
		  	<display:column  style="width: 5%; text-align: center;">
		  		<input type="radio" id="companyIdRadio" name="companyIds" value="${company.id}">
		  	</display:column>
		    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
		    <display:column property="information" escapeXml="true" style="width: 55%" titleKey="table.header.company.information" sortable="false"/>
		    <display:column escapeXml="true" style="width: 15%; text-align: center;" titleKey="table.header.company.factories" sortable="true"><%=((Company)company).getFactories().size()%></display:column>
		    
		    <display:setProperty name="paging.banner.placement" value="top"/>
		    <display:setProperty name="paging.banner.item_name"><fmt:message key="label.company"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><fmt:message key="label.companies"/></display:setProperty>
            <display:setProperty name="paging.banner.page.separator"><fmt:message key="table.paging.banner.page.separator"/></display:setProperty>
            <display:setProperty name="basic.msg.empty_list"><fmt:message key="table.paging.banner.no_items_found_male"><fmt:param><fmt:message key="label.profile"/></fmt:param></fmt:message></display:setProperty>
            <display:setProperty name="paging.banner.no_items_found"><fmt:message key="table.paging.banner.no_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.one_item_found"><fmt:message key="table.paging.banner.one_item_found"/></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found"><fmt:message key="table.paging.banner.all_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found"><fmt:message key="table.paging.banner.some_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.full"><fmt:message key="table.paging.banner.full"/></display:setProperty>
            <display:setProperty name="paging.banner.first"><fmt:message key="table.paging.banner.first"/></display:setProperty>
            <display:setProperty name="paging.banner.last"><fmt:message key="table.paging.banner.last"/></display:setProperty>
		</display:table>
	</div>
	
	<div class="buttonPane">
		<c:url var="view" value="/viewCompany"/>
		<button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_company"/></button>
		<!-- TODO add security tag -->
		<c:url var="edit" value="/showCompanyForm"/>
		<button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_company"/></button>
		<!-- TODO add security tag -->
		<c:url var="delete" value="/deleteCompany"/>
		<button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_company"/></button>
		<!-- TODO add security tag -->
		<c:url var="add" value="/showCompanyForm"/>
		<button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_company"/></button>
	</div>
</body>
</html>