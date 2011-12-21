<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Factory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
	<head>
		<sj:head jqueryui="true"/>
	
		<meta name="menu" content="ManageCompanies"/>
		
		<fmt:message key="error.factory_not_selected" var="noFactorySelected"/>
		
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
					$(location).attr('href',url);
				}
			}
		</SCRIPT>
	</head>
	<body id="viewCompany">
		<h1><s:text name="management.company.view.title" /></h1>
		<p><s:text name="management.company.view.text" /></p>
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
							<img style="float:left;" src="<s:text name='company.director.imagePath'/>" width="128" height="128" title="<s:text name='label.configure.director.current_image'/>"/>
						</li>
					</ul>
					<div class="clear"></div>
				</fieldset>
			</div>
			
			<div class="buttonPane">
				<!-- TODO add security tag -->
				<c:url var="edit" value="/showCompanyForm">
					<c:param name="id">${param.id}</c:param>
				</c:url>
				<button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',false)"><fmt:message key="button.edit_company"/></button>
			</div>
			
			<fieldset class="viewingfieldset">
				<h2><s:text name="label.configure.factories"/></h2>
				<display:table name="company.factories" uid="factory" defaultsort="1" class="" pagesize="10" requestURI="">
				  	<display:column  style="width: 5%">
			  			<input type="radio" id="factoryIdRadio" name="factoryIds" value="${factory.id}">
			  		</display:column>
				    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.factory.name" sortable="true"/>
				    <display:column property="information" escapeXml="true" style="width: 55%" titleKey="table.header.factory.information" sortable="false"/>
				    <display:column escapeXml="true" style="width: 10%" titleKey="table.header.factory.projects" sortable="true"><%=((Factory)factory).getProjects().size()%></display:column>
				    
				    <display:setProperty name="paging.banner.placement" value="top"/>
				    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.factory"/></display:setProperty>
				    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.factories"/></display:setProperty>
				    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
				    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
				    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
				    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
				</display:table>
				<div class="buttonPane">
					<!-- TODO add security tag -->
					<c:url var="view" value="/viewFactory"/>
					<button class="minimal" onclick="javascript:call('<c:out value="${view}"/>',true)"><fmt:message key="button.view_factory"/></button>
					<!-- TODO add security tag -->
					<c:url var="edit" value="/showFactoryForm"/>
					<button class="minimal" onclick="javascript:call('<c:out value="${edit}"/>',true)"><fmt:message key="button.edit_factory"/></button>
					<!-- TODO add security tag -->
					<c:url var="delete" value="/deleteFactory"/>
					<button class="minimal" onclick="javascript:call('<c:out value="${delete}"/>',true)"><fmt:message key="button.remove_factory"/></button>
					<!-- TODO add security tag -->
					<c:url var="add" value="/showFactoryForm">
						<c:param name="companyId">${param.id}</c:param>
					</c:url>
					<button class="minimal" onclick="javascript:call('<c:out value="${add}"/>',false)"><fmt:message key="button.add_factory"/></button>
				</div>
			</fieldset>
		</s:if>
	</body>
</html>