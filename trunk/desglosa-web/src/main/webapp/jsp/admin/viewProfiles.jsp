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
    
    <meta name="menu" content="ViewProfiles"/>
    
    <fmt:message key="error.profile_not_selected" var="noProfileSelected"/>
    <fmt:message key="message.delete_profile_confirmation" var="deleteProfiletConfirmation"/>
    
    <SCRIPT type="text/javascript">
        function getSelectedRadioButton() {
            return $("input:radio[name=profileFilename]:checked").val();
        }
        
        function call(urlAction,selectionRequired) {
            if (!selectionRequired) {
                $(location).attr('href',urlAction);
            } else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
                $("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noProfileSelected}'/></p>");
                $("#errorDialog").dialog("open");
            } else {
                var url = urlAction+"?filename="+getSelectedRadioButton();
                if (urlAction.indexOf('delete') != -1) {
                    if (confirm("<c:out value='${deleteProfiletConfirmation}'/>")) {
                        $(location).attr('href',url);
                    }
                } else {
                    $(location).attr('href',url);
                }
            }
        }
        
        function viewProfile(urlAction) {
        	if (isUndefined(getSelectedRadioButton())) {
        		$("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noProfileSelected}'/></p>");
                $("#errorDialog").dialog("open");
        	} else {
        		var url = urlAction + "/" + getSelectedRadioButton();
                var winParams = "left=0,top=0,width=600,height=600,resizable=1,location=0,menubar=0,scrollbars=1,status=0,toolbar=0";
                openPopup(url, "", winParams);
        	}
        }
    </SCRIPT>
</head>
<body id="viewCompanies">
    <h1><s:text name="management.profile.list.title" /></h1>
    <p><s:text name="management.profile.list.text" /></p>
    <s:actionerror />
    <s:actionmessage />
    
    <c:if test='${not empty param.add and param.add == "success"}'>
        <ul class='actionMessage'><li><span><s:text name="message.profile.created_successfully"/></span></li></ul>
    </c:if>
    
    <div class="displaytagTable">
        <s:set name="profiles" value="profiles" scope="request"/>
        <s:set name="entities" value="entities" scope="request"/>
        <s:set name="models" value="models" scope="request"/>
        <display:table name="profiles" uid="profile" defaultsort="1" class="" pagesize="10" requestURI="" >
            <display:column style="width: 5%; text-align: center;">
                <input type="radio" id="profileFilenameRadio" name="profileFilename" value='${profile.filename}'>
            </display:column>
            <display:column property="profile.name" escapeXml="true" style="width: 20%" titleKey="table.header.profile.name" sortable="true"/>
            <display:column property="profile.description" escapeXml="true" style="width: 30%" titleKey="table.header.profile.description" sortable="false"/>
            <display:column escapeXml="false" style="width: 25%" titleKey="table.header.profile.entity" sortable="true">
                <c:out value="${entities[profile.profile.entityName]}"></c:out>
            </display:column>
            <display:column escapeXml="false" style="width: 25%" titleKey="table.header.profile.model" sortable="true">
                <c:out value="${models[profile.profile.modelName]}"></c:out>
            </display:column>
            
            <display:setProperty name="paging.banner.placement" value="top"/>
            <display:setProperty name="paging.banner.item_name"><fmt:message key="label.profile"/></display:setProperty>
            <display:setProperty name="paging.banner.items_name"><fmt:message key="label.profiles"/></display:setProperty>
            <display:setProperty name="paging.banner.page.separator"><fmt:message key="table.paging.banner.page.separator"/></display:setProperty>
            <display:setProperty name="basic.msg.empty_list"><fmt:message key="table.paging.banner.no_items_found_male"><fmt:param><fmt:message key="label.profile"/></fmt:param></fmt:message></display:setProperty>
            <display:setProperty name="paging.banner.no_items_found"><fmt:message key="table.paging.banner.no_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.one_item_found"><fmt:message key="table.paging.banner.one_item_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found"><fmt:message key="table.paging.banner.all_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found"><fmt:message key="table.paging.banner.some_items_found_male"/></display:setProperty>
            <display:setProperty name="paging.banner.onepage"><fmt:message key="table.paging.banner.onepage"/></display:setProperty>
            <display:setProperty name="paging.banner.full"><fmt:message key="table.paging.banner.full"/></display:setProperty>
            <display:setProperty name="paging.banner.first"><fmt:message key="table.paging.banner.first"/></display:setProperty>
            <display:setProperty name="paging.banner.last"><fmt:message key="table.paging.banner.last"/></display:setProperty>
        </display:table>
    </div>
    
    <div class="buttonPane">
        <c:url var="view" value="/profiles"/>
        <button class="minimal" onclick="javascript:viewProfile('<c:out value="${view}"/>')"><fmt:message key="button.view_profile"/></button>
        <!-- TODO add security tag -->
<%--         <c:url var="edit" value="/editProfile"/> --%>
<%--         <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_profile"/></button> --%>
        <!-- TODO add security tag -->
        <c:url var="delete" value="/deleteProfile"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_profile"/></button>
        <!-- TODO add security tag -->
        <c:url var="add" value="/showProfileForm"/>
        <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_profile"/></button>
    </div>
</body>
</html>