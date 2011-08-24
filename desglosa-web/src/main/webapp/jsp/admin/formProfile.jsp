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
	var mappings = new Array();
	var captionLines = new Array();
	
	function Mapping (entityAttrName, entityAttrType, modelAttrName, modelAttrType, rules) {
		this.entityAttrName = entityAttrName;
		this.entityAttrType = entityAttrType;
		this.modelAttrName = modelAttrName;
		this.modelAttrType = modelAttrType;
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
	
	$.subscribe('reloadEntityAttributes', function() {
		var entity =  $("#entitySelect").val();
		mappings = new Array();
		$.getJSON("/desglosa-web/json_p_loadEntityAttributes.action",
				{ entity: entity },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						entityAttributesArray = new Array();
						$("#entityAttributesDiv").html("");
						$.each(data.entityAttributes, function (key, value) {
							entityAttributesArray[value.name] = value.type;
							htmlText += "<a id='entityAttr_" + value.name + "' class='myButton' onclick='javascript:selectEntityAttribute(this)' style='display: block;' title='" + value.description + "'>" + value.name + " (" + value.type + ")</a>";
						});
						$("#entityAttributesDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve entity information: ' + status);
		});
	});
	
	$.subscribe('reloadModelAttributes', function() {
		var model =  $("#modelSelect").val();
		mappings = new Array();
		$.getJSON("/desglosa-web/json_p_loadModelAttributes.action",
				{ model: model },
				function (data, status) {
					if (status == "success") {
						var htmlText = "";
						modelAttributesArray = new Array();
						$("#modelAttributesDiv").html("");
						$.each(data.modelAttributes, function (key, value) {
							modelAttributesArray[key] = value;
							htmlText += "<a id='modelAttr_" + key + "' class='myButton' onclick='javascript:selectModelAttribute(this)' style='display: block;'>" + key + " (" + value + ")</a>";
						});
						$("#modelAttributesDiv").html(htmlText);
					}
					else alert('An error has occurred while trying to retrieve model information: ' + status);
		});
	});
	
	var selectedEntityAttribute = null;
	var entityAttributesArray = new Array();
	var selectedModelAttribute = null;
	var modelAttributesArray = new Array();
	
	function selectEntityAttribute(element) {
		// if already-selected element is not the newly-selected
		if (selectedEntityAttribute != element) {
			// check if there was a selected element and pop it
			if (selectedEntityAttribute != null) {
				selectedEntityAttribute.className = "myButton"
				selectedEntityAttribute = null;
			}
			// push the new element
			selectedEntityAttribute = element;
			element.className = "myPressedButton";
			checkMapping();
		} else {
			// if already-selected element and newly-selected are the same button, pop it
			selectedEntityAttribute.className = "myButton";
			selectedEntityAttribute = null;
		}
	}
	
	function selectModelAttribute(element) {
		// if already-selected element is not the newly-selected
		if (selectedModelAttribute != element) {
			// check if there was a selected element and pop it
			if (selectedModelAttribute != null) {
				selectedModelAttribute.className = "myButton"
				selectedModelAttribute = null;
			}
			// push the new element
			selectedModelAttribute = element;
			element.className = "myPressedButton";
			checkMapping();
		} else {
			// if already-selected element and newly-selected are the same button, pop it
			selectedModelAttribute.className = "myButton";
			selectedModelAttribute = null;
		}
	}
	
	function checkMapping() {
		$("#mapping_messages").html("");
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
		if (selectedEntityAttribute != null && selectedModelAttribute != null) {
			var entityAttrName = (selectedEntityAttribute.id).replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = (selectedModelAttribute.id).replace("modelAttr_", "");
			var modelAttrType = modelAttributesArray[modelAttrName];
			var compatibility = checkTypeCompatibility(entityAttrType, modelAttrType);
			if (compatibility == -1) {
				$("#mapping_messages").html("<s:text name='error.type_compatibility'/>");
			} else if (compatibility == 0) {
				$("#mapping_messages").html("<s:text name='warning.type_compatibility'/>");
			} else { // compatible types
				if (modelAttrType == "float_range") {
					var range = true;
					if (entityAttrType == "string" || entityAttrType == "boolean") range = false;
					addRangeConfigurationLine(range);
					$("#mapping_control").append("<a href='javascript:addRangeConfigurationLine(" + range + ")'>+</a>");
				} else if (modelAttrType == "color") {
					// if entity attr type is color in hex format, it is a direct mapping
					if (entityAttrType != "hexcolor") {
						var range = true;
						if (entityAttrType == "string" || entityAttrType == "boolean") range = false;
						addColorConfigurationLine(range);
						$("#mapping_control").append("<a href='javascript:addColorConfigurationLine(" + range + ")'>+</a>");	
					}
				}
				// Si es mapeo directo no hay que ahcer nada más
				$("#mapping_control").append("<a href='javascript:saveMapping()'><s:text name='label.save_mapping'/></a>");
			}
		}
	}
	
	function saveMapping() {
		if (selectedEntityAttribute != null && selectedModelAttribute != null) {
			var entityAttrName = (selectedEntityAttribute.id).replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = (selectedModelAttribute.id).replace("modelAttr_", "");
			var modelAttrType = modelAttributesArray[modelAttrName];
			// Recorrer todas las lineas para comprobar que estan bien configuradas
			// Si es asociacion directa no habrá ninguna línea que comprobar, si es range o color, puede haber varias
			// El valor de los textboxes será del tipo de la columna de la tabla
			var error = false;
			var rules = new Array();
			// if entityAttrType == "hexcolor" then direct mapping because it is in hex format
			if (entityAttrType != "hexcolor" && (modelAttrType == "float_range" || modelAttrType == "color")) {
				$("#mapping_cfg").children().each(function(index, element) {
					// element es cfg_line1
					var low = $(element).children('#low').val();
					var high = $(element).children('#high').val();
					var value = null;
					if (modelAttrType == "color") {
						value = rgb2hex($(element).children('.colorSelector').children('div').css('backgroundColor'));
					} else if (modelAttrType == "float_range") {
						value = $(element).children('#value').val();
					}
					if (entityAttrType == "int") {
						low = parseInt(low, 10);
						high = parseInt(high, 10);
						if (modelAttrType == "float_range") value = parseInt(value, 10);
					} else if (entityAttrType == "float") {
						low = parseFloat(low, 10);
						high = parseFloat(high, 10);
						if (modelAttrType == "float_range") value = parseFloat(value, 10);
					} else if (entityAttrType == "string") {
						high = low;
					}
					if ((((entityAttrType == "int" || entityAttrType == "float") && modelAttrType == "color") && (isNaN(low) || isNaN(high)))
							|| (((entityAttrType == "int" || entityAttrType == "float") && modelAttrType == "float_range") && (isNaN(low) || isNaN(high) || isNaN(value)))) {
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
				var mapping = new Mapping(entityAttrName, entityAttrType, modelAttrName, modelAttrType, rules);
				mappings.push(mapping);
				// Ocultar los botones correspondientes a la columna de la tabla y el atributo de la clase
				$("#"+selectedEntityAttribute.id).css('display','none');
				$("#"+selectedModelAttribute.id).css('display','none');
				// Resetear selectedColumn y selectedAttribute
				selectedEntityAttribute = null;
				selectedModelAttribute = null;
				// Resetear el contador de lineas
				lineCounter = 0;
				// Resetear mapping_cfg y mapping_control
				$("#mapping_cfg").html("");
				$("#mapping_control").html("");
				// Feedback en mapping_added
				$("#mapping_added").append("<div class='mapping_line' style='display: block;'>");
				$(".mapping_line:last").append("<div class='entityAttr_field' style='float: left;'>" + entityAttrName + "</div>");
				$(".mapping_line:last").append("<div class='modelAttr_field' style='float: left;'>" + modelAttrName + "</div>");
				if (rules.length > 0) {
					$(".mapping_line:last").append("<div class='mapping_rules' style='float: left;'>");
					$.each(rules, function(index, element) {
						$(".mapping_rules:last").append("<div class='mapping_rule' style='display: block;'>");
						$(".mapping_rule:last").append("<div class='rule_low' style='display: inline;'>" + element.low + "</div>");
						$(".mapping_rule:last").append("<div class='rule_high' style='display: inline;'>" + element.high + "</div>");
						$(".mapping_rule:last").append("<div class='rule_value' style='display: inline;'>" + element.value + "</div>");
					});
					$(".mapping_rules:last").append("</div>");
				}
				$(".mapping_line:last").append("<div class='remove_mapping' style='float: left;'>removeIcon</div>");
				$("#mapping_added").append("</div>");
				$("#mapping_added").append("<div style='clear:both;'></div>");
				// Feedback en mapping_messages
				$("#mapping_messages").html("<s:text name='message.mapping_successful'/>");
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
			if (type1 == "hexcolor" && type2 == "color") compatible = 1;
			else if (type1 == "int") {
				if (type2 == "float" || type2 == "double" || type2 == "float_range" || type2 == "color") compatible = 1;
			} else if (type1 == "float") {
				if (type2 == "double" || type2 == "float_range" || type2 == "color") compatible = 1;
				else if (type2 == "int") compatible = 0;
			} else if (type1 == "double") {
				if (type2 == "float_range" || type2 == "color") compatible = 1;
				else if (type2 == "int" || type1 == "float") compatible = 0;
			} else if (type1 == "string") {
				if (type2 == "float_range" || type2 == "color") compatible = 1;
			} else if (type1 == "boolean") {
				if (type2 == "float_range" || type2 == "color") compatible = 1;
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
		if (mappings.length < 1) {
			$("#mapping_messages").html("<s:text name='error.empty_mapping'/>");
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
			// Comprobar que profileName y profileDescription no están vacíos
			if ($("#profileName").val() == "") {
				alert("profile name vacio");
				noError = false;
			}
			if ($("#profileDescription").val() == "") {
				alert("profile description vacio");
				noError = false;
			}
		}
		return noError;
	}
	
	function saveProfile() {
		if (checkProfile() == true) {
			var jsonCaptionLines = JSON.stringify(captionLines);
			var jsonMappings = JSON.stringify(mappings);
			$.post ("/desglosa-web/saveProfile.action",
				{ profileName: $("#profileName").val(),
				  profileDescription: $("#profileDescription").val(),
				  model: $("#modelSelect").val(),
				  entity: $("#entitySelect").val(),
				  jsonCaptionLines: jsonCaptionLines,
				  jsonMappings: jsonMappings
				},
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
							onChangeTopics="reloadEntityAttributes"/>
				<div id="entityAttributesDiv"></div>
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
							onChangeTopics="reloadModelAttributes"/>
				<div id="modelAttributesDiv"></div>
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