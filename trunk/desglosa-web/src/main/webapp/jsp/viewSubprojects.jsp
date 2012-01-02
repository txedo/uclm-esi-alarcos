<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
    <sj:head jqueryui="true" />
    <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="js/utils.js?version=1"></script>
    
    <meta name="menu" content="ManageSubprojects"/>
    
    <fmt:message key="error.subproject_not_selected" var="noSubprojectSelected"/>
    
    <SCRIPT type="text/javascript">
        function getSelectedRadioButton() {
            return $("input:radio[name=subprojectIds]:checked").val();
        }
        
        function call(urlAction,selectionRequired) {
            if (!selectionRequired) {
                $(location).attr('href',urlAction);
            } else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
                $("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noSubprojectSelected}'/></p>");
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
    <h1><s:text name="management.subproject.list.title" /></h1>
    <p><s:text name="management.subproject.list.text" /></p>
    
    <s:actionerror />
    <s:actionmessage />
    
    <div class="displaytagTable">
	    <s:set name="subprojects" value="subprojects" scope="request"/>  
	    <display:table name="subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
	        <display:column  style="width: 5%; text-align: center;">
	            <input type="radio" id="subprojectIdRadio" name="subprojectIds" value="${subproject.id}">
	        </display:column>
	        <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.subproject.name" sortable="true"/>
	        <display:column property="project.name" escapeXml="true" style="width: 15%" titleKey="table.header.project.name" sortable="true"/>
	        <display:column escapeXml="false" style="width: 15%" titleKey="table.header.project.market" sortable="true">
	            <script>
	            var span = getMarketSpan('${subproject.project.market.color}', '${subproject.project.market.name}');
	            $("td:last").append(span);
	            </script>
	        </display:column>
	        <display:column escapeXml="false" style="width: 15%" titleKey="table.header.factory.name" sortable="true">
	            <div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${subproject.factory.location.latitude}'/>,<c:out value='${subproject.factory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img class="searchIcon" src="images/world_search.png" height="16" width="16" /><c:out value="${subproject.factory.name}"/></div>
	        </display:column>
	        <display:column property="factory.address.city" escapeXml="true" style="width: 10%" titleKey="table.header.address.city" sortable="true"/>
	        <display:column property="factory.address.country" escapeXml="true" style="width: 10%" titleKey="table.header.address.country" sortable="true"/>
	        
	        <display:setProperty name="paging.banner.placement" value="top"/>
	        <display:setProperty name="paging.banner.item_name"><fmt:message key="label.subproject"/></display:setProperty>
	        <display:setProperty name="paging.banner.items_name"><fmt:message key="label.subprojects"/></display:setProperty>
            <display:setProperty name="paging.banner.page.separator"><fmt:message key="table.paging.banner.page.separator"/></display:setProperty>
            <display:setProperty name="basic.msg.empty_list"><fmt:message key="table.paging.banner.no_items_found_male"><fmt:param><fmt:message key="label.profile"/></fmt:param></fmt:message></display:setProperty>
            <display:setProperty name="paging.banner.no_items_found"><fmt:message key="table.paging.banner.no_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.one_item_found"><fmt:message key="table.paging.banner.one_item_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found"><fmt:message key="table.paging.banner.all_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found"><fmt:message key="table.paging.banner.some_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.full"><fmt:message key="table.paging.banner.full"/></display:setProperty>
            <display:setProperty name="paging.banner.first"><fmt:message key="table.paging.banner.first"/></display:setProperty>
            <display:setProperty name="paging.banner.last"><fmt:message key="table.paging.banner.last"/></display:setProperty>
	    </display:table>
	</div>
    
    <div class="buttonPane">
        <c:url var="view" value="/viewSubproject"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_subproject"/></button>
        <!-- TODO add security tag -->
        <c:url var="edit" value="/showSubprojectForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_subproject"/></button>
        <!-- TODO add security tag -->
        <c:url var="delete" value="/deleteSubproject"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_subproject"/></button>
        <!-- TODO add security tag -->
        <c:url var="add" value="/showSubprojectForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_subproject"/></button>
    </div>
</body>
</html>