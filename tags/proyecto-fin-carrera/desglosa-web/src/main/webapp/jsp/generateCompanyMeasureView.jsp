<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureAnnotationParser"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<c:set var="measures" value="<%= MeasureAnnotationParser.parseAllMeasures(Company.class) %>"></c:set>

<c:choose>
	<c:when test="${fn:length(measures) gt 0}">
	    <ul>
	       <s:set name="company" value="company" scope="request"/>
	       
	        <c:forEach var="measure" items="${measures}">
	            <c:set var="measureName" value="company.${measure.name}"></c:set>
	            <li>
	                <label class="key">
	                	<fmt:message key="label.${measure.name}" />
	                	<c:if test="${measure.base == false}">
	                		<c:out value=" (*)"></c:out>
	                	</c:if>
	                </label>
	                <label class="value">
		                <c:if test="${measure.type == 'Boolean'}">
		                   <s:checkbox name="%{#attr.measureName}" onclick="return false;"/>
		                </c:if>
		                <c:if test="${measure.type == 'Integer' || measure.type== 'Float'}">
	                        <s:text name="%{#attr.measureName}"/>
		                </c:if>
	                </label>
	            </li>
	        </c:forEach>
	    </ul>
	    <div class="clear"><p><s:text name="label.base_measure_mark"></s:text></p></div>
	</c:when>
    <c:otherwise>
        <p><s:text name="label.company.measures.lt0"></s:text></p>
    </c:otherwise>
</c:choose>
