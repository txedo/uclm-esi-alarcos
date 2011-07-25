<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
	<head>
		<meta name="menu" content="ManageProfiles"/>
		
		<sj:head jqueryui="true"/>
		<script language="text/javascript">
		$(document).ready(function() {
			$("#entitySelect option:first").attr('disabled','disabled');
		}
		</script>
	</head>
	
	<body>
		<s:form id="configureForm" action="json_p_updateProfileForm">
			<s:url id="updateProfileURL" action="json_p_updateProfileForm"/>
			<s:url id="refreshTableColumns" action="json_p_loadTableColumns"/>
			
			<sj:select 	href="%{updateProfileURL}"
						emptyOption="false"
						headerKey="-1"
						headerValue="Please select an entity"
						id="entitySelect"
						name="entity"
						list="entities"
						onChangeTopics="reloadTableColumns"/>
			
			<!-- 	
			<sj:select  href="refreshTableColumns"
						emptyOption="false"
						size="10"
						id="columnSelect"
						list="metaclass"/>
			 -->
						
			<sj:select 	href="%{updateProfileURL}"
						emptyOption="false"
						headerKey="-1"
						headerValue="Please select a model"
						id="modelSelect"
						name="model"
						list="models"
						formIds="configureForm"
						onChangeTopics="reloadClassAttributes"/>
		</s:form>
	</body>
</html>