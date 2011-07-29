<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageProfiles"/>
	<link href="<s:url value='/styles/buttons.css'/>" rel="stylesheet" type="text/css" />
	
	<sj:head jqueryui="true"/>
	
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/colorpicker.css'/>" />
	<script type="text/javascript" src="<s:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/layout.css'/>" />
	
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
						columnArray = new Array();
						$("#entityColumnsDiv").html("");
						$.each(data.metaclass.tableTypes, function (key, value) {
							columnArray[key] = value;
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
						attributeArray = new Array();
						$("#classAttributesDiv").html("");
						$.each(data.metaclass.classTypes, function (key, value) {
							attributeArray[key] = value;
							htmlText += "<a id='attr_" + key + "' class='myButton' onclick='javascript:selectAttribute(this)'>" + key + " (" + value + ")</a><br />";
						});
						$("#classAttributesDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
		});
	});
	
	var selectedColumn = null;
	var columnArray = new Array();
	var selectedAttribute = null;
	var attributeArray = new Array();
	
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
			var columnType = columnArray[column];
			var attribute = (selectedAttribute.id).replace("attr_", "");
			var attributeType = attributeArray[attribute];
			var compatibility = checkTypeCompatibility(columnType, attributeType);
			if (compatibility == -1) {
				$("#mapping_messages").html("<s:text name='error.type_compatibility'/>");
			} else if (compatibility == 0) {
				$("#mapping_messages").html("<s:text name='warning.type_compatibility'/>");
			} else { // compatible types
				$("#mapping_messages").html("");
				$("#mapping_cfg").html("");
				if (attributeType == "range") {
					addRangeConfigurationLine();
				} else if (attributeType == "color") {
					addColorConfigurationLine();
				}
				// asociacion directa
			}
		}
	}
	
	var lineCounter = 0;
	function addRangeConfigurationLine() {
		
	}
	
	function addColorConfigurationLine() {
		lineCounter++;
		var lineId = "cfg_line" + lineCounter;
		var lineSelector = "#" + lineId;
		$("#mapping_cfg").append("<div id='" + lineId + "'>");
		createColorPicker(lineSelector);
		$(lineSelector).append("<a href='javascript:addColorConfigurationLine()'>+</a>");
		$("#mapping_cfg").append("</div>");
	}
	
	function checkTypeCompatibility(type1, type2) {
		// -1 means no compatible
		// 0 means compatible but with warnings
		// 1 means compatible
		var compatible = -1;
		if (type1 == type2) {
			compatible = 1;
		} else {
			if (type1 == "int") {
				if (type2 == "float" || type2 == "double" || type2 == "range" || type2 == "color") compatible = 1;
			} else if (type1 == "float") {
				if (type2 == "double" || type2 == "range" || type2 == "color") compatible = 1;
				else if (type2 == "int") compatible = 0;
			} else if (type1 == "double") {
				if (type2 == "range" || type2 == "color") compatible = 1;
				else if (type2 == "int" || type1 == "float") compatible = 0;
			} else if (type1 == "string") {
				if (type2 == "range" || type2 == "color") compatible = 1;
			} else if (type1 == "boolean") {
				if (type2 == "range" || type2 == "color") compatible = 1;
			}
		}
		return compatible;
	}
	
	var colorPickerCounter = 0;
	function createColorPicker(divSelector) {
		colorPickerCounter++;
		var id = "colorPicker" + colorPickerCounter;
		var selector = "#" + id;
		var divId = selector + " div";
		var initialColor = "#0000ff";
		$(divSelector).append("<div id='" + id + "' class='colorSelector'><div style='background-color: " + initialColor + ";'></div></div>");
		
		$(selector).ColorPicker({
			color: initialColor,
			onShow: function (colpkr) {
				$(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$(divId).css('backgroundColor', '#' + hex);
			},
			onSubmit: function(hsb, hex, rgb, el) {
				$(el).val(hex);
				$(el).ColorPickerHide();
			}
		});
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
			<div id="mapping_messages"></div>
			<div id="mapping_cfg"></div>
			<div id="mapping_added"></div>
		</div>
	</form>
</body>
</html>