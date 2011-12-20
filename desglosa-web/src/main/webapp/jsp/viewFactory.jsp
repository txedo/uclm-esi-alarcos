<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
    <head>
        <sj:head jqueryui="true"/>
    
        <meta name="menu" content="ManageFactories"/>
        
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
        </SCRIPT>
    </head>
    <body id="viewCompany">
        <h1><s:text name="management.factory.view.title" /></h1>
        <p><s:text name="management.factory.view.text" /></p>
        <s:actionerror />
        <s:actionmessage />
        
        <s:if test="!hasActionErrors()">
            <s:set name="factory" value="factory" scope="request"/> 
            
            <div class="form">
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.factory.company"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.company.name"/></label>
                            <label class="value"><s:text name="factory.company.name"/></label>
                        </li>
                    </ul>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.factory.data"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.factory.name"/></label>
                            <label class="value"><s:text name="factory.name"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.factory.information"/></label>
                            <label class="value"><s:text name="factory.information"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.factory.email"/></label>
                            <label class="value"><s:text name="factory.email"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.factory.employees"/></label>
                            <label class="value"><s:text name="factory.employees"/></label>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.factory.location"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.configure.factory.address.address"/></label>
                            <label class="value"><s:text name="factory.address.address"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.factory.address.city"/></label>
                            <label class="value"><s:text name="factory.address.city"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.factory.address.province"/></label>
                            <label class="value"><s:text name="factory.address.province"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.factory.address.country"/></label>
                            <label class="value"><s:text name="factory.address.country"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.factory.address.postal_code"/></label>
                            <label class="value"><s:text name="factory.address.postalCode"/></label>
                        </li>
                        <li>
                            <label class="key" />
                            <img style="float:left;" src="http://maps.google.com/maps/api/staticmap?zoom=10&size=256x256&maptype=roadmap&markers=color:red|color:red|<c:out value='${factory.location.latitude}'/>,<c:out value='${factory.location.longitude}'/>&sensor=false" width="256" height="256" title="<s:text name='label.configure.factory.address.image'/>"/>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.director"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.configure.director.name"/></label>
                            <label class="value"><s:text name="factory.director.name"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.director.last_name"/></label>
                            <label class="value"><s:text name="factory.director.lastName"/></label>
                        </li>
                        <li>
                            <label class="key"><s:text name="label.configure.director.image"/></label>
                            <img style="float:left;" src="<s:text name='factory.director.imagePath'/>" width="128" height="128" title="<s:text name='label.configure.director.current_image'/>"/>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
            </div>
            
            <div class="buttonPane">
                <!-- TODO add security tag -->
                <c:url var="edit" value="/showFactoryForm">
                    <c:param name="id">${param.id}</c:param>
                </c:url>
                <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_factory"/></button>
            </div>
            
            <fieldset class="viewingfieldset">
                <h2><s:text name="label.configure.projects"/></h2>
                <display:table name="factory.projects" uid="project" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column  style="width: 5%">
                        <input type="radio" id="projectIdRadio" name="projectIds" value="${project.id}">
                    </display:column>
                    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.project.name" sortable="true"/>
                    <display:column property="code" escapeXml="true" style="width: 55%" titleKey="table.header.project.code" sortable="false"/>
                    <display:column property="plan" escapeXml="true" style="width: 55%" titleKey="table.header.project.plan" sortable="false"/>
                    <display:column property="market" escapeXml="true" style="width: 55%" titleKey="table.header.project.market" sortable="false"/>
                    <display:column escapeXml="true" style="width: 10%" titleKey="table.header.project.subprojects" sortable="true"><%=((Project)project).getSubprojects().size()%></display:column>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.project"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.projects"/></display:setProperty>
                    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
                </display:table>
                <div class="buttonPane">
                    <!-- TODO add security tag -->
                    <c:url var="view" value="/viewProject"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_project"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="edit" value="/showProjectForm"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_project"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="delete" value="/deleteProjecty"/>
                    <button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_project"/></button>
                    <!-- TODO add security tag -->
                    <c:url var="add" value="/showProjectForm">
                        <c:param name="companyId">${param.id}</c:param>
                    </c:url>
                    <button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_project"/></button>
                </div>
            </fieldset>
        </s:if>
    </body>
</html>