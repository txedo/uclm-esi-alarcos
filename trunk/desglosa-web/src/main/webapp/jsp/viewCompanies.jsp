<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/common/taglibs.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="menu" content="ViewCompanies"/>
</head>
<body>
	<s:text name="menu.admin.companies" />
	</br>
	<display:table name="requestScope.companies" id="company" cellspacing="0" cellpadding="0"
	    defaultsort="1" class="" pagesize="50" requestURI="">
	  
	    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
	    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.company.information" sortable="false"/>
	    
	    <display:column href="editCompany.action" style="width: 5%" paramId="id" paramProperty="id">edit</display:column>
	    <display:column href="deleteCompany.action" style="width: 5%" paramId="id" paramProperty="id">delete</display:column>
	    
	    <display:setProperty name="paging.banner.placement" value="top"/>
	    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
	    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
	    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	</display:table>
	<!-- TODO add security tag -->
	<a href="<c:url value="/addCompany.action"/>"><fmt:message key="button.add_company"/></a>
</body>
</html>