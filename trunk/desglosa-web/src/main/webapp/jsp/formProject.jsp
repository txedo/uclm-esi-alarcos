<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
    <sj:head jqueryui="true" />
    <script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
    
    <s:set var="projectMarketId" value="project.market.id" scope="request" />
    
	<meta name="menu" content="ManageProjects"/>

	<script type="text/javascript" src="js/utils.js?version=1"></script>

	<script type="text/javascript">
	$(document).ready(function() {
	    // Initialize select of markets
	    $.getJSON("/json_m_execute",
	            { },
	            function (data, status) {
	                if (status == "success") {
	                    $("#marketSelect").html("");
	                    $.each(data.markets, function (key, value) {
	                        $("#marketSelect").append($("<option></option>").attr("value", value.id).text(value.name));
	                        if (value.id == "<c:out value='${projectMarketId}'/>") {
	                            $("#marketSelect option:last").attr('selected','selected');
	                        }
	                    });
	                }
	                else alert('An error has occurred while trying to retrieve markets: ' + status);
	    });
	    
		// Initialize tooltips
		$("div.tooltipstyle").tooltip({
			position: 'top center',
			delay: 0
			});
	});
	</script>
</head>
<body id="showProject" class="">
	<div>
		<h1><s:text name="management.project.add.title" /></h1>
		<p><s:text name="management.project.add.text" /></p>
	
		<s:actionerror />
		<s:fielderror><s:param>error.required_fields</s:param></s:fielderror>
	
		<c:set var="form" value="/editProject"/>
		<c:set var="buttonLabel" value="button.edit_project"/>
		<c:if test="${(empty param.id and not fn:contains(header.referer,'/editProject') and not fn:contains(header.referer,'id=')) or fn:contains(header.referer,'factoryId=')}">
			<c:set var="form" value="/saveProject"/>
			<c:set var="buttonLabel" value="button.add_project"/>
		</c:if>

		<form id="formProject" class="form" method="post" action="<c:url value="${form}"/>" enctype="multipart/form-data">
			<s:hidden name="project.id"/>
		
			<fieldset class="formfieldset">
				<h2><s:text name="label.configure.project.data"/></h2>
				<ul>
					<li>
						<label for="project.name"><s:text name="label.project.name"/></label>
						<s:textfield id="project.name" name="project.name" tabindex="1"/>
						<s:fielderror><s:param>error.project.name</s:param></s:fielderror>
					</li>
					<li>
						<label for="project.code"><s:text name="label.project.code"/></label>
						<s:textfield id="project.code" name="project.code" tabindex="2"/>
						<s:fielderror><s:param>error.project.code</s:param></s:fielderror>
					</li>
					<li>
						<label for="project.plan"><s:text name="label.project.plan"/></label>
						<s:textfield id="project.plan" name="project.plan" tabindex="3"/>
						<s:fielderror><s:param>error.project.plan</s:param></s:fielderror>
					</li>
					<li>
						<label for="project.market"><s:text name="label.configure.project.market"/></label>
						<select id="marketSelect" name="project.market.id"></select>
						<s:fielderror><s:param>error.project.market</s:param></s:fielderror>
					</li>
				</ul>
			</fieldset>
        
			<fieldset class="formfieldset">
				<h2><s:text name="label.configure.project.factory"/></h2>
				<s:fielderror><s:param>error.factory_required</s:param></s:fielderror>
				<s:text name="label.configure.factory.choose_factory"/>
				<div>
					<s:set name="factories" value="factories" scope="request"/>  
					<s:set name="project" value="project" scope="request"/>
					<display:table name="factories" id="factory" cellspacing="0" cellpadding="0" defaultsort="1" pagesize="10" requestURI="showFactoryForm.action">
						<display:column style="width: 5%">
						<c:choose>
							<c:when test="${project.mainFactory.id == factory.id or factory.id == param.factoryId}">
								<input type="radio" name="project.mainFactory.id" value="${factory.id}" checked/>
							</c:when>
							<c:otherwise>
								<input type="radio" name="project.mainFactory.id" value="${factory.id}" />
							</c:otherwise>
						</c:choose>
						</display:column>
						<display:column property="name" escapeXml="true" style="width: 15%" titleKey="table.header.factory.name" sortable="true"/>
						<display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.factory.information" sortable="false"/>
						<display:column property="mostRepresentativeMarket" escapeXml="true" style="width: 11%" titleKey="table.header.market.name" sortable="true"/>
						<display:column property="address.city" escapeXml="true" style="width: 15%" titleKey="table.header.address.city" sortable="true"/>
						<display:column property="address.country" escapeXml="true" style="width: 15%" titleKey="table.header.address.country" sortable="true"/>
						<display:column escapeXml="false" style="width: 10%" titleKey="table.header.location" sortable="false">
							<div class="tooltipstyle" title="<img src='http://maps.google.com/maps/api/staticmap?zoom=10&size=170x130&maptype=roadmap&markers=color:red|color:red|<c:out value='${factory.location.latitude}'/>,<c:out value='${factory.location.longitude}'/>&sensor=false' width='170' height='130' title='<s:text name='label.configure.factory.address.image'/>'/>"><img src="images/world_search.png" height="16" width="16" /></div>
						</display:column>
						
						<display:setProperty name="paging.banner.placement" value="top"/>
						<display:setProperty name="paging.banner.item_name"><fmt:message key="label.factory"/></display:setProperty>
						<display:setProperty name="paging.banner.items_name"><fmt:message key="label.factories"/></display:setProperty>
						<display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
						<display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
						<display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
						<display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
					</display:table>
				</div>
			</fieldset>

			<s:submit value="%{getText(#attr.buttonLabel)}"/>
		</form>
	</div>
</body>
</html>