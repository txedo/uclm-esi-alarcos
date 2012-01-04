<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureAnnotationParser"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
<link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />

<fmt:message key="label.tooltip.int" var="labelInteger"/>
<fmt:message key="label.tooltip.float" var="labelFloat"/>

<c:set var="measures" value="<%= MeasureAnnotationParser.parseBaseMeasures(Company.class) %>"></c:set>
<c:choose>
	<c:when test="${fn:length(measures) gt 0}">
	<ul>
		<c:forEach var="measure" items="${measures}">
		    <c:set var="measureName" value="company.${measure.name}"></c:set>
		    <li>
		        <label for="${measureName}"><fmt:message key="label.${measure.name}" /></label>
		        <c:if test="${measure.type == 'Boolean'}">
		            <s:checkbox id="%{#attr.measureName}" name="%{#attr.measureName}"></s:checkbox>
		        </c:if>
		        <c:if test="${measure.type == 'Integer'}">
		            <c:set var="measureTooltip" value="${measure.description} ${labelInteger}"></c:set>
		            <s:textfield cssClass="intNumber" id="%{#attr.measureName}" name="%{#attr.measureName}" title="%{#attr.measureTooltip}"/>
		        </c:if>
		        <c:if test="${measure.type== 'Float'}">
		            <c:set var="measureTooltip" value="${measure.description} ${labelFloat}"></c:set>
                    <s:textfield cssClass="floatNumber" id="%{#attr.measureName}" name="%{#attr.measureName}" title="%{#attr.measureTooltip}"/>
                </c:if>
		        <s:fielderror><s:param>error.company.${measureName}</s:param></s:fielderror>
		    </li>
		</c:forEach>
	</ul>
	</c:when>
    <c:otherwise>
        <p><s:text name="label.company.base_measures.lt0"></s:text></p>
    </c:otherwise>
</c:choose>

<!-- javascript coding -->
<script>
// execute your scripts when the DOM is ready. this is a good habit
$(function() {
	// select all desired input fields and attach tooltips to them
	$("input.floatNumber[type=text],input.intNumber[type=text]").tooltip({
		tipClass: "inputtooltip",
	    // place tooltip on the right edge
	    position: "center right",
	    // a little tweaking of the position
	    offset: [-2, 10],
	    // use the built-in fadeIn/fadeOut effect
	    effect: "fade",
	    // custom opacity setting
	    opacity: 0.7
	});
});
</script>
