<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
    <head>
        <sj:head jqueryui="true"/>
    
        <meta name="menu" content="ManageFactories"/>
        
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
        </SCRIPT>
    </head>
    <body id="viewCompany">
        <h1><s:text name="management.project.view.title" /></h1>
        <p><s:text name="management.project.view.text" /></p>
        <s:actionerror />
        <s:actionmessage />
        
        <s:if test="!hasActionErrors()">
            <s:set name="project" value="project" scope="request"/> 
            
            <div class="form">
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.project.details"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.project.name"/></label>
                            <label class="value"><s:text name="project.name"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.project.code"/></label>
                            <label class="value"><s:text name="project.code"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.project.plan"/></label>
                            <label class="value"><s:text name="project.plan"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.market.name"/></label>
                            <label class="value"><s:text name="project.market.name"/><span class="icon" style="background-color:#<s:text name='project.market.color'/>"></span></label>
                        </li>
                    </ul>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.project.mainFactory"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.factory.name"/></label>
                            <label class="value"><s:text name="project.mainFactory.name"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.factory.information"/></label>
                            <label class="value"><s:text name="project.mainFactory.information"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.company.name"/></label>
                            <label class="value"><s:text name="project.mainFactory.company.name"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.factory.email"/></label>
                            <label class="value"><s:text name="project.mainFactory.email"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.market.name"/></label>
                            <label class="value"><s:text name="project.mainFactory.mostRepresentativeMarket.name"/><span class="icon" style="background-color:#<s:text name='project.mainFactory.mostRepresentativeMarket.color'/>"></span></label>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
                
            </div>
            
            <div class="buttonPane">
                <!-- TODO add security tag -->
                <c:url var="edit" value="/showProjectForm">
                    <c:param name="id">${param.id}</c:param>
                </c:url>
                <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_project"/></button>
            </div>
            
            <fieldset class="viewingfieldset">
                <h2><s:text name="label.configure.subprojects"/></h2>
                <display:table name="project.subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column  style="width: 5%">
                        <input type="radio" id="subprojectIdRadio" name="subprojectIds" value="${subproject.id}">
                    </display:column>
                    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.subproject.name" sortable="true"/>
                    <display:column property="factory.name" escapeXml="true" style="width: 55%" titleKey="table.header.factory.name" sortable="false"/>
                    <display:column property="plan" escapeXml="true" style="width: 55%" titleKey="table.header.project.plan" sortable="false"/>
                    <display:column property="market" escapeXml="true" style="width: 55%" titleKey="table.header.project.market" sortable="false"/>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.subproject"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="submessage.projects"/></display:setProperty>
                    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
                </display:table>
                <div class="buttonPane">
                    <!-- TODO add security tag -->
                    <c:url var="view" value="/viewSubproject"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_subproject"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="edit" value="/showSubprojectForm"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_subproject"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="delete" value="/deleteSubprojecty"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_subproject"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="add" value="/showSubprojectForm">
                        <c:param name="companyId">${param.id}</c:param>
                    </c:url>
                    <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_subproject"/></button>
                </div>
            </fieldset>
        </s:if>
    </body>
</html>