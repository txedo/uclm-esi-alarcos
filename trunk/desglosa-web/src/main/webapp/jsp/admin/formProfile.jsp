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
	<fmt:message key="error.general" var="criticalError"/>

	<meta name="menu" content="ManageProfiles"/>
	
	<link href="<s:url value='/styles/style.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/profile.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/buttons.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/js/colorpicker/css/colorpicker.css?version=1'/>" rel="stylesheet" type="text/css" media="screen" />
	
	<sj:head jqueryui="true"/>
	
	<script type="text/javascript" src="js/jquery.tools-1.2.6.min.js?version=1"></script>
    <link href="<s:url value='/styles/tooltip.css?version=1'/>" rel="stylesheet" type="text/css" />
    <fmt:message key="label.tooltip.int" var="labelInteger"/>
    <fmt:message key="label.tooltip.float" var="labelFloat"/>
    <fmt:message key="label.tooltip.string" var="labelString"/>
    <fmt:message key="label.tooltip.boolean" var="labelBoolean"/>
    <fmt:message key="label.tooltip.defaultInt" var="labelDefaultInteger"/>
    <fmt:message key="label.tooltip.defaultFloat" var="labelDefaultFloat"/>
    <fmt:message key="label.tooltip.defaultString" var="labelDefaultString"/>
    <fmt:message key="label.tooltip.defaultBoolean" var="labelDefaultBoolean"/>
	
	<script type="text/javascript" src="<s:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/utils.js?version=1"></script>
	
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
	
	function getValueClass(valueType) {
		var valueClass = null;
		if (valueType == "string") {
			valueClass = "stringValue";
		} else if (valueType == "boolean") {
			valueClass = "booleanValue";
		} else if (valueType == "int") {
			valueClass = "intNumber";
		} else if (valueType == "float" || valueType == "float_range") {
            valueClass = "floatNumber";
        }
		return valueClass;
	}
	
   function getTitleForValueClass(valueClass) {
        var title = "";
        if (valueClass == "stringValue") {
            title = "<c:out value='${labelString}'/>";
        } else if (valueClass == "booleanValue") {
            title = "<c:out value='${labelBoolean}'/>";
        } else if (valueClass == "intNumber") {
            title = "<c:out value='${labelInteger}'/>";
        } else if (valueClass == "floatNumber") {
            title = "<c:out value='${labelFloat}'/>";
        }
        return title;
    }
	
	function checkMapping() {
		clearTooltips();
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
					var expectedValueClass = getValueClass(entityAttrType);
					var assignedValueClass = getValueClass(modelAttrType);
					addRangeConfigurationLine(range, expectedValueClass, assignedValueClass);
					$("#mapping_control").append("<a href='javascript:void(0)' onclick='javascript:addRangeConfigurationLine(" + range + ",\"" + expectedValueClass + "\",\"" + assignedValueClass + "\")' class='clrright'><s:text name='label.add_configuration_line'/></a>");
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
						var expectedValueClass = getValueClass(entityAttrType);
						addColorConfigurationLine(range, expectedValueClass);
						$("#mapping_control").append("<a href='javascript:void(0)' onclick='javascript:addColorConfigurationLine(" + range + ",\"" + expectedValueClass + "\")' class='clrright'><s:text name='label.add_configuration_line'/></a>");	
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
					$("#mapping_cfg .cfg_line:last ul li:last").append("<input id='ratio' class='floatNumber' type='text' value='' title='" + getTitleForValueClass('floatNumber') + "'/>");
					addTooltips();
				} else {
					// Si es mapeo directo no hay que hacer nada más
					$("#mapping_cfg").html("<p><c:out value='${noExplanation}'/></p>");
				}
				$("#mapping_control").append("<a href='javascript:void(0)' onclick='javascript:saveMapping()' class='clrright'><s:text name='label.save_mapping'/></a>");
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
					// Pick the values up from textfields
					var lowSelector = $(element).children('ul').children('li').children('#low');
					var low = $(lowSelector).val();
					var highSelector = $(element).children('ul').children('li').children('#high');
					var high = $(highSelector).val();
					if (entityAttrType == "boolean") {
						low = $(lowSelector).is(':checked');
						high = "";
					}
					var valueSelector = null;
					var value = null;
					if (modelAttrType == "color") {
						valueSelector = $(element).children('ul').children('li').children('.colorSelector').children('div');
						value = rgb2hex($(valueSelector).css('backgroundColor'));
					} else if (modelAttrType == "float_range") {
						valueSelector = $(element).children('ul').children('li').children('#value');
						value = $(valueSelector).val();
					}
					// Now check that the data is correct with its type
					// first check the low and high range values
					if (entityAttrType == "int") {
						var validLowValue = isFieldValueInt(lowSelector);
						var validHighValue = isFieldValueInt(highSelector);
						if (validLowValue && validHighValue) {
							low = parseInt(low, 10);
							high = parseInt(high, 10);
						} else {
							error = true;
						}
					} else if (entityAttrType == "float") {
						var validLowValue = isFieldValueFloat(lowSelector);
                        var validHighValue = isFieldValueFloat(highSelector);
                        if (validLowValue && validHighValue) {
                            low = parseFloat(low.replace(",", "."));
                            high = parseFloat(high.replace(",", "."));
                        } else {
                            error = true;
                        }
					} else if (entityAttrType == "string") {
						high = low;
					}
					// then check the assigned value
					if (modelAttrType == "int") {
						if (isFieldValueInt(valueSelector)) {
							value = parseInt(value, 10);
						} else {
							error = true;
						}
					} else if (modelAttrType == "float_range") {
						if (isFieldValueFloat(valueSelector)) {
							value = parseFloat(value.replace(",", "."));
						} else {
							error = true;
						}
					}
					if (!error) {
						// Actualizar tablas de valores para construir el fichero XML
						rule = new Rule(low, high, value);
						rules.push(rule);
					} else {
						$("#mapping_messages").html("<p class='messageBox error'><s:text name='error.field_isNaN'/></p>");
                        error = true;
                        // Resetear tablas de valores
                        rules = new Array();
					}
				});
			} else if (modelAttrType == "float") {
				if ($("#ratio").val() != "") {
					// Si se configura un ratio, este valor sera utilizado como el maximo para la normaliacion
					var ratioSelector = $("#ratio");
					var ratio = $(ratioSelector).val();
					if (isFieldValueFloat(ratioSelector)) {
						ratio = parseFloat(ratio.replace(",", "."));
					} else {
	                    $("#mapping_messages").html("<p class='messageBox error'><s:text name='error.field_isNaN'/></p>");
	                    error = true;
					}
				} else {
					// Si el ratio es null, se tomara el maximo valor de las medidas para normalizar
					ratio = null;
				}
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
				clearTooltips();
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
	
	function addRangeConfigurationLine(range, expectedValueClass, assignedValueClass) {
		$("#mapping_cfg").append("<fieldset class='cfg_line form'>");
		$(".cfg_line:last").append("<legend><s:text name='label.rule_configuration'/>:</legend>");
		$(".cfg_line:last").append("<ul>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${expectedValue}'/></label>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeLow}'/></label>");
		}
		if (expectedValueClass == "booleanValue") {
			$(".cfg_line:last ul li:last").append("<input type='checkbox' id='low' name='low' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
		} else {
		    $(".cfg_line:last ul li:last").append("<input type='text' id='low' name='low' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
	        $(".cfg_line:last ul").append("<li>");
	        if (!range) {
	            $(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' style='display: none;'/>");
	        } else {
	            $(".cfg_line:last ul li:last").append("<label><c:out value='${rangeHigh}'/></label>");
	            $(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
	        }
		}
		$(".cfg_line:last ul").append("<li>");
		$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeValue}'/></label>");
		$(".cfg_line:last ul li:last").append("<input type='text' id='value' name='value' class='" + assignedValueClass + "' title='" + getTitleForValueClass(assignedValueClass) + "'/>");
		$(".cfg_line:last").append("<span><a href='javascript:void(0)' class='removeRangeConfigurationLine rightclr'><s:text name='label.remove_configuration_line'/></a></span>");
		$(".cfg_line:last").append("<div class='clear'></div>");
		
		$("a.removeRangeConfigurationLine").click(function() {
			$(this).parent().parent().slideUp('slow');
			$(this).parent().parent().remove();
		});
		
		addTooltips();
	}
	
	function addColorConfigurationLine(range, expectedValueClass) {
		$("#mapping_cfg").append("<fieldset class='cfg_line form'>");
		$(".cfg_line:last").append("<legend><s:text name='label.rule_configuration'/>:</legend>");
		$(".cfg_line:last").append("<ul>");
		$(".cfg_line:last ul").append("<li>");
		if (!range) {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${expectedValue}'/></label>");
		} else {
			$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeLow}'/></label>");
		}
	    if (expectedValueClass == "booleanValue") {
	        $(".cfg_line:last ul li:last").append("<input type='checkbox' id='low' name='low' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
	    } else {
			$(".cfg_line:last ul li:last").append("<input type='text' id='low' name='low' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
			$(".cfg_line:last ul").append("<li>");
			if (!range) {
				$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' style='display: none;'/>");
			} else {
				$(".cfg_line:last ul li:last").append("<label><c:out value='${rangeHigh}'/></label>");
				$(".cfg_line:last ul li:last").append("<input type='text' id='high' name='high' class='" + expectedValueClass + "' title='" + getTitleForValueClass(expectedValueClass) + "'/>");
			}
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
		
		addTooltips();
	}
	
	function addTooltips() {
		// select all desired input fields and attach tooltips to them
		$("input.floatNumber[type=text],input.intNumber[type=text],input.stringValue[type=text],input.booleanValue[type=checkbox]").live("mouseover", function() {
			var me = $(this);
			if (!me.hasClass('tooltiped')) {
				me.tooltip({
		            tipClass: "inputtooltip",
		            // place tooltip on the right edge
		            position: "center right",
		            // a little tweaking of the position
		            offset: [-2, 10],
		            // use the built-in fadeIn/fadeOut effect
		            effect: "fade",
		            // custom opacity setting
		            opacity: 0.7,
		            cancelDefault: false,
		            lazy: false, 
		            api: true
		        });
				me.addClass('tooltiped');
			}
		});
	}
	
	function clearTooltips() {
		$("div").remove(".inputtooltip");
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
	
	function getTitleForValueClassConstant(valueClass) {
	     var title = getTitleForValueClass(valueClass);
	     if (valueClass == "stringValue") {
	         title += " <c:out value='${labelDefaultString}'/>";
	     } else if (valueClass == "booleanValue") {
	         title += " <c:out value='${labelDefaultBoolean}'/>";
	     } else if (valueClass == "intNumber") {
	         title += " <c:out value='${labelDefaultInteger}'/>";
	     } else if (valueClass == "floatNumber") {
	         title += " <c:out value='${labelDefaultFloat}'/>";
	     }
	     return title;
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
					var type = "text";
					if (modelAttributesArray[this] == "boolean") {
						type = "checkbox";
					}
					var valueClass = getValueClass(modelAttributesArray[this]);
					var title = getTitleForValueClassConstant(valueClass);
					$("#nonMapped_dimensions tbody tr:last td:last").append("<input id='constant_" + this + "' type='" + type + "' value='' class='" + valueClass + "' title='" + title + "'/>");
				}
			});
			addTooltips();
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
		} else {
			$("#mapping_messages").html("");
		}
		// Comprobar que no quedan nonMappedModelAttributes sin valor por defecto y que el valor corresponde al tipo
		var nonMappedAttributesError = false;
		$("#nonMapped_dimensions tbody tr").each(function(index, element) {
			var attrName = $(element).children(".attr").children().attr('id').replace("constant_", "");
			var attrType = modelAttributesArray[attrName];
			var attrValueSelector = null;
			var attrValue = null;
			if (attrType == "color") {
				attrValue = rgb2hex($(element).children(".attr").children('div').children('div').css('backgroundColor'));
			} else if (attrType == "boolean") {
				attrValue = $(element).children(".attr").children("input[type=checkbox]").is(':checked');
			} else {
				attrValueSelector = $(element).children(".attr").children("input");
				attrValue = $(attrValueSelector).val();
				// Check types if no empty value (empty value means using maximun dimension value to normalize)
				if (attrValue != "" && (attrType == "float_range" || attrType == "float")) {
					var isValid = isFieldValueFloat(attrValueSelector);
					if (!isValid) {
						nonMappedAttributesError = true;
					}
				} else if (attrValue != "" && attrType == "int") {
					var isValid = isFieldValueInt(attrValueSelector);
                    if (!isValid) {
                        nonMappedAttributesError = true;
                    }
				}
			}
		});
		if (nonMappedAttributesError) {
			$("#nonMapped_dimensions tfoot").html("<tr>");
			$("#nonMapped_dimensions tfoot tr:last").html("<td colspan='2'>");
			$("#nonMapped_dimensions tfoot tr td:last").html("<p class='messageBox error'><s:text name='error.constant_attributes'/></p>");
		} else {
			$("#nonMapped_dimensions tfoot").html("");
		}
		// Comprobar que no hay caption lines sin texto
		$("#added_captionLines tfoot").html("");
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
			$("#profileNameError").html("<ul class='errorMessage'><li><span><s:text name='error.profileName.isMandatory'/></span></li></ul>")
			noError = false;
		} else {
			$("#profileNameError").html("");
		}
		if ($("#profileDescription").val() == "") {
			$("#profileDescriptionError").html("<ul class='errorMessage'><li><span><s:text name='error.profileDescription.isMandatory'/></span></li></ul>")
			noError = false;
		} else {
			$("#profileDescriptionError").html("");
		}
	    if($("#entitySelect").val() == -1 || $("#modelSelect").val() == -1) {
	    	$("#entityAndModelError").html("<p><ul class='errorMessage'><li><span><s:text name='error.entity_model_required_fields'/></span></li></ul></p>")
	    	noError = false;
	    } else {
	    	$("#entityAndModelError").html("");
	    }
		return (!nonMappedAttributesError && noError);
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
					else if (attr.type == "boolean") attr.value = $("#constant_"+item).is(':checked');
				}
				constants.push(attr);
			});
			var jsonCaptionLines = JSON.stringify(captionLines);
			var jsonMappings = JSON.stringify(mappings);
			var jsonConstants = JSON.stringify(constants);
			$("#saveIndicator").toggle();
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
						$(location).attr('pathname', '/desglosa-web/listProfiles?add=success');
					} else {
						$('#errorDialogBody').html("<p class='messageBox error'><c:out value='${criticalError}'/></p>");
			            $('#errorDialog').dialog('open');
			            $("#saveIndicator").toggle();
					}
			});
		} else {
			$('#errorDialogBody').html("<p class='messageBox error'><c:out value='${profileCreationErrors}'/></p>");
			$('#errorDialog').dialog('open');
		}
	}
	
	function toggleSteps(from, to) {
	    clearTooltips();
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
    <a href="javascript:void(0)" onclick="javascript:goBack()" title="<s:text name='label.go_back'/>">&lt; <s:text name='label.go_back'/></a>
    
    <s:if test="!hasActionErrors()">
        <p><s:actionerror /></p>
    </s:if>
    <s:if test="!hasActionMessages()">
        <p><s:actionmessage /></p>
    </s:if>
    
	<div id="first_step">
	   <fieldset class="formfieldset">
			<s:url id="updateProfileURL" action="json_p_showForm"/>
			
			<div id="info">
			    <h2><s:text name="label.general_information_about_mappings.title1"/></h2>
				<p><s:text name="label.general_information_about_mappings1"/></p>
				<p><s:text name="label.general_information_about_mappings2"/></p>
				<span id="entityAndModelError"></span>
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
				
				<div><s:fielderror><s:param>error.nomappings</s:param></s:fielderror></div>
				<div id="mapping_messages"></div>
				<div id="mapping_cfg"></div>
				<div id="mapping_control"></div>
			</div>
		</fieldset>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(1,2)" class="right"><s:text name="label.next"/> &gt;</a>
		</div>
		<div class="clear"></div>
	</div>
	
	<div id="second_step">
	   <fieldset class="formfieldset">
	        <h2><s:text name="label.general_information_about_mappings.title2"/></h2>
	        <p><s:text name="label.general_information_about_mappings3"/></p>
	        
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
					<tfoot></tfoot>
                    <caption align="bottom">
						<a href='javascript:void(0)' onclick="javascript:addCaptionLine()"><s:text name="label.add_caption_line"/></a>
					</caption>
				</table>
			</div>
		</fieldset>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(2,1)" class="left">&lt; <s:text name="label.back"/></a>
			<a href="javascript:toggleSteps(2,3)" class="right"><s:text name="label.next"/> &gt;</a>
		</div>
		<div class="clear"></div>
	</div>
	
	<div id="third_step">
	   <fieldset class="formfieldset">
			<div id="profilePane" class="form">
				<h2><s:text name="label.profile_info.title" /></h2>
				<p><s:text name="label.profile_info.text" /></p>
				<ul>
					<li>
						<label for="profileName"><s:text name="label.profile_name"/> (*)</label>
						<input id="profileName" name="profileName" type="text"/>
						<span id="profileNameError"><s:fielderror><s:param>error.profile.name</s:param></s:fielderror></span>
					</li>
					<li>
						<label for="profileDescription"><s:text name="label.profile_description"/> (*)</label>
						<textarea id="profileDescription" name="profileDescription" rows="3" cols="15"></textarea>
						<span id="profileDescriptionError"><s:fielderror><s:param>error.profile.description</s:param></s:fielderror></span>
					</li>
				</ul>
			</div>
		</fieldset>
		<div class="clear"></div>
		<div class="wizardSteps">
			<a href="javascript:toggleSteps(3,2)" class="left">&lt; <s:text name="label.back"/></a>
			<button class="minimal" style="float: right;" onclick="javascript:saveProfile()"><img id="saveIndicator" src="images/indicator.gif" alt="<s:text name="label.loading"/>" title="<s:text name="label.loading"/>" style="display:none;" class="icon"/><fmt:message key="label.saveProfile"/></button>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>