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
	var associations = new Array();
	
	function Association (colName, colType, attrName, attrType, rules) {
		this.columnName = colName;
		this.columnType = colType;
		this.attributeName = attrName;
		this.attributeType = attrType;
		this.rules = rules;
	}
	
	function Rule (low, high, value) {
		this.low = low;
		this.high = high;
		this.value = value;
	}
	
	$.subscribe('disableHeader', function(event, element) {
		$("option:first", element).attr('disabled','disabled');
	});
	
	$.subscribe('reloadTableColumns', function() {
		var entity =  $("#entitySelect").val();
		associations = new Array();
		$.getJSON("/desglosa-web/json_p_loadTableColumns.action",
				{ entity: entity },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						columnArray = new Array();
						$("#entityColumnsDiv").html("");
						$.each(data.metaclass.tableTypes, function (key, value) {
							columnArray[key] = value;
							htmlText += "<a id='col_" + key + "' class='myButton' onclick='javascript:selectColumn(this)' style='display: block;'>" + key + " (" + value + ")</a>";
						});
						$("#entityColumnsDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve factory information: ' + status);
		});
	});
	
	$.subscribe('reloadClassAttributes', function() {
		var model =  $("#modelSelect").val();
		associations = new Array();
		$.getJSON("/desglosa-web/json_p_loadClassAttributes.action",
				{ model: model },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						attributeArray = new Array();
						$("#classAttributesDiv").html("");
						$.each(data.metaclass.classTypes, function (key, value) {
							attributeArray[key] = value;
							htmlText += "<a id='attr_" + key + "' class='myButton' onclick='javascript:selectAttribute(this)' style='display: block;'>" + key + " (" + value + ")</a>";
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
				$("#mapping_control").html("");
				if (attributeType == "range") {
					var range = true;
					if (columnType == "string") range = false;
					addRangeConfigurationLine(range);
					$("#mapping_control").append("<a href='javascript:addRangeConfigurationLine(" + range + ")'>+</a>");
				} else if (attributeType == "color") {
					var range = true;
					if (columnType == "string") range = false;
					addColorConfigurationLine(range);
					$("#mapping_control").append("<a href='javascript:addColorConfigurationLine(" + range + ")'>+</a>");
				}
				// Si es mapeo directo no hay que ahcer nada más
				$("#mapping_control").append("<a href='javascript:saveMapping()'><s:text name='label.save_mapping'/></a>");
			}
		}
	}
	
	function saveMapping() {
		if (selectedColumn != null && selectedAttribute != null) {
			var columnName = (selectedColumn.id).replace("col_", "");
			var columnType = columnArray[columnName];
			var attributeName = (selectedAttribute.id).replace("attr_", "");
			var attributeType = attributeArray[attributeName];
			// Recorrer todas las lineas para comprobar que estan bien configuradas
			// Si es asociacion directa no habrá ninguna línea que comprobar, si es range o color, puede haber varias
			// El valor de los textboxes será del tipo de la columna de la tabla
			var error = false;
			var rules = new Array();
			if (attributeType == "range" || attributeType == "color") {
				$("#mapping_cfg").children().each(function(index, element) {
					// element es cfg_line1
					var low = $(element).children('#low').val();
					var high = $(element).children('#high').val();
					var color = rgb2hex($(element).children('.colorSelector').children('div').css('backgroundColor'));
					if (columnType == "int") {
						low = parseInt(low, 10);
						high = parseInt(high, 10);
					} else if (columnType == "float") {
						low = parseFloat(low, 10);
						high = parseFloat(high, 10);
					} else if (columnType == "string") {
						high = low;
					}
					if ((columnType == "int" || columnType == "float") && (isNaN(low) || isNaN(high))) {
						$("#mapping_messages").html("");
						$("#mapping_messages").html("<s:text name='error.field_isNaN'/>");
						error = true;
						// Resetear tablas de valores
						rules = new Array();
					} else {
						// Actualizar tablas de valores para construir el fichero XML
						rule = new Rule(low, high, color);
						rules.push(rule);
					}
				});
			}
			if (!error) {
				$("#mapping_messages").html("");
				// Si no hay error, establecemos al asociacion con sus reglas
				var association = new Association(columnName, columnType, attributeName, attributeType, rules);
				associations.push(association);
				// Ocultar los botones correspondientes a la columna de la tabla y el atributo de la clase
				$("#"+selectedColumn.id).css('display','none');
				$("#"+selectedAttribute.id).css('display','none');
				// Resetear selectedColumn y selectedAttribute
				selectedColumn = null;
				selectedAttribute = null;
				// Resetear el contador de lineas
				lineCounter = 0;
				// Resetear mapping_cfg y mapping_control
				$("#mapping_cfg").html("");
				$("#mapping_control").html("");
				// Feedback en mapping_added
				$("#mapping_added").append("<div id='association_line' style='display: block;'>");
				$("#association_line").append("<div id='col_field' style='display: inline;'>" + columnName + "</div>");
				$("#association_line").append("<div id='attr_field' style='display: inline;'>" + attributeName + "</div>");
				if (rules.length > 0) {
					$("#association_line").append("<div id='association_rules' style='display: inline;'>");
					$.each(rules, function(index, element) {
						$("#association_rules").append("<div id='association_rule' style='display: block;'>");
						$("#association_rule").append("<div id='rule_low' style='display: inline;'>" + element.low + "</div>");
						$("#association_rule").append("<div id='rule_high' style='display: inline;'>" + element.high + "</div>");
						$("#association_rule").append("<div id='rule_value' style='display: inline;'>" + element.value + "</div>");
					});
					$("#association_rules").append("</div>");
				}

				$("#association_line").append("<div id='remove_association' style='display: inline;'>removeIcon</div>");
				$("#mapping_added").append("</div>");
				// Feedback en mapping_messages
				$("#mapping_messages").html("<s:text name='message.association_successful'/>");
			}
		}
	}
	
	function rgb2hex (rgbString) {
		// http://stackoverflow.com/questions/638948/background-color-hex-to-javascript-variable-jquery
		// x must be in "rgb(0, 70, 255)" format
		var parts = rgbString.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
		// parts now should be ["rgb(0, 70, 255", "0", "70", "255"]
		delete (parts[0]);
		for (var i = 1; i <= 3; ++i) {
		    parts[i] = ("0" + parseInt(parts[i]).toString(16)).slice(-2);
		}
		var hexString = parts.join(''); // "0070ff"
		return hexString;
	}
	
	var lineCounter = 0;
	
	function addRangeConfigurationLine(range) {
		lineCounter++;
		var lineId = "cfg_line" + lineCounter;
		var lineSelector = "#" + lineId;
		$("#mapping_cfg").append("<div id='" + lineId + "'>");
		$(lineSelector).append("<input type='text' id='low' name='low'/>");
		if (!range) $(lineSelector).append("<input type='text' id='high' name='high' style='display: none;'/>");
		else $(lineSelector).append("<input type='text' id='high' name='high'/>");
		$(lineSelector).append("<input type='text' id='value' name='value'/>");
		$(lineSelector).append("<a href=\"javascript:removeConfigurationLine('" + lineId + "')\">-</a>");
		$("#mapping_cfg").append("</div>");
	}
	
	function addColorConfigurationLine(range) {
		lineCounter++;
		var lineId = "cfg_line" + lineCounter;
		var lineSelector = "#" + lineId;
		$("#mapping_cfg").append("<div id='" + lineId + "'>");
		$(lineSelector).append("<input type='text' id='low' name='low'/>");
		if (!range) $(lineSelector).append("<input type='text' id='high' name='high' style='display: none;'/>");
		else $(lineSelector).append("<input type='text' id='high' name='high'/>");
		createColorPicker(lineSelector);
		$(lineSelector).append("<a href=\"javascript:removeConfigurationLine('" + lineId + "')\">-</a>");
		$("#mapping_cfg").append("</div>");
	}
	
	function removeConfigurationLine(id) {
		$("#"+id).remove();
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
			<div id="mapping_control"></div>
			<div id="mapping_added"></div>
		</div>
	</form>
</body>
</html>