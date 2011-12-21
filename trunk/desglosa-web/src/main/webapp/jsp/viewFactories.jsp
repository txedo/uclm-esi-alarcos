<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Factory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
    <sj:head jqueryui="true"/>
    
	<meta name="menu" content="ManageFactories"/>
	
    <fmt:message key="error.factory_not_selected" var="noFactorySelected"/>
    
    <SCRIPT type="text/javascript">
        function getSelectedRadioButton() {
            return $("input:radio[name=factoryIds]:checked").val();
        }
        
        function call(urlAction,selectionRequired) {
            if (!selectionRequired) {
                $(location).attr('href',urlAction);
            } else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
                $("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noFactorySelected}'/></p>");
                $("#errorDialog").dialog("open");
            } else {
                var url = urlAction+"?id="+getSelectedRadioButton();
                $(location).attr('href',url);
            }
        }
    </SCRIPT>
</head>
<body id="viewFactories">
	<h1><s:text name="management.factory.list.title" /></h1>
	<p><s:text name="management.factory.list.text" /></p>
	
	<s:actionerror />
	<s:actionmessage />
	
	<s:set name="factories" value="factories" scope="request"/>  
	<display:table name="factories" uid="factory" defaultsort="1" class="" pagesize="10" requestURI="">
        <display:column  style="width: 5%">
            <input type="radio" id="factoryIdRadio" name="factoryIds" value="${factory.id}">
        </display:column>
	    <display:column property="name" escapeXml="true" style="width: 20%" titleKey="table.header.factory.name" sortable="true"/>
	    <display:column property="company.name" escapeXml="true" style="width: 20%" titleKey="table.header.company.name" sortable="true"/>
	    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.factory.information" sortable="false"/>
	    <display:column property="email" escapeXml="true" style="width: 15%" titleKey="table.header.factory.email" sortable="false"/>
	    <display:column property="employees" escapeXml="true" style="width: 5%" titleKey="table.header.factory.employees" sortable="true"/>
	    <display:column escapeXml="true" style="width: 10%" titleKey="table.header.factory.projects" sortable="true"><%=((Factory)factory).getProjects().size()%></display:column>
	    
	    <display:setProperty name="paging.banner.placement" value="top"/>
	    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.factory"/></display:setProperty>
	    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.factories"/></display:setProperty>
	    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
	    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
	</display:table>
	
    <div class="buttonPane">
        <c:url var="view" value="/viewFactory"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_factory"/></button>
        <!-- TODO add security tag -->
        <c:url var="edit" value="/showFactoryForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_factory"/></button>
        <!-- TODO add security tag -->
        <c:url var="delete" value="/deleteFactory"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_factory"/></button>
        <!-- TODO add security tag -->
        <c:url var="add" value="/showFactoryForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_factory"/></button>
    </div>
</body>
</html>