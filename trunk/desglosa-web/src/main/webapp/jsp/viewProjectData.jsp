<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper"%>
<%@page import="java.util.List"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureAnnotationParser"%>
<%@page import="es.uclm.inf_cr.alarcos.desglosa_web.model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<c:set var="measures" value="<%= MeasureAnnotationParser.parseAllMeasures(Project.class) %>"></c:set>

<c:choose>
	<c:when test="${fn:length(measures) gt 0}">
	    <ul>
	       <s:set name="project" value="project" scope="request"/>
	       
	        <c:forEach var="measure" items="${measures}">
	            <c:set var="measureName" value="project.${measure.name}"></c:set>
	            <li>
	                <label class="key"><fmt:message key="label.${measure.name}" /></label>
	                <c:if test="${measure.type == 'Boolean'}">
	                   <c:out value="${measureName}"></c:out>
	                    <c:choose>
	                        <c:when test="project.${measure.name} == 1">
	                            <label class="value"><s:text name="label.Yes"/></label>
	                        </c:when>
	                        <c:otherwise>
	                            <label class="value"><s:text name="label.No"/></label>
	                        </c:otherwise>
	                    </c:choose>
	                </c:if>
	                <c:if test="${measure.type == 'Integer' || measure.type== 'Float'}">
                        <label class="value">
                            <s:text name="%{#attr.measureName}"/>
                        </label>
	                </c:if>
	            </li>
	        </c:forEach>
	    </ul>
	</c:when>
    <c:otherwise>
        <p><s:text name="label.project.measures.lt0"></s:text></p>
    </c:otherwise>
</c:choose>
