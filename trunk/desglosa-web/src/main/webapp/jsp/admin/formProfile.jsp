<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageProfiles"/>
	<link href="<s:url value='/styles/buttons.css'/>" rel="stylesheet" type="text/css" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript">
	$.subscribe('disableHeader', function(event, element) {
		$("option:first", element).attr('disabled','disabled');
	});
	
	$.subscribe('reloadTableColumns', function() {
		var entity =  $("#entitySelect").val();
		$.getJSON("/desglosa-web/json_p_loadTableColumns.action",
				{ entity: entity },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						$("#entityColumnsDiv").html("");
						$.each(data.metaclass.tableTypes, function (key, value) {
							htmlText += "<a href='#' id='col_" + key + "' class='myButton' onclick='javascript:selectColumn(this)'>" + key + " (" + value + ")</a><br />";
						});
						$("#entityColumnsDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
		});
	});
	
	var selectedColumn = null;
	var selectedAttribute = null;
	
	function selectColumn(element) {
		if (selectedColumn != element) {
			if (selectedColumn != null) {
				selectedColumn.className = "myButton"
				selectedColumn = null;
			}
			selectedColumn = element;
			element.className = "myPressedButton";
		} else {
			selectedColumn = null;
			element.className = "myButton";
		}
	}
	
	$(document).ready(function() {
	});
	</script>
</head>

<body>
	<s:form id="configureForm" action="json_p_updateProfileForm">
		<s:url id="updateProfileURL" action="json_p_updateProfileForm"/>
		<s:url id="refreshTableColumns" action="json_p_loadTableColumns"/>
		
		<sj:select 	href="%{updateProfileURL}"
					emptyOption="false"
					headerKey="-1"
					headerValue="-- Please select an entity --"
					disabled="option:first"
					id="entitySelect"
					name="entity"
					list="entities"
					onAlwaysTopics="disableHeader"
					onChangeTopics="reloadTableColumns"/>
		
		<sj:div id="entityColumnsDiv"/>
					
		<sj:select 	href="%{updateProfileURL}"
					emptyOption="false"
					headerKey="-1"
					headerValue="-- Please select a model --"
					id="modelSelect"
					name="model"
					list="models"
					formIds="configureForm"
					onAlwaysTopics="disableHeader"
					onChangeTopics="reloadClassAttributes"/>
	</s:form>
</body>
</html>