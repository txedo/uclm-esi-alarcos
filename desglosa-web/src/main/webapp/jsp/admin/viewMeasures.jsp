<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.Map"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
    <sj:head jqueryui="true"/>
    
    <meta name="menu" content="ManageMeasures"/>
    
    <fmt:message key="error.measure_not_selected" var="noMeasureSelected"/>
    <fmt:message key="message.delete_measure_confirmation" var="deleteMeasureConfirmation"/>
    
    <SCRIPT type="text/javascript">
        function getSelectedRadioButton() {
            return $("input:radio[name=measureId]:checked").val();
        }
        
        function call(urlAction,selectionRequired) {
            if (!selectionRequired) {
                $(location).attr('href',urlAction);
            } else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
                $("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noMeasureSelected}'/></p>");
                $("#errorDialog").dialog("open");
            } else {
                var url = urlAction+"?id="+getSelectedRadioButton();
                if (urlAction.indexOf('delete') != -1) {
                    if (confirm("<c:out value='${deleteMeasureConfirmation}'/>")) {
                        $(location).attr('href',url);
                    }
                } else {
                    $(location).attr('href',url);
                }
            }
        }
    </SCRIPT>
</head>
<body id="viewCompanies">
    <h1><s:text name="management.measure.list.title" /></h1>
    <p><s:text name="management.measure.list.text" /></p>
    <s:actionerror />
    <s:actionmessage />
    
    <c:if test='${not empty param.add and param.add == "success"}'>
        <ul class='actionMessage'><li><span><s:text name="message.measure.created_successfully"/></span></li></ul>
    </c:if>
    
    <div class="displaytagTable">
        <s:set name="measures" value="measures" scope="request"/>
        <display:table name="measures" uid="measure" defaultsort="2" class="" pagesize="10" requestURI="" >
            <display:column style="width: 3%; text-align: center;">
                <input type="radio" id="measureIdRadio" name="measureId" value='${measure.id}'>
            </display:column>
            <display:column property="entity" escapeXml="true" style="width: 20%" titleKey="table.header.measure.entity" sortable="true"/>
            <display:column property="name" escapeXml="true" style="width: 20%" titleKey="table.header.measure.name" sortable="true"/>
            <display:column property="type" escapeXml="true" style="width: 20%" titleKey="table.header.measure.type" sortable="false"/>
            
            <display:setProperty name="paging.banner.placement" value="top"/>
            <display:setProperty name="paging.banner.item_name"><fmt:message key="label.measure"/></display:setProperty>
            <display:setProperty name="paging.banner.items_name"><fmt:message key="label.measures"/></display:setProperty>
            <display:setProperty name="paging.banner.page.separator"><fmt:message key="table.paging.banner.page.separator"/></display:setProperty>
            <display:setProperty name="basic.msg.empty_list"><fmt:message key="table.paging.banner.no_items_found"><fmt:param><fmt:message key="label.profile"/></fmt:param></fmt:message></display:setProperty>
            <display:setProperty name="paging.banner.no_items_found"><fmt:message key="table.paging.banner.no_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.one_item_found"><fmt:message key="table.paging.banner.one_item_found"/></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found"><fmt:message key="table.paging.banner.all_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found"><fmt:message key="table.paging.banner.some_items_found"/></display:setProperty>
            <display:setProperty name="paging.banner.onepage"><fmt:message key="table.paging.banner.onepage"/></display:setProperty>
            <display:setProperty name="paging.banner.full"><fmt:message key="table.paging.banner.full"/></display:setProperty>
            <display:setProperty name="paging.banner.first"><fmt:message key="table.paging.banner.first"/></display:setProperty>
            <display:setProperty name="paging.banner.last"><fmt:message key="table.paging.banner.last"/></display:setProperty>
        </display:table>
    </div>
    
    <div class="buttonPane">
<%-- 		<security:authorize ifAnyGranted="ROLE_ADMIN"> --%>
	        <c:url var="delete" value="/deleteMeasure"/>
	        <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_measure"/></button>
<%--         </security:authorize> --%>
<%--         <security:authorize ifAnyGranted="ROLE_ADMIN"> --%>
	        <c:url var="add" value="/showMeasureForm"/>
	        <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_measure"/></button>
<%-- 	    </security:authorize> --%>
    </div>
</body>
</html>