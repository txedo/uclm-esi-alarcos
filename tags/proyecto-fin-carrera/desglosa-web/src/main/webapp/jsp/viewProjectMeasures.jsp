<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="js/utils.js?version=1"></script>

<s:actionerror />
<s:actionmessage />

<s:url var="goBack" value="/listProjects"></s:url>
        <a href="<c:out value='${goBack}'/> " title="<s:text name='management.project.list.title'/>">&lt; <s:text name='management.project.list.title'/></a>

<s:if test="!hasActionErrors()">
    <c:set var="projectId" value="${param.id}"/>
    <s:hidden name="id" value="%{#attr.projectId}"/>

    <div class="form">
        <fieldset class="formfieldset">
            <h2><s:text name="label.view.project.measures.title"/></h2>
            <fieldset class="viewingfieldset">
                <ul>
                    <li>
                        <label class="key"><s:text name="label.project.name"/></label>
                        <label class="value"><s:text name="project.name"/></label>
                    </li>
                    <li>
                        <label class="key"><s:text name="label.project.code"/></label>
                        <label class="value"><s:text name="project.code"/></label>
                    </li>
                    <li>
                        <label class="key"><s:text name="label.project.plan"/></label>
                        <label class="value"><s:text name="project.plan"/></label>
                    </li>
                </ul>
            </fieldset>
            <p><s:text name="label.view.project.measures.text"/></p>
            
            <%@ include file="/jsp/generateProjectMeasureView.jsp"%>
            
            <div class="buttonPane">
                <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER">
	                <c:url var="configureMeasures" value="/configureProjectMeasures">
	                    <c:param name="id">${param.id}</c:param>
	                </c:url>
	                <button class="minimal" onclick="javascript:goTo('<c:out value="${configureMeasures}"/>')"><img id="saveIndicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none;" class="icon"/><fmt:message key="button.configure_measures"/></button>
	            </security:authorize>
            </div>
        </fieldset>
    </div>
</s:if>
