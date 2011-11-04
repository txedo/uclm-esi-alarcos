<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/jsp/dialogs.jsp' %>

<html lang="en">
<head>
	<sj:head jqueryui="true"/>
	
	<meta name="menu" content="ManageCompanies"/>
	
	<fmt:message key="error.company_not_selected" var="noCompanySelected"/>
	
	<SCRIPT type="text/javascript">
		function getSelectedRadioButton() {
			return $("input:radio[name=companyIds]:checked").val();
		}
		
		function call(urlAction) {
			if (isUndefined(getSelectedRadioButton())) {
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
	<h1><s:text name="menu.admin.companies" /></h1>
	<s:actionerror />
	<s:actionmessage />
	<s:set name="companies" value="companies" scope="request"/>  
	<display:table name="companies" uid="company" defaultsort="1" class="" pagesize="10" requestURI="">
	  	<display:column>
	  		<input type="radio" id="companyIdRadio" name="companyIds" value="${company.id}">
	  	</display:column>
	    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
	    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.company.information" sortable="false"/>
	    
	    <display:setProperty name="paging.banner.placement" value="top"/>
	    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
	    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
	    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
	</display:table>
	
	<div id="controls">
		<c:url var="view" value="/viewCompany"/>
		<a href="javascript:void(0)" onclick="javascript:call('<c:out value="${view}"/>')"><fmt:message key="button.view_company"/></a>
		<!-- TODO add security tag -->
		<c:url var="edit" value="/showCompanyForm"/>
		<a href="javascript:void(0)" onclick="javascript:call('<c:out value="${edit}"/>')"><fmt:message key="button.edit_company"/></a>
		<!-- TODO add security tag -->
		<c:url var="delete" value="/deleteCompany"/>
		<a href="javascript:void(0)" onclick="javascript:call('<c:out value="${delete}"/>')"><fmt:message key="button.delete_company"/></a>
		<!-- TODO add security tag -->
		<a href="<c:url value="/showCompanyForm"/>"><fmt:message key="button.add_company"/></a>
	</div>
</body>
</html>