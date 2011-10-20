<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
<head>
	<meta name="menu" content="ManageProfiles"/>
	
	<link href="<s:url value='/styles/profile.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link href="<s:url value='/styles/buttons.css?version=1'/>" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/colorpicker.css'/>" />
	<link rel="stylesheet" media="screen" type="text/css" href="<s:url value='/js/colorpicker/css/layout.css'/>" />
	
	<sj:head jqueryui="true" jquerytheme="cupertino"/>
	
	<script type="text/javascript" src="<s:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	
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
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
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
							$("#entityAttributesDiv ul").append("<li id='entityAttr_" + value.name + "' class='selectablelist ui-corner-all' title='" + value.description + "'>" + value.name + " (" + value.type + ")</li>");
						});
						$("#entityAttributesDiv").append("</ul>");
					}
					else alert('An error has occurred while trying to retrieve entity information: ' + status);
		});
	});
	
	$.subscribe('reloadModelAttributes', function() {
		selectedModelAttribute = null;
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
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
	
	function resetMappings() {
		// reset mappings array
		mappings = new Array();
		// clean mappings table
		$(".mapping_line").remove();
		// clean mappings messages
		$("#mapping_messages").html("");
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
		$("#mapping_messages").html("");
		$("#mapping_cfg").html("");
		$("#mapping_control").html("");
		// TODO esto genera un pequeño bug en la interfaz. Comprobar que el elemento sea visible
		selectedEntityAttribute = $("#entityAttributesDiv").children('ul').children('li.ui-selected');
		selectedModelAttribute = $("#modelAttributesDiv").children('ul').children('li.ui-selected');
		if (selectedEntityAttribute != null && selectedModelAttribute != null) {
			var entityAttrName = $(selectedEntityAttribute).attr('id').replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = (selectedModelAttribute).attr('id').replace("modelAttr_", "");
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
					$("#mapping_control").append("<a href='javascript:addRangeConfigurationLine(" + range + ")'><s:text name='label.add_configuration_line'/></a>");
				} else if (modelAttrType == "color") {
					// if entity attr type is color in hex format, it is a direct mapping
					if (entityAttrType != "hexcolor") {
						var range = true;
						if (entityAttrType == "string" || entityAttrType == "boolean") range = false;
						addColorConfigurationLine(range);
						$("#mapping_control").append("<a href='javascript:addColorConfigurationLine(" + range + ")'><s:text name='label.add_configuration_line'/></a>");	
					}
				} else if (modelAttrType == "float") {
					$("#mapping_cfg").append("<ul><li><input id='ratio' type='text' value=''/></li></ul>");
				}
				// Si es mapeo directo no hay que ahcer nada más
				$("#mapping_control").append("<a href='javascript:saveMapping()'><s:text name='label.save_mapping'/></a>");
			}
		}
	}
	
	function saveMapping() {
		if (selectedEntityAttribute != null && selectedModelAttribute != null) {
			var entityAttrName = $(selectedEntityAttribute).attr('id').replace("entityAttr_", "");
			var entityAttrType = entityAttributesArray[entityAttrName];
			var modelAttrName = (selectedModelAttribute).attr('id').replace("modelAttr_", "");
			var modelAttrType = modelAttributesArray[modelAttrName];
			// Recorrer todas las lineas para comprobar que estan bien configuradas
			// Si es asociacion directa no habrá ninguna línea que comprobar, si es range o color, puede haber varias
			// El valor de los textboxes será del tipo de la columna de la tabla
			var error = false;
			var rules = new Array();
			var ratio = null;
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
						low = parseFloat(low);
						high = parseFloat(high);
						if (modelAttrType == "float_range") value = parseFloat(value);
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
			} else if (modelAttrType == "float") {
				if ($("#ratio").val() != "") {
					ratio = parseFloat($("#ratio").val());
					if (isNaN(ratio)) {
						$("#mapping_messages").html("");
						$("#mapping_messages").html("<s:text name='error.field_isNaN'/>");
						error = true;
					}
				} else ratio = null;
			}
			if (!error) {
				$("#mapping_messages").html("");
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
				// Resetear el contador de lineas
				lineCounter = 0;
				// Resetear mapping_cfg y mapping_control
				$("#mapping_cfg").html("");
				$("#mapping_control").html("");
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
				$(".mapping_line:last").append("<td rowspan='" + rowspan + "'><a href='javascript:void(0)' class='remove_mapping'><s:text name='label.remove_mapping'/></a></td>");
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
				$("#mapping_messages").html("<s:text name='message.mapping_successful'/>");
			}
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
		$("#mapping_messages").html("<s:text name='message.mapping_deleted'/>");
		if (mappings.length == 0) {
			// TODO <s:text name='message.no_mappings'/>
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
		$(lineSelector).append("<span><a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'><s:text name='label.remove_configuration_line'/></a></span>");
		$(lineSelector).append("<div style='clear:both;' />");
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
		createSequentialColorPicker(lineSelector);
		$(lineSelector).append("<span><a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'><s:text name='label.remove_configuration_line'/></a></span>");
		$(lineSelector).append("<div style='clear:both;' />");
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
	function createSequentialColorPicker(divSelector) {
		colorPickerCounter++;
		var id = "colorPicker" + colorPickerCounter;
		var selector = "#" + id;
		
		buildColorPicker(divSelector, selector, id);
	}
	
	function createColorPicker(divSelector, id) {
		var selector = "#" + id;
		
		buildColorPicker(divSelector, selector, id);
	}
	
	function buildColorPicker(divSelector, selector, id) {
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
		createSequentialColorPicker(lineSelector);
		$(lineSelector).append("<span><input type='text' id='text' name='text' style='float: left;'/></span>");
		$(lineSelector).append("<span><a href=\"javascript:removeInputLine('" + lineId + "')\" style='float: left;'>-</a></span>");
		$(lineSelector).append("<div style='clear:both;' />");
		$("#added_captionLines").append("</div>");
	}
	
	function configureNonMappedModelAttributes() {
		nonMappedModelAttributesArray = new Array();
		$("#modelAttributesDiv").children('ul').children('li').each(function(){
			if ($(this).css('display') != 'none') {
				nonMappedModelAttributesArray.push($(this).attr('id').replace("modelAttr_", ""));	
			}
		});
		$("#nonMappedModelAttributesDiv").html("<ul>");
		$.each(nonMappedModelAttributesArray, function() {
			$("#nonMappedModelAttributesDiv > ul").append("<li>");
			$("#nonMappedModelAttributesDiv > ul > li:last").append("<label for='constant_" + this + "'>" + this + " (" + modelAttributesArray[this] + ")</label>");
			if (modelAttributesArray[this] == "color") {
				createColorPicker("#nonMappedModelAttributesDiv > ul > li:last", "constant_" + this);
			} else {
				$("#nonMappedModelAttributesDiv > ul > li:last").append("<span><input id='constant_" + this + "' type='text' value='' /></span>");
			}
			$("#nonMappedModelAttributesDiv > ul").append("</li>");
		});
		$("#nonMappedModelAttributesDiv").append("</ul>");
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
			// Comprobar que no quedan nonMappedModelAttributes sin valor por defecto y que el valor corresponde al tipo
			$("#nonMappedModelAttributesDiv > ul > li > input").each(function() {
				var attrName = this;
				var attrType = modelAttributesArray[attrName];
				var attrValue = $(this).val();
				// TODO
			});
			// Comprobar que no hay caption lines sin texto
			$("#added_captionLines > .caption_line").each(function(index, element) {
				var label = $(element).children("span").children("#text").val();
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
			$.post ("/desglosa-web/saveProfile.action",
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
			
		}
	}
	
	function toggleSteps(forward) {
		if (forward) {
			configureNonMappedModelAttributes()
		}
		$("#first_step").toggle();
		$("#second_step").toggle();
	}

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
		
		<div id="panes">
			<div id="leftPane">
				<sj:select 	href="%{updateProfileURL}"
							emptyOption="false"
							headerKey="-1"
							headerValue="-- %{getText('label.entity_selection')} --"
							disabled="option:first"
							id="entitySelect"
							name="entity"
							list="entities"
							onAlwaysTopics="disableHeader"
							onChangeTopics="reloadEntityAttributes"/>
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
							onAlwaysTopics="disableHeader"
							onChangeTopics="reloadModelAttributes"/>
				<sj:div id="modelAttributesDiv" selectableOnStopTopics="onstop" selectable="true" selectableFilter="li"></sj:div>
			</div>
		</div>
		
		<div id="mapping">
		
			<!-- Table markup-->
			<table id="mapping_added">
			
				<!-- Table header -->
				<thead id="mapping_added_header">
					<tr class="header">
						<th colspan="7"><s:text name="label.configured_mappings"/></th>
					</tr>
					<tr class="subheader">
						<th class="first"><s:text name="label.entity_attribute"/></th>
						<th><s:text name="label.model_attribute"/></th>
						<th><s:text name="label.range_low"/></th>
						<th><s:text name="label.range_high"/></th>
						<th><s:text name="label.range_value"/></th>
						<th><s:text name="label.ratio"/></th>
						<th class="last"><s:text name="label.delete_mapping"/></th>
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
		<a href="javascript:toggleSteps(true)"><s:text name="label.next"/> &gt;</a>
	</div>
	
	<div id="second_step">
		<div id="nonMappedPane">
			<s:text name="label.configure_nonMapped_Attributes"/>
			<div id="nonMappedModelAttributesDiv"></div>
		</div>
		<div id="captionPanel">
			<s:text name="label.configure_caption_lines"/>
			<div id="added_captionLines"></div>
			<a href="javascript:addCaptionLine()"><s:text name="label.add_caption_line"/></a>
		</div>
		<div id="profilePane">
			<ul>
				<li>
					<label for="profileName"><s:text name="label.profile_name"/></label>
					<input id="profileName" name="profileName" type="text"/>
				</li>
				<li>
					<label for="profileDescription"><s:text name="label.profile_description"/></label>
					<textarea id="profileDescription" name="profileDescription" rows="3" cols="15"></textarea>
				</li>
			</ul>
		</div>
		<a href="javascript:toggleSteps(false)">&lt; <s:text name="label.back"/></a>
		<a href="javascript:saveProfile()">Save profile</a>
	</div>
</body>
</html>