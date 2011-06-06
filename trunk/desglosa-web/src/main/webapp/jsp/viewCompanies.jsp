<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ViewCompanies"/>
</head>
<body>
	<s:text name="menu.admin.companies" />
	<s:actionerror />
	<s:actionmessage />
	<s:set name="companies" value="companies" scope="request"/>  
	<display:table name="companies" uid="company" defaultsort="1" class="" pagesize="10" requestURI="">
	  
	    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
	    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.company.information" sortable="false"/>
	    
	    <display:column href="showCompanyForm.action" style="width: 5%" paramId="id" paramProperty="id">edit</display:column>
	    <display:column href="deleteCompany.action" style="width: 5%" paramId="id" paramProperty="id">delete</display:column>
	    
	    <display:setProperty name="paging.banner.placement" value="top"/>
	    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
	    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
	    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
	</display:table>
	<!-- TODO add security tag -->
	<a href="<c:url value="/showCompanyForm.action"/>"><fmt:message key="button.add_company"/></a>
</body>
</html>