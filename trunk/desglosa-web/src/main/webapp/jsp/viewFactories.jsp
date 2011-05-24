<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/common/taglibs.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="menu" content="ViewFactories"/>
</head>
<body>
	<s:text name="menu.admin.factories" />
	<s:actionerror />
	<s:actionmessage />
	<display:table name="requestScope.factories" id="factory" cellspacing="0" cellpadding="0"
	    defaultsort="1" class="" pagesize="50" requestURI="">
	  
	    <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.factory.name" sortable="true"/>
	    <display:column property="company.name" escapeXml="true" style="width: 15%" titleKey="table.header.company.name" sortable="true"/>
	    <display:column property="information" escapeXml="true" style="width: 15%" titleKey="table.header.factory.information" sortable="false"/>
	    <display:column property="email" escapeXml="true" style="width: 15%" titleKey="table.header.factory.email" sortable="false"/>
	    <display:column property="employees" escapeXml="true" style="width: 15%" titleKey="table.header.factory.employees" sortable="true"/>
	    
	    <display:column href="showMore" style="width: 5%" paramId="id" paramProperty="id">+</display:column>
	    <display:column href="showFactoryForm.action" style="width: 5%" paramId="id" paramProperty="id">edit</display:column>
	    <display:column href="deleteFactory.action" style="width: 5%" paramId="id" paramProperty="id">delete</display:column>
	    
	    <display:setProperty name="paging.banner.placement" value="top"/>
	    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.factory"/></display:setProperty>
	    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.factories"/></display:setProperty>
	    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	</display:table>
	<!-- TODO add security tag -->
	<a href="<c:url value="/showFactoryForm.action"/>"><fmt:message key="button.add_factory"/></a>
</body>
</html>