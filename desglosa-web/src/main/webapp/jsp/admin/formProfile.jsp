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
							htmlText += "<a id='col_" + key + "' class='myButton' onclick='javascript:selectColumn(this)'>" + key + " (" + value + ")</a><br />";
						});
						$("#entityColumnsDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
		});
	});
	
	$.subscribe('reloadClassAttributes', function() {
		var model =  $("#modelSelect").val();
		$.getJSON("/desglosa-web/json_p_loadClassAttributes.action",
				{ model: model },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						$("#classAttributesDiv").html("");
						$.each(data.metaclass.classTypes, function (key, value) {
							htmlText += "<a id='attr_" + key + "' class='myButton' onclick='javascript:selectAttribute(this)'>" + key + " (" + value + ")</a><br />";
						});
						$("#classAttributesDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
		});
	});
	
	var selectedColumn = null;
	var selectedAttribute = null;
	
	function selectColumn(element) {
		// if already-selected element is not the newly-selected
		if (selectedColumn != element) {
			// check if there was a selected element and pop it
			if (selectedColumn != null) {
				selectedColumn.className = "myButton"
				selectedColumn = null;
			}
			// push the new element
			selectedColumn = element;
			element.className = "myPressedButton";
			checkMapping();
		} else {
			// if already-selected element and newly-selected are the same button, pop it
			selectedColumn.className = "myButton";
			selectedColumn = null;
		}
	}
	
	function selectAttribute(element) {
		// if already-selected element is not the newly-selected
		if (selectedAttribute != element) {
			// check if there was a selected element and pop it
			if (selectedAttribute != null) {
				selectedAttribute.className = "myButton"
				selectedAttribute = null;
			}
			// push the new element
			selectedAttribute = element;
			element.className = "myPressedButton";
			checkMapping();
		} else {
			// if already-selected element and newly-selected are the same button, pop it
			selectedAttribute.className = "myButton";
			selectedAttribute = null;
		}
	}
	
	function checkMapping() {
		if (selectedColumn != null && selectedAttribute != null) {
			var column = (selectedColumn.id).replace("col_", "");
			var attribute = (selectedAttribute.id).replace("attr_", "");
			
		}
	}
	
	$(document).ready(function() {
	});
	</script>
</head>

<body>
	<form id="configureForm" action="json_p_updateProfileForm">
		<s:url id="updateProfileURL" action="json_p_updateProfileForm"/>
		<s:url id="refreshTableColumns" action="json_p_loadTableColumns"/>
		
		<div id="leftPane" style="float:left;">
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
			<div id="entityColumnsDiv"></div>
		</div>
		
		<div id="rightPane" style="float:left;">
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
			<div id="classAttributesDiv"></div>
		</div>
		
		<div style="clear:both;"></div>
		
		<div id="mapping">
			
		</div>
	</form>
</body>
</html>