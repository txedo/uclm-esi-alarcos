<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="js/utils.js?version=1"></script>
<script type="text/javascript">
function updateMeasures() {
    var submit = true;
    if (checkInputFields()) {
    	$("#saveIndicator").toggle();
        $("#incorrectInputValues").html("");
    } else {
        $("#incorrectInputValues").html("<ul class='errorMessage'><li><span><s:text name='error.incorrect_input_values'/></span></li></ul>");
        submit = false;
    }
    return submit;
}
</script>

<s:actionerror />
<s:actionmessage />

<s:if test="!hasActionErrors()">
	<form id="formFactory" class="form" method="post" action="<c:url value='/editFactoryMeasures'/>" enctype="multipart/form-data">
	    <c:set var="factoryId" value="${param.id}"/>
	    <s:hidden name="id" value="%{#attr.factoryId}"/>
	   
	    <a href="javascript:void(0)" onclick="javascript:goBack()" title="<s:text name='label.go_back'/>">&lt; <s:text name='label.go_back'/></a>
	    
	    <fieldset class="formfieldset">
	        <h2><s:text name="label.configure.factory.measures.title"/></h2>
            <fieldset class="viewingfieldset">
                <ul>
                    <li>
                        <label class="key"><s:text name="label.factory.name"/></label>
                        <label class="value"><s:text name="factory.name"/></label>
                    </li>
                </ul>
            </fieldset>
	        <p><s:text name="label.configure.factory.measures.text"/></p>
	        <span id="incorrectInputValues"></span>
	        
	        <%@ include file="/jsp/generateFactoryMeasureForm.jsp"%>
	        
	        <div class="buttonPane">
                <button class="minimal" onclick="return updateMeasures();"><img id="saveIndicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none;" class="icon"/><fmt:message key="button.update_measures"/></button>
            </div>
	    </fieldset>
	</form>
</s:if>

