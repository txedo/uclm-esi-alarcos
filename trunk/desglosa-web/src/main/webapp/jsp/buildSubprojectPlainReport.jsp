<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<div id="plainReport">

    <s:actionerror />
    <s:actionmessage />
    
    <s:if test="!hasActionErrors()">
        <s:set name="subproject" value="subproject" scope="request"/> 
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.subproject.details"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.subproject.name"/></label>
                    <label class="value"><s:text name="subproject.name"/></label>
                </li>
            </ul>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.subproject.project"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.project.name"/></label>
                    <label class="value"><s:text name="subproject.project.name"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.project.code"/></label>
                    <label class="value"><s:text name="subproject.project.code"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.project.plan"/></label>
                    <label class="value"><s:text name="subproject.project.plan"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.market.name"/></label>
                    <label class="value"><c:out value='${subproject.project.market.name}'/></label>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.subproject.factory"/></h3>
            <ul>
                <li>
                    <label class="key"><s:text name="label.factory.name"/></label>
                    <label class="value"><s:text name="subproject.factory.name"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.factory.information"/></label>
                    <label class="value"><s:text name="subproject.factory.information"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.company.name"/></label>
                    <label class="value"><s:text name="subproject.factory.company.name"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.factory.email"/></label>
                    <label class="value"><s:text name="subproject.factory.email"/></label>
                </li>
                <li>
                    <label class="key"><s:text name="label.market.name"/></label>
                    <label class="value"><c:out value='${subproject.factory.mostRepresentativeMarket.name}'/></label>
                </li>
                <li>
                    <label class="key" />
                    <img class="framed" style="float:left;" src="http://maps.google.com/maps/api/staticmap?zoom=10&size=270x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${subproject.factory.location.latitude}'/>,<c:out value='${subproject.factory.location.longitude}'/>&sensor=false" title="<s:text name='label.configure.factory.address.image'/>"/>
                </li>
            </ul>
            <div class="clear"></div>
        </fieldset>
    
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.subproject.measures"/></h3>
            <%@ include file="/jsp/viewSubprojectMeasures.jsp"%>
        </fieldset>
        
        <fieldset class="plainreportfieldset">
            <h3><s:text name="label.configure.subproject.subprojects"/></h3>
            
            <div class="displaytagTable">
                <display:table name="subproject.project.subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.subproject.name" sortable="true"/>
                    <display:column property="factory.name" escapeXml="true" style="width: 15%" titleKey="table.header.factory.name" sortable="true"/>
                    <display:column property="factory.address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
                    <display:column property="factory.address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
                    <display:column property="factory.company.name" escapeXml="true" style="width: 15%" titleKey="table.header.company.name" sortable="true"/>
                    
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
