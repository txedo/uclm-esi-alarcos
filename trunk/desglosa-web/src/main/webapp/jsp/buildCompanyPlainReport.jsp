<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Factory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<div id="plainReport">

    <s:actionerror />
    <s:actionmessage />
    
    <s:if test="!hasActionErrors()">
        <s:set name="company" value="company" scope="request"/> 
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.company"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.company.name"/></label>
                    <label class="value"><s:text name="company.name"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.company.information"/></label>
                    <label class="value"><s:text name="company.information"/></label>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>

        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.director"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.configure.director.name"/></label>
                    <label class="value"><s:text name="company.director.lastName"/>, <s:text name="company.director.name"/></label>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.factories"/></h3>
            
            <div class="displaytagTable">
                <display:table name="company.factories" uid="factory" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column property="name" escapeXml="true" style="width: 20%" titleKey="table.header.factory.name" sortable="true"/>
                    <display:column escapeXml="true" style="width: 8%; text-align: center;" titleKey="table.header.factory.projects" sortable="true">
                    	<%=((Factory)factory).getProjects().size()%>
                    </display:column>
					<display:column escapeXml="true" style="width: 8%; text-align: center;" titleKey="table.header.factory.subprojects" sortable="true">
                    	<%=((Factory)factory).getSubprojects().size()%>
                    </display:column>
                    <display:column property="mostRepresentativeMarket.name" escapeXml="true" style="width: 11%" titleKey="table.header.market.name" sortable="true"/>
                    <display:column property="address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
                    <display:column property="address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="label.factory"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="label.factories"/></display:setProperty>
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
            
        </fieldset>
    </s:if>
</div>
