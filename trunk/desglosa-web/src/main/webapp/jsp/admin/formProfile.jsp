<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageProfiles"/>
	<link href="<s:url value='/styles/buttons.css'/>" rel="stylesheet" type="text/css" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/colorpicker.css'/>" />
	<script type="text/javascript" src="<s:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/layout.css'/>" />
	
	<script type="text/javascript">
	var associations = new Array();
	var captionLines = new Array();
	
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
	
	function CaptionLine (label, hexColor) {
		this.label = label;
		this.color = hexColor;
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
						$.each(data.tableColumns, function (key, value) {
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
						$.each(data.classAttributes, function (key, value) {
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
		$("#mapping_messages").html("");
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
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
				if (attributeType == "range") {
					var range = true;
					if (columnType == "string" || columnType == "boolean") range = false;
					addRangeConfigurationLine(range);
					$("#mapping_control").append("<a href='javascript:addRangeConfigurationLine(" + range + ")'>+</a>");
				} else if (attributeType == "color") {
					var range = true;
					if (columnType == "string" || columnType == "boolean") range = false;
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
					var value = null;
					if (attributeType == "color") {
						value = rgb2hex($(element).children('.colorSelector').children('div').css('backgroundColor'));
					} else if (attributeType == "range") {
						value = $(element).children('#value').val();
					}
					if (columnType == "int") {
						low = parseInt(low, 10);
						high = parseInt(high, 10);
						if (attributeType == "range") value = parseInt(value, 10);
					} else if (columnType == "float") {
						low = parseFloat(low, 10);
						high = parseFloat(high, 10);
						if (attributeType == "range") value = parseFloat(value, 10);
					} else if (columnType == "string") {
						high = low;
					}
					if ((((columnType == "int" || columnType == "float") && attributeType == "color") && (isNaN(low) || isNaN(high)))
							|| (((columnType == "int" || columnType == "float") && attributeType == "range") && (isNaN(low) || isNaN(high) || isNaN(value)))) {
						$("#mapping_messages").html("");
						$("#mapping_messages").html("<s:text name='error.field_isNaN'/>");
						error = true;
						// Resetear tablas de valores
						rules = new Array();
					} else {
						// Actualizar tablas de valores para construir el fichero XML
						rule = new Rule(low, high, value);
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
				$("#mapping_added").append("<div class='association_line' style='display: block;'>");
				$(".association_line:last").append("<div class='col_field' style='float: left;'>" + columnName + "</div>");
				$(".association_line:last").append("<div class='attr_field' style='float: left;'>" + attributeName + "</div>");
				if (rules.length > 0) {
					$(".association_line:last").append("<div class='association_rules' style='float: left;'>");
					$.each(rules, function(index, element) {
						$(".association_rules:last").append("<div class='association_rule' style='display: block;'>");
						$(".association_rule:last").append("<div class='rule_low' style='display: inline;'>" + element.low + "</div>");
						$(".association_rule:last").append("<div class='rule_high' style='display: inline;'>" + element.high + "</div>");
						$(".association_rule:last").append("<div class='rule_value' style='display: inline;'>" + element.value + "</div>");
					});
					$(".association_rules:last").append("</div>");
				}
				$(".association_line:last").append("<div class='remove_association' style='float: left;'>removeIcon</div>");
				$("#mapping_added").append("</div>");
				$("#mapping_added").append("<div style='clear:both;'></div>");
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
		var clazz = "cfg_line";
		var lineId = clazz + lineCounter;
		var lineSelector = "#" + lineId;
		$("#mapping_cfg").append("<div id='" + lineId + "' class='" + clazz + "'>");
		$(lineSelector).append("<input type='text' id='low' name='low' style='float: left;'/>");
		if (!range) $(lineSelector).append("<input type='text' id='high' name='high' style='display: none;'/>");
		else $(lineSelector).append("<input type='text' id='high' name='high' style='float: left;'/>");
		$(lineSelector).append("<input type='text' id='value' name='value' style='float: left;'/>");
		$(lineSelector).append("<a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'>-</a>");
		$(lineSelector).append("<div style='clear:both;'></div>");
		$("#mapping_cfg").append("</div>");
	}
	
	function addColorConfigurationLine(range) {
		lineCounter++;
		var clazz = "cfg_line";
		var lineId = clazz + lineCounter;
		var lineSelector = "#" + lineId;
		$("#mapping_cfg").append("<div id='" + lineId + "' class='" + clazz + "'>");
		$(lineSelector).append("<input type='text' id='low' name='low' style='float: left;'/>");
		if (!range) $(lineSelector).append("<input type='text' id='high' name='high' style='display: none;'/>");
		else $(lineSelector).append("<input type='text' id='high' name='high' style='float: left;'/>");
		createColorPicker(lineSelector);
		$(lineSelector).append("<a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'>-</a>");
		$(lineSelector).append("<div style='clear:both;'></div>");
		$("#mapping_cfg").append("</div>");
	}
	
	function removeInputLine(id) {
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
		$(divSelector).append("<div id='" + id + "' class='colorSelector' style='float: left;'><div style='background-color: " + initialColor + ";'></div></div>");
		
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
	
	function addCaptionLine(){
		lineCounter++;
		var clazz = "caption_line";
		var lineId = clazz + lineCounter;
		var lineSelector = "#" + lineId;
		$("#added_captionLines").append("<div id='" + lineId + "' class='" + clazz + "'>");
		createColorPicker(lineSelector);
		$(lineSelector).append("<input type='text' id='text' name='text' style='float: left;'/>");
		$(lineSelector).append("<a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'>-</a>");
		$(lineSelector).append("<div style='clear:both;'></div>");
		$("#added_captionLines").append("</div>");
	}
	
	function resetProfileForm() {
		captionLines = new Array();
		$("#added_captionLines > .caption_line").each(function(index, element) {
			var label = $(element).children("#text").val();
			if (label != "") {
				// reset css class
			}
		});
	}
	
	function checkProfile() {
		var noError = true;
		resetProfileForm();
		// Comprobar que hay asociaciones hechas
		if (associations.length < 1) {
			$("#mapping_messages").html("<s:text name='error.empty_associations'/>");
			swapDivVisibility('second_step','first_step');
			noError = false;
		} else {
			// Comprobar que no hay caption lines sin texto
			$("#added_captionLines > .caption_line").each(function(index, element) {
				var label = $(element).children("#text").val();
				var hexColor = rgb2hex($(element).children('.colorSelector').children('div').css('backgroundColor'));
				if (label == "") {
					// change css class
					alert("change css class");
					noError = false;
				} else {
					var cl = new CaptionLine(label, hexColor);
					captionLines.push(cl);
				}
			});
		}
		return noError;
	}
	
	function saveProfile() {
		if (checkProfile() == true) {
			var jsonCaptionLines = JSON.stringify(captionLines);
			var jsonAssociations = JSON.stringify(associations);
			$.post ("/desglosa-web/saveProfile.action",
				{ model: $("#modelSelect").val(),
				  entity: $("#entitySelect").val(),
				  jsonCaptionLines: jsonCaptionLines,
				  jsonAssociations: jsonAssociations },
				function(data, status) {
					if (status == "success") {
						alert("success");
					} else {
						alert("error");
					}
			});
		} else {
			
		}
	}

	</script>
</head>

<body>
	<div id="first_step" style="display:;">
		<s:url id="updateProfileURL" action="json_p_updateProfileForm"/>
		<s:url id="refreshTableColumns" action="json_p_loadTableColumns"/>
		
		<div id="panes" style="float:left;">
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
							onAlwaysTopics="disableHeader"
							onChangeTopics="reloadClassAttributes"/>
				<div id="classAttributesDiv"></div>
			</div>
		
			<div style="clear:both;"></div>
		</div>
		
		<div id="mapping" style="float:left;">
			<div id="mapping_added"></div>
			<div id="mapping_messages"></div>
			<div id="mapping_cfg"></div>
			<div id="mapping_control"></div>
		</div>
		
		<div style="clear:both;"></div>
		<a href="javascript:swapDivVisibility('first_step','second_step')">Next &gt;</a>
	</div>
	
	<div id="second_step" style="display: none;">
		<s:text name="label.configure_caption_lines"/>
		<div id="added_captionLines"></div>
		<a href="javascript:addCaptionLine()"><s:text name="label.add_caption_line"/></a>
		<label for="profileName"><s:text name="label.profile_name"/></label>
		<input id="profileName" name="profileName" type="text"/>
		<label for="profileDescription"><s:text name="label.profile_description"/></label>
		<textarea id="profileDescription" name="profileDescription" rows="3" cols="15"></textarea>
		<a href="javascript:swapDivVisibility('second_step','first_step')">&lt; Back</a>
		<a href="javascript:saveProfile()">Save profile</a>
	</div>
</body>
</html>