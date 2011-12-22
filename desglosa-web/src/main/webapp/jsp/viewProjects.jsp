<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
    <sj:head jqueryui="true" />
    <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
    
    <meta name="menu" content="ManageProjects"/>
    
    <fmt:message key="error.project_not_selected" var="noProjectSelected"/>
    
    <SCRIPT type="text/javascript">
        function getSelectedRadioButton() {
            return $("input:radio[name=projectIds]:checked").val();
        }
        
        function call(urlAction,selectionRequired) {
            if (!selectionRequired) {
                $(location).attr('href',urlAction);
            } else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
                $("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noProjectSelected}'/></p>");
                $("#errorDialog").dialog("open");
            } else {
                var url = urlAction+"?id="+getSelectedRadioButton();
                $(location).attr('href',url);
            }
        }
        
        $(document).ready(function() {
            $("div.tooltipstyle").tooltip({
                position: 'top center',
                delay: 0
                });
        });
    </SCRIPT>
</head>
<body id="viewProjects">
    <h1><s:text name="management.project.list.title" /></h1>
    <p><s:text name="management.project.list.text" /></p>
    
    <s:actionerror />
    <s:actionmessage />
    
    <s:set name="projects" value="projects" scope="request"/>  
    <display:table name="projects" uid="project" defaultsort="1" class="" pagesize="10" requestURI="">
        <display:column  style="width: 5%">
            <input type="radio" id="projectIdRadio" name="projectIds" value="${project.id}">
        </display:column>
        <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.project.name" sortable="true"/>
        <display:column property="code" escapeXml="true" style="width: 15%" titleKey="table.header.project.code" sortable="true"/>
        <display:column property="plan" escapeXml="true" style="width: 15%" titleKey="table.header.project.plan" sortable="true"/>
        <display:column escapeXml="false" style="width: 20%" titleKey="table.header.market.name" sortable="true">
            <span class="icon" style="background-color:#<%=((Project)project).getMarket().getColor()%>"></span><%=((Project)project).getMarket().getName()%>
        </display:column>
        <display:column escapeXml="false" style="width: 20%" titleKey="table.header.factory.name" sortable="true">
            <div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${project.mainFactory.location.latitude}'/>,<c:out value='${project.mainFactory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img class="searchIcon" src="images/world_search.png" height="16" width="16" /><c:out value="${project.mainFactory.name}"/></div>
        </display:column>
        <display:column escapeXml="true" style="width: 10%" titleKey="table.header.project.subprojects" sortable="true"><%=((Project)project).getSubprojects().size()%></display:column>
        
        <display:setProperty name="paging.banner.placement" value="top"/>
        <display:setProperty name="paging.banner.item_name"><fmt:message key="label.project"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="label.projects"/></display:setProperty>
        <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
        <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
        <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
        <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
    </display:table>
    
    <div class="buttonPane">
        <c:url var="view" value="/viewProject"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_project"/></button>
        <!-- TODO add security tag -->
        <c:url var="edit" value="/showProjectForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_project"/></button>
        <!-- TODO add security tag -->
        <c:url var="delete" value="/deleteProject"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_project"/></button>
        <!-- TODO add security tag -->
        <c:url var="add" value="/showProjectForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_project"/></button>
    </div>
</body>
</html>