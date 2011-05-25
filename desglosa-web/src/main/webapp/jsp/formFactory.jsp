<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageFactories"/>
	<script src="js/radio.js" type="text/javascript"></script>
	<script type="text/javascript">
	function swapDivVisibility (toHide, toShow) {
		document.getElementById(toHide).style.display='none';
		document.getElementById(toShow).style.display='';
	}
	</script>
</head>
<body>
	<s:text name="menu.admin.factories" />
	<s:actionerror />
	<c:set var="form" value="/editFactory.action"/>
	<c:set var="buttonLabel" value="button.edit_factory"/>
	<c:if test="${empty param.id}">
		<c:set var="form" value="/saveFactory.action"/>
		<c:set var="buttonLabel" value="button.add_factory"/>
	</c:if>
	<form id="formFactory" method="post" action="<c:url value="${form}"/>">
		<div id="chooseCompany" style="display:">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.choose_company"/>
				</li>
				<li>
					<display:table name="requestScope.companies" id="company" cellspacing="0" cellpadding="0" defaultsort="1" class="" pagesize="50" requestURI="">
						<display:column style="width: 5%">
							<input type="radio" name="choosenCompanyId" value="${company.id}" onclick="document.getElementById('gotoSecondStep').disabled=''"/>
						</display:column>
					    <display:column property="name" escapeXml="true" style="width: 30%" titleKey="table.header.company.name" sortable="true"/>
					    <display:column property="information" escapeXml="true" style="width: 30%" titleKey="table.header.company.information" sortable="false"/>
					    
					    <display:setProperty name="paging.banner.placement" value="bottom"/>
					    <display:setProperty name="paging.banner.item_name"><fmt:message key="message.company"/></display:setProperty>
					    <display:setProperty name="paging.banner.items_name"><fmt:message key="message.companies"/></display:setProperty>
					    <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.no_items_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.one_item_found"><span class="pagebanner"><fmt:message key="table.paging.banner.one_item_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.all_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.all_items_found"/></span></display:setProperty>
					    <display:setProperty name="paging.banner.some_items_found"><span class="pagebanner"><fmt:message key="table.paging.banner.some_items_found"/></span></display:setProperty>
					</display:table>
				</li>
				<li>
					<input type="button" value="< Back" disabled="disabled" style="visibility: hidden"/>
					<input type="button" id="gotoSecondStep" name="gotoSecondStep" value="Next >" disabled="disabled" onclick="swapDivVisibility('chooseCompany','fillFactoryData')"/>
				</li>
			</ul>
		</div>
		<div id="fillFactoryData" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.data"/>
				</li>
				<li>
					<label for="factory.name"><fmt:message key="label.configure.factory.data.name"></fmt:message></label>
					<s:textfield id="factory.name" name="factory.name" tabindex="1"/>
				</li>
				<li>
					<label for="factory.information"><fmt:message key="label.configure.factory.data.information"></fmt:message></label>
					<s:textfield id="factory.information" name="factory.information" tabindex="2"/>
				</li>
				<li>
					<label for="factory.email"><fmt:message key="label.configure.factory.data.email"></fmt:message></label>
					<s:textfield id="factory.email" name="factory.email" tabindex="3"/>
				</li>
				<li>
					<label for="factory.employees"><fmt:message key="label.configure.factory.data.employees"></fmt:message></label>
					<s:textfield id="factory.employees" name="factory.employees" tabindex="4"/>
				</li>
				<li>
					<input type="button" id="gotoSecondStep" name="gotoSecondStep" value="< Back" onclick="swapDivVisibility('fillFactoryData','chooseCompany')"/>
					<input type="button" id="gotoThirdStep" name="gotoThirdStep" value="Next >" onclick="swapDivVisibility('fillFactoryData','fillAddress')"/>
				</li>
			</ul>
		</div>
		<div id="fillAddress" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.address"/>
				</li>
				<li>
					<label for="address.address"><fmt:message key="label.configure.factory.address.address"></fmt:message></label>
					<s:textfield id="address.address" name="address.address" tabindex="1"/>
				</li>
				<li>
					<label for="address.city"><fmt:message key="label.configure.factory.address.city"></fmt:message></label>
					<s:textfield id="address.city" name="address.city" tabindex="2"/>
				</li>
				<li>
					<label for="address.province"><fmt:message key="label.configure.factory.address.province"></fmt:message></label>
					<s:textfield id="address.province" name="address.province" tabindex="3"/>
				</li>
				<li>
					<label for="address.country"><fmt:message key="label.configure.factory.address.country"></fmt:message></label>
					<s:textfield id="address.country" name="address.country" tabindex="4"/>
				</li>
				<li>
					<label for="address.postal_code"><fmt:message key="label.configure.factory.address.postal_code"></fmt:message></label>
					<s:textfield id="address.postal_code" name="address.postal_code" tabindex="5"/>
				</li>
				<!-- address, number, postal_code city, province, country -->
				<li>
					<input type="button" id="gotoThirdStep" name="gotoThirdStep" value="< Back" onclick="swapDivVisibility('fillAddress','fillFactoryData')"/>
					<input type="button" id="gotoFourthStep" name="gotoFourthStep" value="Next >" onclick="swapDivVisibility('fillAddress','fillDirector')"/>
				</li>
			</ul>
		</div>
		<div id="fillDirector" style="display: none">
			<ul>
				<li>
					<fmt:message key="label.configure.factory.director"/>
				</li>
				<li>
					<label for="director.name"><fmt:message key="label.configure.factory.director.name"></fmt:message></label>
					<s:textfield id="director.name" name="director.name" tabindex="1"/>
				</li>
				<li>
					<label for="director.firstSurname"><fmt:message key="label.configure.factory.director.first_surname"></fmt:message></label>
					<s:textfield id="director.firstSurname" name="director.firstSurname" tabindex="2"/>
				</li>
				<li>
					<label for="director.lastSurname"><fmt:message key="label.configure.factory.director.last_surname"></fmt:message></label>
					<s:textfield id="director.lastSurname" name="director.lastSurname" tabindex="3"/>
				</li>
				<li>
					image
				</li>
				<li>
					<input type="button" id="gotoFourthStep" name="gotoFourthStep" value="< Back" onclick="swapDivVisibility('fillDirector','fillAddress')"/>
					<input type="submit" id="submit" name="submit" value="Finish" onclick=""/>
				</li>
			</ul>
		</div>
	</form>
</body>
</html>