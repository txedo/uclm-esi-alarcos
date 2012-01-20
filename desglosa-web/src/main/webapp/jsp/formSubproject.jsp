<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
    <sj:head jqueryui="true" />
    <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />

	<meta name="menu" content="ManageSubprojects"/>

	<script type="text/javascript" src="js/utils.js?version=1"></script>

	<script type="text/javascript">
	$(document).ready(function() {
		// Initialize tooltips
		$("div.tooltipstyle").tooltip({
			position: 'top center',
			delay: 0
			});
	});
	</script>
</head>
<body id="showSubproject" class="">
	<div>
	    <a href="javascript:void(0)" onclick="javascript:goBack()" title="<s:text name='label.go_back'/>">&lt; <s:text name='label.go_back'/></a>
	    
		<h1><s:text name="management.subproject.add.title" /></h1>
		<p><s:text name="management.subproject.add.text" /></p>
	
		<s:actionerror />
		<s:fielderror><s:param>error.required_fields</s:param></s:fielderror>
	
		<c:set var="form" value="/editSubproject"/>
		<c:set var="buttonLabel" value="button.edit_subproject"/>
		<c:if test="${(empty param.id and not fn:contains(header.referer,'/editSubproject') and not fn:contains(header.referer,'id=')) or fn:contains(header.referer,'factoryId=') or not empty param.projectId}">
			<c:set var="form" value="/saveSubproject"/>
			<c:set var="buttonLabel" value="button.add_subproject"/>
		</c:if>

		<form id="formSubproject" class="form" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
			<s:hidden name="subproject.id"/>
		
			<fieldset class="formfieldset">
				<h2><s:text name="label.configure.subproject.data"/></h2>
				<ul>
					<li>
						<label for="subproject.name"><s:text name="label.subproject.name"/> (*)</label>
						<s:textfield id="subproject.name" name="subproject.name" tabindex="1"/>
						<s:fielderror><s:param>error.subproject.name</s:param></s:fielderror>
					</li>
				</ul>
			</fieldset>
			
			<fieldset class="formfieldset">
				<h2><s:text name="label.configure.subproject.project"/></h2>
				<s:fielderror><s:param>error.project_required</s:param></s:fielderror>
				<p>(*) <s:text name="label.configure.subproject.choose_project"/></p>
				<div class="displaytagTable">
					<s:set name="projects" value="projects" scope="request"/>  
					<s:set name="subproject" value="subproject" scope="request"/>
					<display:table name="projects" id="project" defaultsort="2" pagesize="10" requestURI="">
						<display:column style="width: 5%; text-align: center;">
						<c:choose>
							<c:when test="${subproject.project.id == project.id or project.id == param.projectId}">
								<input type="radio" name="subproject.project.id" value="${project.id}" checked/>
							</c:when>
							<c:otherwise>
								<input type="radio" name="subproject.project.id" value="${project.id}" />
							</c:otherwise>
						</c:choose>
						</display:column>
						<display:column property="name" escapeXml="true" style="width: 12%" titleKey="table.header.project.name" sortable="true"/>
						<display:column property="code" escapeXml="true" style="width: 8%; text-align: center;" titleKey="table.header.project.code" sortable="true"/>
				        <display:column property="plan" escapeXml="true" style="width: 8%" titleKey="table.header.project.plan" sortable="true"/>
				        <display:column escapeXml="false" style="width: 15%" titleKey="table.header.project.market" sortable="true">
				            <span class="icon" style="background-color:#<c:out value='${project.market.color}' />"></span><c:out value='${project.market.name}' />
				        </display:column>
				        <display:column escapeXml="false" style="width: 15%" titleKey="table.header.factory.name" sortable="true">
							<div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${project.mainFactory.location.latitude}'/>,<c:out value='${project.mainFactory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img class="searchIcon" src="images/world_search.png" height="16" width="16" /><c:out value="${project.mainFactory.name}"/></div>
						</display:column>
        				<display:column property="mainFactory.address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
						<display:column property="mainFactory.address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
						
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
			
			<fieldset class="formfieldset">
				<h2><s:text name="label.configure.subproject.factory"/></h2>
				<s:fielderror><s:param>error.factory_required</s:param></s:fielderror>
				<p>(*) <s:text name="label.configure.subproject.choose_factory"/></p>
				<div class="displaytagTable">
					<s:set name="factories" value="factories" scope="request"/>  
					<s:set name="subproject" value="subproject" scope="request"/>
					<display:table name="factories" id="factory" defaultsort="2" pagesize="10" requestURI="">
						<display:column style="width: 5%; text-align: center;">
						<c:choose>
							<c:when test="${subproject.factory.id == factory.id or factory.id == param.factoryId}">
								<input type="radio" name="subproject.factory.id" value="${factory.id}" checked/>
							</c:when>
							<c:otherwise>
								<input type="radio" name="subproject.factory.id" value="${factory.id}" />
							</c:otherwise>
						</c:choose>
						</display:column>
						<display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.factory.name" sortable="true"/>
						<display:column property="information" escapeXml="true" style="width: 25%" titleKey="table.header.factory.information" sortable="false"/>
						<display:column escapeXml="false" style="width: 12%" titleKey="table.header.market.name" sortable="true">
						    <script>
                            var span = getMarketSpan('${factory.mostRepresentativeMarket.color}', '${factory.mostRepresentativeMarket.name}');
                            $("td:last").append(span);
                            </script>
				        </display:column>
						<display:column property="address.city" escapeXml="true" style="width: 12%" titleKey="table.header.address.city" sortable="true"/>
						<display:column property="address.country" escapeXml="true" style="width: 12%" titleKey="table.header.address.country" sortable="true"/>
						<display:column escapeXml="false" style="width: 8%; text-align: center;" titleKey="table.header.location" sortable="false">
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
			</fieldset>
			
            <div style="display:none">
                <%@ include file="/jsp/generateSubprojectMeasureForm.jsp"%>
            </div>
			<s:submit onclick="formatFloatFields();return true;" value="%{getText(#attr.buttonLabel)}"/>
		</form>
	</div>
</body>
</html>