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
        
        $(document).ready(function() {
            // Initialize tooltips
            $("div.tooltipstyle").tooltip({
                position: 'top center',
                delay: 0
                });
        });
        </SCRIPT>
    </head>
    <body id="viewCompany">
        <h1><s:text name="management.subproject.view.title" /></h1>
        <p><s:text name="management.subproject.view.text" /></p>
        <s:actionerror />
        <s:actionmessage />
        
        <s:if test="!hasActionErrors()">
            <s:set name="subproject" value="subproject" scope="request"/> 
            
            <div class="form">
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.subproject.details"/></h2>
                    <ul>
                        <li>
                            <label class="key"><s:text name="label.subproject.name"/></label>
                            <label class="value"><s:text name="subproject.name"/></label>
                        </li>
                    </ul>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.subproject.project"/></h2>
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
                            <label class="value"><s:text name="subproject.project.market.name"/><span class="icon" style="background-color:#<s:text name='subproject.project.market.color'/>"></span></label>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
                
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.configure.subproject.factory"/></h2>
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
                            <label class="value"><s:text name="subproject.factory.mostRepresentativeMarket.name"/><span class="icon" style="background-color:#<s:text name='subproject.factory.mostRepresentativeMarket.color'/>"></span></label>
                        </li>
                        <li>
                            <label class="key" />
                            <img style="float:left;" src="http://maps.google.com/maps/api/staticmap?zoom=10&size=256x256&maptype=roadmap&markers=color:red|color:red|<c:out value='${subproject.factory.location.latitude}'/>,<c:out value='${subproject.factory.location.longitude}'/>&sensor=false" width="256" height="256" title="<s:text name='label.configure.factory.address.image'/>"/>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </fieldset>
                
            </div>
            
            <div class="buttonPane">
                <!-- TODO add security tag -->
                <c:url var="edit" value="/showSubrojectForm">
                    <c:param name="id">${param.id}</c:param>
                </c:url>
                <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_subproject"/></button>
            </div>
            
            <fieldset class="viewingfieldset">
                <h2><s:text name="label.configure.subproject.subprojects"/></h2>
                <display:table name="subproject.project.subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
                    <display:column  style="width: 5%">
                        <input type="radio" id="subprojectIdRadio" name="subprojectIds" value="${subproject.id}">
                    </display:column>
                    <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.subproject.name" sortable="true"/>
                    <display:column property="factory.company.name" escapeXml="true" style="width: 15%" titleKey="table.header.company.name" sortable="true"/>
                    <display:column escapeXml="false" style="width: 15%" titleKey="table.header.factory.name" sortable="true">
                        <div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${subproject.factory.location.latitude}'/>,<c:out value='${subproject.factory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img class="searchIcon" src="images/world_search.png" height="16" width="16" /><c:out value="${subproject.factory.name}"/></div>
                    </display:column>
                    <display:column escapeXml="false" style="width: 15%" titleKey="table.header.factory.market" sortable="true">
                        <span class="icon" style="background-color:#<c:out value='${subproject.factory.mostRepresentativeMarket.color}'/>"></span><c:out value='${subproject.factory.mostRepresentativeMarket.name}'/>
                    </display:column>
                    <display:column property="factory.address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
                    <display:column property="factory.address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
                    
                    <display:setProperty name="paging.banner.placement" value="top"/>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="label.subproject"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="label.subprojects"/></display:setProperty>
                    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found_male"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found_male"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found_male"/></span></display:setProperty>
                    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found_male"/></span></display:setProperty>
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