<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Factory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
	<head>
	    <sj:head jqueryui="true" />
	    <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
	    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
	    
	    <script type="text/javascript" src="js/utils.js?version=1"></script>
    
		<meta name="menu" content="ManageCompanies"/>
		
		<fmt:message key="error.factory_not_selected" var="noFactorySelected"/>
		<fmt:message key="message.delete_factory_confirmation" var="deleteFactoryConfirmation"/>
		
		<SCRIPT type="text/javascript">
		function getSelectedRadioButton() {
			return $("input:radio[name=factoryIds]:checked").val();
		}
		
		function call(urlAction,selectionRequired) {
			if (!selectionRequired) {
				$(location).attr('href',urlAction);
			} else if (selectionRequired && isUndefined(getSelectedRadioButton())) {
				$("#errorDialogBody").html("<p class='messageBox error'><c:out value='${noFactorySelected}'/></p>");
				$("#errorDialog").dialog("open");
			} else {
				var url = urlAction+"?id="+getSelectedRadioButton();
				if (urlAction.indexOf('delete') != -1) {
                    if (confirm("<c:out value='${deleteFactoryConfirmation}'/>")) {
                        $(location).attr('href',url);
                    }
                } else {
                    $(location).attr('href',url);
                }
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
	    <s:url var="goBack" value="/listCompanies"></s:url>
	    <a href="<c:out value='${goBack}'/> " title="<s:text name='management.company.list.title'/>">&lt; <s:text name='management.company.list.title'/></a>
	   
		<h1><s:text name="management.company.view.title" /></h1>
<%-- 		<p><s:text name="management.company.view.text" /></p> --%>
		<s:actionerror />
		<s:actionmessage />
		
		<s:if test="!hasActionErrors()">
			<s:set name="company" value="company" scope="request"/> 
			
			<div class="form">
				<fieldset class="viewingfieldset">
					<h2><s:text name="label.configure.company"/></h2>
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

				<fieldset class="viewingfieldset">
					<h2><s:text name="label.configure.director"/></h2>
					<ul>
						<li>
							<label class="key"><s:text name="label.configure.director.name"/></label>
							<label class="value"><s:text name="company.director.name"/></label>
						</li>
						<li>
							<label class="key"><s:text name="label.configure.director.last_name"/></label>
							<label class="value"><s:text name="company.director.lastName"/></label>
						</li>
						<li>
							<label class="key"><s:text name="label.configure.director.image"/></label>
							<img class="framed" style="float:left;" src="<s:text name='company.director.imagePath'/>" width="128" height="128" title="<s:text name='label.configure.director.current_image'/>"/>
						</li>
					</ul>
					<div class="clear"></div>
				</fieldset>
				
				<div class="buttonPane">
	                <security:authorize ifAnyGranted="ROLE_ADMIN">
		                <c:url var="edit" value="/showCompanyForm">
		                    <c:param name="id">${param.id}</c:param>
		                </c:url>
		                <button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_company"/></button>
		            </security:authorize>
	            </div>
			</div>
			
            <div class="form">
                <fieldset class="viewingfieldset">
                    <h2><s:text name="label.company.measures"/></h2>
                    <%@ include file="/jsp/generateCompanyMeasureView.jsp"%>
                </fieldset>
                
	            <div class="buttonPane">
	                <security:authorize ifAnyGranted="ROLE_ADMIN">
		                <c:url var="configureMeasures" value="/configureCompanyMeasures">
		                    <c:param name="id">${param.id}</c:param>
		                </c:url>
		                <button class="minimal" onclick="javascript:call('<c:out value="${configureMeasures}"/>',false)"><fmt:message key="button.configure_measures"/></button>
		            </security:authorize>
	            </div>
            </div>
			
			<fieldset class="viewingfieldset">
				<h2><s:text name="label.configure.factories"/></h2>
				
				<div class="displaytagTable">
					<display:table name="company.factories" uid="factory" defaultsort="1" class="" pagesize="10" requestURI="">
					  	<display:column  style="width: 5%; text-align: center;">
				  			<input type="radio" id="factoryIdRadio" name="factoryIds" value="${factory.id}">
				  		</display:column>
					    <display:column property="name" escapeXml="true" style="width: 20%" titleKey="table.header.factory.name" sortable="true"/>
					    <display:column property="information" escapeXml="true" style="width: 25%" titleKey="table.header.factory.information" sortable="false"/>
					    <display:column escapeXml="true" style="width: 8%; text-align: center;" titleKey="table.header.factory.projects" sortable="true"><%=((Factory)factory).getProjects().size()%></display:column>
					    <display:column escapeXml="true" style="width: 8%; text-align: center;" titleKey="table.header.factory.subprojects" sortable="true"><%=((Factory)factory).getSubprojects().size()%></display:column>
	                    <display:column escapeXml="false" style="width: 11%" titleKey="table.header.market.name" sortable="true">
	                    	<script>
	                    	var span = getMarketSpan('${factory.mostRepresentativeMarket.color}', '${factory.mostRepresentativeMarket.name}');
	                    	$("td:last").append(span);
	                    	</script>
	                    </display:column>
	                    <display:column property="address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
	                    <display:column property="address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
	                    <display:column escapeXml="false" style="width: 10%; text-align: center;" titleKey="table.header.location" sortable="false">
	                        <div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${factory.location.latitude}'/>,<c:out value='${factory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img src="images/world_search.png" height="16" width="16" /></div>
	                    </display:column>
					    
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
				
				<div class="buttonPane">
					<security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER,ROLE_USER">
						<c:url var="view" value="/viewFactory"/>
						<button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_factory"/></button>
					</security:authorize>
	    			<security:authorize ifAnyGranted="ROLE_ADMIN">
						<c:url var="edit" value="/showFactoryForm"/>
						<button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_factory"/></button>
					</security:authorize>
	    			<security:authorize ifAnyGranted="ROLE_ADMIN">
						<c:url var="delete" value="/deleteFactory"/>
						<button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_factory"/></button>
					</security:authorize>
	    			<security:authorize ifAnyGranted="ROLE_ADMIN">
						<c:url var="add" value="/showFactoryForm">
							<c:param name="companyId">${param.id}</c:param>
						</c:url>
						<button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_factory"/></button>
					</security:authorize>
				</div>
			</fieldset>
		</s:if>
	</body>
</html>