<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Project"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
    <head>
        <sj:head jqueryui="true"/>
        <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
        <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript" src="js/utils.js?version=1"></script>
    
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
        
        $(document).ready(function() {
            $("div.tooltipstyle").tooltip({
                position: 'top center',
                delay: 0
                });
        });
        </SCRIPT>
    </head>
    <body id="viewFactory">
        <s:url var="goBack" value="/listFactories"></s:url>
        <a href="<c:out value='${goBack}'/> " title="<s:text name='management.factory.list.title'/>">&lt; <s:text name='management.factory.list.title'/></a>
        
        <div id="factoryView">
	        <h1><s:text name="management.factory.view.title" /></h1>
	<%--         <p><s:text name="management.factory.view.text" /></p> --%>
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
	                        <li>
	                            <label class="key"><s:text name="label.market.name"/></label>
	                            <label class="value">
	                                <script>
						            var span = getMarketSpan('${factory.mostRepresentativeMarket.color}', '${factory.mostRepresentativeMarket.name}');
						            $("label:last").append(span);
						            </script>
	                            </label>
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
	                            <img class="framed" style="float:left;" src="http://maps.google.com/maps/api/staticmap?zoom=10&size=256x256&maptype=roadmap&markers=color:red|color:red|<c:out value='${factory.location.latitude}'/>,<c:out value='${factory.location.longitude}'/>&sensor=false" width="256" height="256" title="<s:text name='label.configure.factory.address.image'/>"/>
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
	                            <img class="framed" style="float:left;" src="<s:text name='factory.director.imagePath'/>" width="128" height="128" title="<s:text name='label.configure.director.current_image'/>"/>
	                        </li>
	                    </ul>
	                    <div class="clear"></div>
	                </fieldset>
	                
	                <div class="buttonPane">
	                    <!-- TODO add security tag -->
	                    <c:url var="edit" value="/showFactoryForm">
	                        <c:param name="id">${param.id}</c:param>
	                    </c:url>
	                    <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_factory"/></button>
	                </div>
	            </div>
	            
	            <div class="form">
	                <fieldset class="viewingfieldset">
	                    <h2><s:text name="label.factory.measures"/></h2>
	                    <%@ include file="/jsp/generateFactoryMeasureView.jsp"%>
	                </fieldset>
	                
		            <div class="buttonPane">
		                <!-- TODO add security tag -->
		                <c:url var="configureMeasures" value="/configureFactoryMeasures">
		                    <c:param name="id">${param.id}</c:param>
		                </c:url>
		                <button class="minimal" onclick="javascript:call('<c:out value="${configureMeasures}"/>',false)"><fmt:message key="button.configure_measures"/></button>
		            </div>
	            </div>
	            
	            <fieldset class="viewingfieldset">
	                <h2><s:text name="label.factory.projects.title"/></h2>
	                <p><s:text name="label.factory.projects.text"/></p>
	                
	                <div class="displaytagTable">
		                <display:table name="factory.projects" uid="project" defaultsort="1" class="" pagesize="10" requestURI="">
		                    <display:column  style="width: 5%; text-align: center;">
		                        <input type="radio" id="projectIdRadio" name="projectIds" value="${project.id}">
		                    </display:column>
		                    <display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.project.name" sortable="true"/>
		                    <display:column property="code" escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.code" sortable="true"/>
		                    <display:column property="plan" escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.plan" sortable="true"/>
		                    <display:column escapeXml="false" style="width: 10%; text-align: center;" titleKey="table.header.market.name" sortable="true">
	                            <script>
	                            var span = getMarketSpan('${project.market.color}', '${project.market.name}');
	                            $("td:last").append(span);
	                            </script>
					        </display:column>
		                    <display:column escapeXml="true" style="width: 10%; text-align: center;" titleKey="table.header.project.subprojects" sortable="true"><%=((Project)project).getSubprojects().size()%></display:column>
		                    
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
	            
	            <fieldset class="viewingfieldset">
	                <h2><s:text name="label.factory.subprojects.title"/></h2>
	                <p><s:text name="label.factory.subprojects.text"/></p>
	                
	                <div class="displaytagTable">
	                    <display:table name="factory.subprojects" uid="subproject" defaultsort="1" class="" pagesize="10" requestURI="">
	                        <display:column  style="width: 5%; text-align: center;">
	                            <input type="radio" id="subprojectIdRadio" name="subprojectIds" value="${subproject.id}">
	                        </display:column>
	                        <display:column property="name" escapeXml="true" style="width: 10%" titleKey="table.header.subproject.name" sortable="true"/>
	                        <display:column property="project.name" escapeXml="true" style="width: 10%" titleKey="table.header.project.name" sortable="true"/>
	                        <display:column property="project.code" escapeXml="true" style="width: 7%; text-align: center;" titleKey="table.header.subproject.project.code" sortable="true"/>
	                        <display:column property="project.plan" escapeXml="true" style="width: 7%; text-align: center;" titleKey="table.header.subproject.project.plan" sortable="true"/>
	                        <display:column escapeXml="false" style="width: 10%" titleKey="table.header.project.market" sortable="true">
	                            <script>
	                            var span = getMarketSpan('${subproject.project.market.color}', '${subproject.project.market.name}');
	                            $("td:last").append(span);
	                            </script>
	                        </display:column>
	                        <display:column escapeXml="false" style="width: 8%; text-align: center;" titleKey="table.header.project.subprojects" sortable="true">
	                            <%=((Subproject)subproject).getProject().getSubprojects().size() %>
	                        </display:column>
	                        <display:column escapeXml="false" style="width: 12%; text-align: center;" titleKey="table.header.subproject.project.factory" sortable="true">
	                            <div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${project.mainFactory.location.latitude}'/>,<c:out value='${project.mainFactory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img class="searchIcon" src="images/world_search.png" height="16" width="16" /><c:out value="${project.mainFactory.name}"/></div>
	                        </display:column>
	                        
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
        </div>
    </body>
</html>