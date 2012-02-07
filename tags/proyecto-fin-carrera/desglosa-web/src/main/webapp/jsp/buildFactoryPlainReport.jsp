<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Project"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<div id="plainReport">

    <s:actionerror />
    <s:actionmessage />
    
    <s:if test="!hasActionErrors()">
        <s:set name="factory" value="factory" scope="request"/> 
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.factory.data"/></h3>
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
                <li>
                    <label class="key"><s:text name="label.market.name"/></label>
                    <label class="value"><c:out value='${factory.mostRepresentativeMarket.name}'/></label>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.factory.company"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.company.name"/></label>
                    <label class="value"><s:text name="factory.company.name"/></label>
                </li>
            </ul>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.factory.location"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.configure.factory.address.city"/></label>
                    <label class="value"><s:text name="factory.address.city"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.configure.factory.address.country"/></label>
                    <label class="value"><s:text name="factory.address.country"/></label>
                </li>
                <li>
                    <label class="key" />
                    <img class="framed" style="float:left;" src="http://maps.google.com/maps/api/staticmap?zoom=10&size=270x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${factory.location.latitude}'/>,<c:out value='${factory.location.longitude}'/>&sensor=false" title="<s:text name='label.configure.factory.address.image'/>"/>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.director"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.configure.director.name"/></label>
                    <label class="value"><s:text name="factory.director.lastName"/>, <s:text name="factory.director.name"/></label>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.factory.measures"/></h3>
            <%@ include file="/jsp/generateFactoryMeasureView.jsp"%>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.factory.projects.title"/></h3>
            
            <div class="displaytagTable">
                <display:table name="factory.projects" uid="project" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.project.name" sortable="false"/>
                    <display:column property="code" escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.code" sortable="false"/>
                    <display:column property="plan" escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.plan" sortable="false"/>
                    <display:column property="market.name" escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.market.name" sortable="false"/>
                    <display:column escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.subprojects" sortable="false"><%=((Project)project).getSubprojects().size()%></display:column>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="label.project"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="label.projects"/></display:setProperty>
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
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.factory.subprojects.title"/></h3>
            
            <div class="displaytagTable">
                <display:table name="factory.subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column property="name" escapeXml="true" style="width: 10%" titleKey="table.header.subproject.name" sortable="true"/>
                    <display:column property="project.name" escapeXml="true" style="width: 10%" titleKey="table.header.project.name" sortable="true"/>
					<display:column escapeXml="false" style="width: 8%; text-align: center;" titleKey="table.header.project.subprojects" sortable="true">
                        <%=((Subproject)subproject).getProject().getSubprojects().size() %>
                    </display:column>
                    <display:column property="project.mainFactory.name" escapeXml="true" style="width: 12%; text-align: center;" titleKey="table.header.subproject.project.factory" sortable="true"/>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="label.subproject"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="label.subprojects"/></display:setProperty>
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
        </fieldset>
    </s:if>
</div>
