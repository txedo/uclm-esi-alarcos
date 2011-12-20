<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file='/common/dialogs.jsp' %>

<html lang="en">
<head>
	<fmt:message key="label.range.low" var="rangeLow"/>
	<fmt:message key="label.range.high" var="rangeHigh"/>
	<fmt:message key="label.range.value" var="rangeValue"/>
	<fmt:message key="label.range.expectedValue" var="expectedValue"/>
	<fmt:message key="label.range.color" var="color"/>
	<fmt:message key="label.ratio" var="ratioValue"/>
	<fmt:message key="message.noExplanation" var="noExplanation"/>
	<fmt:message key="message.ratio.explanation" var="ratioExplanation"/>
	<fmt:message key="message.range.explanation" var="rangeExplanation"/>
	<fmt:message key="message.range.expectedValueExplanation" var="expectedValueExplanation"/>
	<fmt:message key="error.profileCreation" var="profileCreationErrors"/>

	<meta name="menu" content="ManageProfiles"/>
	
	<link href="<s:url value='/styles/style.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/profile.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/buttons.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/js/colorpicker/css/colorpicker.css?version=1'/>" rel="stylesheet" type="text/css" media="screen" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="<s:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	
	<script type="text/javascript">
	var mappings = new Array();
	var captionLines = new Array();
	
	function Attribute (name, type) {
		this.name = name;
		this.type = type;
	}
	
	function Mapping (entityAttr, modelAttr, ratio, rules) {
		this.entityAttribute = entityAttr;
		this.modelAttribute = modelAttr;
		this.ratio = ratio;
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
		selectedEntityAttribute = null;
		resetMessageAndControlDivs();
		var entity =  $("#entitySelect").val();
		if (mappings.length > 0) {
			resetMappings();
			var model =  $("#modelSelect").val();
			if (model != -1) {
				$.publish('reloadModelAttributes');
			}
		}
		$.getJSON("/desglosa-web/json_p_loadEntityAttributes.action",
				{ entity: entity },
				function (data, status) {
					if (status == "success") {
						entityAttributesArray = new Array();
						$("#entityAttributesDiv").html("");
						$("#entityAttributesDiv").append("<ul>");
						$.each(data.entityAttributes, function (key, value) {
							entityAttributesArray[value.name] = value.type;
							$("#entityAttributesDiv ul").append("<li id='entityAttr_" + value.name + "' class='selectablelist ui-corner-all' title='" + value.description + "'>" + (value.name).split('_').join(' ') + " (" + value.type + ")</li>");
						});
						$("#entityAttributesDiv").append("</ul>");
					}
					else alert('An error has occurred while trying to retrieve entity information: ' + status);
		});
	});
	
	$.subscribe('reloadModelAttributes', function() {
		selectedModelAttribute = null;
		resetMessageAndControlDivs();
		var model =  $("#modelSelect").val();
		if (mappings.length > 0) {
			resetMappings();
			var entity =  $("#entitySelect").val();
			if (entity != -1) {
				$.publish('reloadEntityAttributes');
			}
		}
		$.getJSON("/desglosa-web/json_p_loadModelAttributes.action",
				{ model: model },
				function (data, status) {
					if (status == "success") {
						modelAttributesArray = new Array();
						$("#modelAttributesDiv").html("");
						$("#modelAttributesDiv").append("<ul>");
						$.each(data.modelAttributes, function (key, value) {
							modelAttributesArray[key] = value;
							$("#modelAttributesDiv ul").append("<li id='modelAttr_" + key + "' class='selectablelist ui-corner-all'>" + key + " (" + value + ")</li>");
						});
						$("#modelAttributesDiv").append("</ul>");
					}
					else alert('An error has occurred while trying to retrieve model information: ' + status);
		});
	});
	
	function resetMessageAndControlDivs() {
		$("#mapping_messages").html("");
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
	}
	
	function resetMappings() {
		selectedEntityAttribute = null;
		$("#entityAttributesDiv").children('ul').children('li').removeClass('ui-selected');
		selectedModelAttribute = null;
		$("#modelAttributesDiv").children('ul').children('li').removeClass('ui-selected');
		// reset mappings array
		mappings = new Array();
		// clean mappings table
		$(".mapping_line").remove();
		// clean mappings messages
		resetMessageAndControlDivs();
		// restablish no mappings info messag
		$("#mapping_added tfoot").html("<tr>");
		$("#mapping_added tfoot tr:last").append("<td colspan='7'>");
		$("#mapping_added tfoot tr:last td:last").append("<span class='messageBox info'><s:text name='message.no_mappings'/></span>");
	}
	
	var selectedEntityAttribute = null;
	var entityAttributesArray = new Array();
	var selectedModelAttribute = null;
	var modelAttributesArray = new Array();
	var nonMappedModelAttributesArray = new Array();
	
	$.subscribe('onstop', function(event,data) {
		checkMapping();
	});
	
	function checkMapping() {
		resetMessageAndControlDivs();
		selectedEntityAttribute = $("#entityAttributesDiv").children('ul').children('li.ui-selected');
		selectedModelAttribute = $("#modelAttributesDiv").children('ul').children('li.ui-selected');
		if ((selectedEntityAttribute != null && selectedEntityAttribute.length > 0)
				&& (selectedModelAttribute != null && selectedModelAttribute.length > 0)) {
			var entityAttrName = $(selectedEntityAttribute).attr('id').replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = $(selectedModelAttribute).attr('id').replace("modelAttr_", "");
			var modelAttrType = modelAttributesArray[modelAttrName];
			var compatibility = checkTypeCompatibility(entityAttrType, modelAttrType);
			if (compatibility == -1) { // Incompatible types
				$("#mapping_messages").html("<p class='messageBox error'><s:text name='error.type_compatibility'/></p>");
			} else if (compatibility == 0) { // Not fully compatible types
				$("#mapping_messages").html("<p class='messageBox warning'><s:text name='warning.type_compatibility'/></p>");
			} else { // Fully compatible types
				if (modelAttrType == "float_range") {
					var range = null;
					if (entityAttrType == "string" || entityAttrType == "boolean") {
						range = false;
						$("#mapping_cfg").html("<p><c:out value='${expectedValueExplanation}'/></p>");
					} else {
						range = true;
						$("#mapping_cfg").html("<p><c:out value='${rangeExplanation}'/></p>");
					}
					addRangeConfigurationLine(range);
					$("#mapping_control").append("<a href='javascript:addRangeConfigurationLine(" + range + ")' class='clrright'><s:text name='label.add_configuration_line'/></a>");
				} else if (modelAttrType == "color") {
					// if entity attr type is color in hex format, it is a direct mapping
					if (entityAttrType != "hexcolor") {
						var range = null;
						if (entityAttrType == "string" || entityAttrType == "boolean") {
							range = false;
							$("#mapping_cfg").html("<p><c:out value='${expectedValueExplanation}'/></p>");
						} else {
							range = true;
							$("#mapping_cfg").html("<p><c:out value='${rangeExplanation}'/></p>");
						}
						addColorConfigurationLine(range);
						$("#mapping_control").append("<a href='javascript:addColorConfigurationLine(" + range + ")' class='clrright'><s:text name='label.add_configuration_line'/></a>");	
					} else {
						// Si es mapeo directo no hay que hacer nada más
						$("#mapping_cfg").html("<p><c:out value='${noExplanation}'/></p>");
					}
				} else if (modelAttrType == "float") {
					// If we try to map to a float dimension, we must stablish a ratio value that specifies the maximum value the entity attribute could have (p.e. portability - 100.0)
					$("#mapping_cfg").html("<p><c:out value='${ratioExplanation}'/></p>");
					$("#mapping_cfg").append("<fieldset class='cfg_line form'>");
					$("#mapping_cfg .cfg_line:last").append("<legend><s:text name='label.ratio_configuration'/>:</legend>");
					$("#mapping_cfg .cfg_line:last").append("<ul>");
					$("#mapping_cfg .cfg_line:last ul").append("<li>");
					$("#mapping_cfg .cfg_line:last ul li:last").append("<label><c:out value='${ratioValue}'/></label>");
					$("#mapping_cfg .cfg_line:last ul li:last").append("<input id='ratio' type='text' value=''/>");
				} else {
					// Si es mapeo directo no hay que hacer nada más
					$("#mapping_cfg").html("<p><c:out value='${noExplanation}'/></p>");
				}
				$("#mapping_control").append("<a href='javascript:saveMapping()' class='clrright'><s:text name='label.save_mapping'/></a>");
			}
		}
	}
	
	function saveMapping() {
		if ((selectedEntityAttribute != null && selectedEntityAttribute.length > 0)
				&& (selectedModelAttribute != null && selectedModelAttribute.length > 0)) {
			var entityAttrName = $(selectedEntityAttribute).attr('id').replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = $(selectedModelAttribute).attr('id').replace("modelAttr_", "");
			var modelAttrType = modelAttributesArray[modelAttrName];
			// Recorrer todas las lineas para comprobar que estan bien configuradas
			// Si es asociacion directa no habrá ninguna línea que comprobar, si es range o color, puede haber varias
			// El valor de los textboxes será del tipo de la columna de la tabla
			var error = false;
			var rules = new Array();
			var ratio = null;
			// if entityAttrType == "hexcolor" then direct mapping because it is in hex format
			if (entityAttrType != "hexcolor" && (modelAttrType == "float_range" || modelAttrType == "color")) {
				$("#mapping_cfg").children(".cfg_line").each(function(index, element) {
					var low = $(element).children('ul').children('li').children('#low').val();
					var high = $(element).children('ul').children('li').children('#high').val();
					var value = null;
					if (modelAttrType == "color") {
						value = rgb2hex($(element).children('ul').children('li').children('.colorSelector').children('div').css('backgroundColor'));
					} else if (modelAttrType == "float_range") {
						value = $(element).children('ul').children('li').children('#value').val();
					}
					if (entityAttrType == "int") {
						low = parseInt(low, 10);
						high = parseInt(high, 10);
						if (modelAttrType == "float_range") value = parseInt(value, 10);
					} else if (entityAttrType == "float") {
						low = parseFloat(low);
						high = parseFloat(high);
						if (modelAttrType == "float_range") value = parseFloat(value);
					} else if (entityAttrType == "string") {
						high = low;
					}
					if ((((entityAttrType == "int" || entityAttrType == "float") && modelAttrType == "color") && (isNaN(low) || isNaN(high)))
							|| (((entityAttrType == "int" || entityAttrType == "float") && modelAttrType == "float_range") && (isNaN(low) || isNaN(high) || isNaN(value)))) {
						$("#mapping_messages").html("<p class='messageBox error'><s:text name='error.field_isNaN'/></p>");
						error = true;
						// Resetear tablas de valores
						rules = new Array();
					} else {
						// Actualizar tablas de valores para construir el fichero XML
						rule = new Rule(low, high, value);
						rules.push(rule);
					}
				});
			} else if (modelAttrType == "float") {
				if ($("#ratio").val() != "") {
					ratio = parseFloat($("#ratio").val());
					if (isNaN(ratio)) {
						$("#mapping_messages").html("<p class='messageBox error'><s:text name='error.field_isNaN'/></p>");
						error = true;
					}
				} else ratio = null;
			}
			if (!error) {
				$("#mapping_added tfoot").html("");
				// Si no hay error, establecemos al asociacion con sus reglas
				var entityAttribute = new Attribute(entityAttrName, entityAttrType);
				var modelAttribute = new Attribute(modelAttrName, modelAttrType)
				var mapping = new Mapping(entityAttribute, modelAttribute, ratio, rules);
				mappings.push(mapping);
				// Ocultar los botones correspondientes a la columna de la tabla y el atributo de la clase
				$(selectedEntityAttribute).slideUp('slow');
				$(selectedModelAttribute).slideUp('slow');
				// Deseleccionar los botones
				$(selectedEntityAttribute).removeClass('ui-selected');
				$(selectedModelAttribute).removeClass('ui-selected');
				// Resetear selectedColumn y selectedAttribute
				selectedEntityAttribute = null;
				selectedModelAttribute = null;
				// Resetear mapping_messages, mapping_cfg y mapping_control
				resetMessageAndControlDivs();
				// Feedback en mapping_added
				var rowspan = rules.length;
				if (rowspan == 0) rowspan = 1;
				$("#mapping_added_body").append("<tr class='mapping_line' />");
				$(".mapping_line:last").append("<td class='entityAttr_field' rowspan='" + rowspan + "'>" + entityAttrName + "</td>");
				$(".mapping_line:last").append("<td class='modelAttr_field' rowspan='" + rowspan + "'>" + modelAttrName + "</td>");
				if (ratio == null && rules.length == 0) {
					$(".mapping_line:last").append("<td colspan='4' />");
				} else {
					if (ratio != null) {
						$(".mapping_line:last").append("<td colspan='3' rowspan='" + rowspan + "' />");
						$(".mapping_line:last").append("<td class='ratio' rowspan='" + rowspan + "'>" + ratio + "</td>");
					}
					if (rules.length > 0) {
						// Only first rule in this tr
						var element = rules[0];
						$(".mapping_line:last").append("<td class='rule_low'>" + element.low + "</td>");
						$(".mapping_line:last").append("<td class='rule_high'>" + element.high + "</td>");
						$(".mapping_line:last").append("<td class='rule_value'>" + element.value + "</td>");
						$(".mapping_line:last").append("<td />"); // empty ratio column
					}
				}
				$(".mapping_line:last").append("<td rowspan='" + rowspan + "'><a href='javascript:void(0)' class='remove_mapping'><img class='removeIcon' src='images/gtk-cancel.png' alt=\"<s:text name='label.remove_mapping'/>\" title=\"<s:text name='label.remove_mapping'/>\" /></a></td>");
				$("a.remove_mapping").click(function() {
					$(this).parent().parent().slideUp('slow');
					removeMapping($(this).parent().parent());
				});
				if (rules.length > 1) {
					var rulesButFirstElement = rules.slice(1);
					$.each(rulesButFirstElement, function(index, element) {
						$("#mapping_added_body").append("<tr class='mapping_rule' />");
						$(".mapping_rule:last").append("<td class='rule_low'>" + element.low + "</td>");
						$(".mapping_rule:last").append("<td class='rule_high'>" + element.high + "</td>");
						$(".mapping_rule:last").append("<td class='rule_value'>" + element.value + "</td>");
					});
				}
				// Feedback en mapping_messages
				$("#mapping_messages").html("<p class='messageBox ok'><s:text name='message.mapping_successful'/></p>");
			}
		} else {
			// TODO show message error no 2 side mappings
		}
	}
	
	function removeMapping(mappingLineSelector) {
		// Show selectable divs
		var entityAttributeName = $(mappingLineSelector).children("td.entityAttr_field").html();
		var entityAttrSelector = "#entityAttr_" + entityAttributeName;
		$(entityAttrSelector).slideDown('slow');
		var modelAttributeName = $(mappingLineSelector).children("td.modelAttr_field").html();
		var modelAttrSelector = "#modelAttr_" + modelAttributeName;
		$(modelAttrSelector).slideDown('slow');
		var next = $(mappingLineSelector).next();
		while (next.attr('class') == "mapping_rule") {
			next.remove();
			next = $(mappingLineSelector).next();
		}
		// Remove mapping line
		$(mappingLineSelector).remove();
		// Remove from mappings array
		$.each(mappings, function(i, item) {
			if (item.entityAttribute.name == entityAttributeName && item.modelAttribute.name == modelAttributeName) {
				mappings.splice(i, 1); // This removes one element from position i
			}
		});
		$("#mapping_messages").html("<p class='messageBox ok'><s:text name='message.mapping_deleted'/></p>");
		// If no mappings left, feedback it
		if (mappings.length == 0) {
			$("#mapping_added tfoot").html("<tr>");
			$("#mapping_added tfoot tr:last").append("<td colspan='7'>");
			$("#mapping_added tfoot tr:last td:last").append("<span class='messageBox info'><s:text name='message.no_mappings'/></span>");
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
	
	function addRangeConfigurationLine(range) {
		$("#mapping_cfg").append("<fieldset class='cfg_line form'>");
		$(".cfg_line:last").append("<legend><s:text name='label.rule_configuration'/>:</legend>");
		$(".cfg_line:last").append("<ul>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${expectedValue}'/></label>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeLow}'/></label>");
		}
		$(".cfg_line:last ul li:last").append("<input type='text' id='low' name='low'/>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' style='display: none;'/>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeHigh}'/></label>");
			$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high'/>");
		}
		$(".cfg_line:last ul").append("<li>");
		$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeValue}'/></label>");
		$(".cfg_line:last ul li:last").append("<input type='text' id='value' name='value'/>");
		$(".cfg_line:last").append("<span><a href='javascript:void(0)' class='removeRangeConfigurationLine rightclr'><s:text name='label.remove_configuration_line'/></a></span>");
		$(".cfg_line:last").append("<div class='clear'></div>");
		
		$("a.removeRangeConfigurationLine").click(function() {
			$(this).parent().parent().slideUp('slow');
			$(this).parent().parent().remove();
		});
	}
	
	function addColorConfigurationLine(range) {
		$("#mapping_cfg").append("<fieldset class='cfg_line form'>");
		$(".cfg_line:last").append("<legend><s:text name='label.rule_configuration'/>:</legend>");
		$(".cfg_line:last").append("<ul>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${expectedValue}'/></label>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeLow}'/></label>");
		}
		$(".cfg_line:last ul li:last").append("<input type='text' id='low' name='low'/>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' style='display: none;'/>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeHigh}'/></label>");
			$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high'/>");
		}
		$(".cfg_line:last ul").append("<li>");
		$(".cfg_line:last ul li:last").append("<label><c:out value='${color}'/></label>");
		buildColorPicker(".cfg_line:last ul li:last", "");
		$(".cfg_line:last").append("<span><a href='javascript:void(0)' class='removeColorConfigurationLine rightclr'><s:text name='label.remove_configuration_line'/></a></span>");
		$(".cfg_line:last").append("<div class='clear'></div>");
		
		$("a.removeColorConfigurationLine").click(function() {
			$(this).parent().parent().slideUp('slow');
			$(this).parent().parent().remove();
		});
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
				if (type2 == "float" || type2 == "float_range" || type2 == "color" || type2 == "string") compatible = 1;
			} else if (type1 == "float") {
				if (type2 == "float_range" || type2 == "color" || type2 == "string") compatible = 1;
			} else if (type1 == "string") {
				if (type2 == "float_range" || type2 == "color") compatible = 1;
			} else if (type1 == "boolean") {
				if (type2 == "float_range" || type2 == "color") compatible = 1;
			}
		}
		return compatible;
	}
	
	var colorPickerCounter = 0;
	function buildColorPicker(selector, id) {
		var initialColor = "#FFFFFF";
		
		if (id == "") id = "colorPicker" + ++colorPickerCounter;
		$(selector).append("<div id='" + id + "' class='colorSelector' style='float: left;'><div style='background-color: " + initialColor + ";'></div></div>");
		$(selector).append("<div class='clear'></div>");
		
		$(".colorSelector:last").ColorPicker({
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
				$("#" + id + " div").css('backgroundColor', '#' + hex);
			},
			onSubmit: function(hsb, hex, rgb, el) {
				$(el).val(hex);
				$(el).ColorPickerHide();
			}
		});
	}
	
	function addCaptionLine(){
		$("#added_captionLines tbody").append("<tr class='cfg_line'>");
		$("#added_captionLines tbody tr:last").append("<td>");
		buildColorPicker("#added_captionLines tbody tr:last td:last", "");
		$("#added_captionLines tbody tr:last").append("<td>");
		$("#added_captionLines tbody tr:last td:last").append("<input type='text' id='text' name='text'/>");
		$("#added_captionLines tbody tr:last").append("<td>");
		$("#added_captionLines tbody tr:last td:last").append("<a href='javascript:void(0)' class='removeInputLine'><img class='removeIcon' src='images/gtk-cancel.png' alt=\"<s:text name='label.remove_caption_line'/>\" title=\"<s:text name='label.remove_caption_line'/>\" /></a>");
		
		$("a.removeInputLine").click(function() {
			$(this).parent().parent().slideUp('slow');
			$(this).parent().parent().remove();
		});
	}
	
	function configureNonMappedModelAttributes() {
		// Clean non mapped model attributes array
		nonMappedModelAttributesArray = new Array();
		// Navigate through all model attributes and pick visible ones up
		$("#modelAttributesDiv").children('ul').children('li').each(function(){
			if ($(this).css('display') != 'none') {
				nonMappedModelAttributesArray.push($(this).attr('id').replace("modelAttr_", ""));	
			}
		});
		// Clean configuration table body and foot
		$("#nonMapped_dimensions tfoot").html("");
		$("#nonMapped_dimensions tbody").html("");
		if (nonMappedModelAttributesArray.length > 0) {
			// Fill configuration table in
			$.each(nonMappedModelAttributesArray, function() {
				$("#nonMapped_dimensions tbody").append("<tr>");
				$("#nonMapped_dimensions tbody tr:last").append("<td>");
				$("#nonMapped_dimensions tbody tr:last td:last").append("<label for='constant_" + this + "'>" + this + " (" + modelAttributesArray[this] + ")</label>");
				$("#nonMapped_dimensions tbody tr:last").append("<td class='attr'>");
				if (modelAttributesArray[this] == "color") {
					buildColorPicker("#nonMapped_dimensions tbody tr:last td:last", "constant_" + this);
				} else {
					$("#nonMapped_dimensions tbody tr:last td:last").append("<input id='constant_" + this + "' type='text' value='' />");
				}
			});
		} else {
			$("#nonMapped_dimensions tfoot").html("<tr>");
			$("#nonMapped_dimensions tfoot tr:last").append("<td colspan='2'>");
			$("#nonMapped_dimensions tfoot tr:last td:last").append("<p class='messageBox info'><s:text name='message.all_model_attributes_mapped'/></p>");
		}
	}
	
	function checkProfile() {
		captionLines = new Array();
		$("#mapping_messages").html("");
		var noError = true;
		// Comprobar que hay asociaciones hechas
		if (mappings.length < 1) {
			$("#mapping_messages").html("<p class='messageBox error'><s:text name='error.empty_mapping'/></p>");
			toggleSteps(3,1);
			noError = false;
		}
		// Comprobar que no quedan nonMappedModelAttributes sin valor por defecto y que el valor corresponde al tipo
		var nonMappedAttributesError = false;
		$("#nonMapped_dimensions tbody tr").each(function(index, element) {
			var attrName = $(element).children(".attr").children().attr('id').replace("constant_", "");
			var attrType = modelAttributesArray[attrName];
			var attrValue = null;
			if (attrType == "color") {
				attrValue = rgb2hex($(element).children(".attr").children('div').children('div').css('backgroundColor'));
			} else {
				attrValue = $(element).children(".attr").children("input").val();
			}
			// Check types if no empty value (empty value means using default graphic model constants)
			if (attrValue != "") {
				if (((attrType == "float_range" || attrType == "float") && !isFloat(attrValue))
						|| (attrType == "int" && !isInt(attrValue))) {
					// Add class error if incorrect types
					$(element).children(".attr").children().addClass("error");
					nonMappedAttributesError = true;
				} else {
					// Remove class error if already added and types are correct
					if ($(element).children(".attr").children().hasClass("error")) {
						$(element).children(".attr").children().removeClass("error");
					}
				}
			}
		});
		if (nonMappedAttributesError) {
			$("#nonMapped_dimensions tfoot").html("<tr>");
			$("#nonMapped_dimensions tfoot tr:last").html("<td colspan='2'>");
			$("#nonMapped_dimensions tfoot tr td:last").html("<p class='messageBox error'><s:text name='error.constant_attributes'/></p>");
		}
		// Comprobar que no hay caption lines sin texto
		$("#added_captionLines tbody tr.cfg_line").each(function(index, element) {
			var labelElement = $(element).children("td").children("#text");
			var label = $(labelElement).val();
			var hexColor = rgb2hex($(element).children("td").children('.colorSelector').children('div').css('backgroundColor'));
			if (label == "") {
				$(labelElement).addClass("error");
				$("#added_captionLines tfoot").html("<tr>");
				$("#added_captionLines tfoot tr:last").append("<td colspan='3'>");
				$("#added_captionLines tfoot tr:last td:last").append("<p class='messageBox error'><s:text name='error.empty_caption_lines'/></p>");
				noError = false;
			} else {
				if ($(labelElement).hasClass("error")) {
					$(labelElement).removeClass("error");
				}
				var cl = new CaptionLine(label, hexColor);
				captionLines.push(cl);
			}
		});
		// Comprobar que profileName y profileDescription no están vacíos
		if ($("#profileName").val() == "") {
			$("#profileNameError").html("<span class='messageBox error'><s:text name='error.profileName.isMandatory'/></span>")
			noError = false;
		}
		if ($("#profileDescription").val() == "") {
			$("#profileDescriptionError").html("<span class='messageBox error'><s:text name='error.profileDescription.isMandatory'/></span>")
			noError = false;
		}

		return noError;
	}
	
	function saveProfile() {
		if (checkProfile() == true) {
			// Configure nonMappedAttributes to mappings
			var constants = new Array(); // hashmap-like
			$.each(nonMappedModelAttributesArray, function(i, item) {
				var attr = new Object();
				attr.name = item;
				attr.type = modelAttributesArray[item];
				if (attr.type == "color") {
					attr.value = rgb2hex($("#constant_"+item).children('div').css('backgroundColor'));
				} else {
					attr.value = $("#constant_"+item).val();
					if (attr.type == "float_range") attr.type = "float";
					if (attr.type == "int") attr.value = parseInt(attr.value, 10);
					else if (attr.type == "float") attr.value = parseFloat(attr.value);
				}
				constants.push(attr);
			});
			var jsonCaptionLines = JSON.stringify(captionLines);
			var jsonMappings = JSON.stringify(mappings);
			var jsonConstants = JSON.stringify(constants);
			$.post ("/desglosa-web/saveProfile",
				{ profileName: $("#profileName").val(),
				  profileDescription: $("#profileDescription").val(),
				  model: $("#modelSelect").val(),
				  entity: $("#entitySelect").val(),
				  jsonCaptionLines: jsonCaptionLines,
				  jsonMappings: jsonMappings,
				  jsonConstants: jsonConstants
				},
				function(data, status) {
					if (status == "success") {
						alert("success");
					} else {
						alert("error");
					}
			});
		} else {
			$('#errorDialogBody').html("<p class='messageBox error'><c:out value='${profileCreationErrors}'/></p>");
			$('#errorDialog').dialog('open');
		}
	}
	
	function toggleSteps(from, to) {
		var fromStep = null;
		switch (from) {
		case 1:
			fromStep = "#first_step";
			break;
		case 2:
			fromStep = "#second_step";
			break;
		case 3:
			fromStep = "#third_step";
			break;
		}
		var toStep = null;
		switch (to) {
		case 1:
			toStep = "#first_step";
			break;
		case 2:
			toStep = "#second_step";
			break;
		case 3:
			toStep = "#third_step";
			break;
		}
		if (from == 1 && to == 2) {
			configureNonMappedModelAttributes();
		}
		$(fromStep).toggle();
		$(toStep).toggle();
	}
	
	$(document).ready(function() {
		resetMappings();
	});

	</script>
</head>

<body>
	<div id="first_step">
		<s:url id="updateProfileURL" action="json_p_updateProfileForm"/>
		<s:url id="refreshTableColumns" action="json_p_loadTableColumns"/>
		
		<div id="info">
			<p><s:text name="label.general_information_about_mappings1"/></p>
			<p><s:text name="label.general_information_about_mappings2"/></p>
		</div>
		
		<div id="panes" class="pane">
			<div id="leftPane">
				<sj:select 	href="%{updateProfileURL}"
							emptyOption="false"
							headerKey="-1"
							headerValue="-- %{getText('label.entity_selection')} --"
							disabled="option:first"
							id="entitySelect"
							name="entity"
							list="entities"
							indicator="indicator1"
							onAlwaysTopics="disableHeader"
							onChangeTopics="reloadEntityAttributes"/>
				<div><img id="indicator1" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none"/></div>
				<sj:div id="entityAttributesDiv" selectableOnStopTopics="onstop" selectable="true" selectableFilter="li"></sj:div>
			</div>
			<div id="rightPane">
				<sj:select 	href="%{updateProfileURL}"
							emptyOption="false"
							headerKey="-1"
							headerValue="-- %{getText('label.model_selection')} --"
							id="modelSelect"
							name="model"
							list="models"
							indicator="indicator2"
							onAlwaysTopics="disableHeader"
							onChangeTopics="reloadModelAttributes"/>
				<div><img id="indicator2" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none"/></div>
				<sj:div id="modelAttributesDiv" selectableOnStopTopics="onstop" selectable="true" selectableFilter="li"></sj:div>
			</div>
		</div>
		
		<div id="mapping" class="pane">
		
			<!-- Table markup-->
			<table id="mapping_added" class="default">
				
				<!-- Table header -->
				<thead id="mapping_added_header">
					<tr class="header">
						<th colspan="7"><s:text name="label.configured_mappings"/></th>
					</tr>
					<tr class="subheader">
						<th class="first"><s:text name="label.entity_attribute"/></th>
						<th><s:text name="label.model_attribute"/></th>
						<th><s:text name="label.range.low.abbr"/></th>
						<th><s:text name="label.range.high.abbr"/></th>
						<th><s:text name="label.range.value.abbr"/></th>
						<th><s:text name="label.ratio"/></th>
						<th class="last"></th>
					</tr>
				</thead>
				
				<!-- Table body --> 
				<tbody id="mapping_added_body"></tbody>
				
				<!-- Table footer -->
				<tfoot id="mapping_added_footer"></tfoot>
			</table>
			
			<div id="mapping_messages"></div>
			<div id="mapping_cfg"></div>
			<div id="mapping_control"></div>
		</div>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(1,2)" class="right"><s:text name="label.next"/> &gt;</a>
		</div>
		<div class="clear"></div>
	</div>
	
	<div id="second_step">
		<div id="nonMappedPane" class="pane">
			<table id="nonMapped_dimensions" class="default">
				<thead>
					<tr><th colspan="2"><s:text name="label.configure_nonMapped_Attributes"/></th></tr>
					<tr>
						<th>
							<s:text name="label.model_attribute"/>
						</th>
						<th>
							<s:text name="label.value"/>
						</th>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot></tfoot>
			</table>
		</div>
		<div id="captionPane" class="pane">
			<table id="added_captionLines" class="default">
				<thead>
					<tr>
						<th colspan="3"><s:text name="label.configure_caption_lines"/></th>
					</tr>
					<tr>
						<th><s:text name="label.caption.color"/></th>
						<th><s:text name="label.caption.text"/></th>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot>
					<tr>
						<td colspan="3"><a href="javascript:addCaptionLine()"><s:text name="label.add_caption_line"/></a></td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(2,1)" class="left">&lt; <s:text name="label.back"/></a>
			<a href="javascript:toggleSteps(2,3)" class="right"><s:text name="label.next"/> &gt;</a>
		</div>
		<div class="clear"></div>
	</div>
	
	<div id="third_step">
		<div id="profilePane" class="form">
			<span class="header"><s:text name="label.profile_info"></s:text></span>
			<ul>
				<li>
					<label for="profileName"><s:text name="label.profile_name"/></label>
					<input id="profileName" name="profileName" type="text"/>
					<span id="profileNameError"></span>
				</li>
				<li>
					<label for="profileDescription"><s:text name="label.profile_description"/></label>
					<textarea id="profileDescription" name="profileDescription" rows="3" cols="15"></textarea>
					<span id="profileDescriptionError"></span>
				</li>
			</ul>
		</div>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(3,2)" class="left">&lt; <s:text name="label.back"/></a>
			<a href="javascript:saveProfile()" class="right"><s:text name="label.saveProfile"/></a>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>