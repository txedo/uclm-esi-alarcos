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
	<display:table name="companies" id="company" cellspacing="0" cellpadding="0"
	    defaultsort="1" class="table" pagesize="50" requestURI="">
	  
	    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="user.username" sortable="true"/>
	        
	    <display:setProperty name="paging.banner.item_name" value="user" />
	    <display:setProperty name="paging.banner.items_name" value="users" />
	</display:table>
</body>
</html>