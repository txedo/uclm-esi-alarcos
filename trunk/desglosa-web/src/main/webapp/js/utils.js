function isFloat(val) {
	var result = false;
	if (getType(val) == "float") result = true;
	return result;
}

function isInt(val) {
	var result = false;
	if (getType(val) == "int") result = true;
	return result;
}

function getType (val) {
	var result = null
	if (val && typeof val == "string" && val.constructor == String) {
		var isNumber = !isNaN(new Number(val));
		if (isNumber) {
			if (val.indexOf('.') != -1 && val.split('.').length == 2) result = "float";
			else result = "int";
		} else {
			result = "string";
		}
	}
	return result;
}

function swapDivVisibility (hideSelector, showSelector) {
	$(hideSelector).css('display','none');
	$(showSelector).css('display','');
}


function getMarketSpan (color, name) {
	var result = "<span>-</span>";
	if (color != "" && name != "") {
		result = "<span class='icon' style='background-color:#" + color + "'></span>" + name;
	}
	return result;
}


///////////////////////////////////////////////////////////////////////////////////////////
///// This check methods are designed in order to format float fields in measure forms ////
///////////////////////////////////////////////////////////////////////////////////////////

function checkInputFields() {
	var submit = false;
	var floatOk = checkFloatFields();
	var intOk = checkIntFields();
	if (floatOk && intOk) {
		// format all float fields to have "," as decimal separator in order to work with xwork and struts and expected
		// if decimal separator is ".", then the value will be INCORRECT in the value stack"
		formatFloatFields();
		submit = true;
	}
	return submit;
}

function checkFloatFields() {
    var anyError = false;
    $("input.floatNumber[type=text]").each(function(index) {
    	var fieldError = false;
    	// if value is an integer, format it to float
    	if(isInt($(this).val())) {
    		$(this).val($(this).val() + ".0");
    	}
    	// accept both "." and "," as decimal separator
        if(!isFloat($(this).val().replace(",", ".")) || $(this).val() < 0) {
            anyError = true;
            fieldError = true;
        }
        handleFieldStyle(this, fieldError);
    });
    return !anyError;
}

function checkIntFields() {
	var anyError = false;
    $("input.intNumber[type=text]").each(function(index) {
    	var fieldError = false;
        if(!isInt($(this).val()) || $(this).val() < 0) {
        	anyError = true;
        	fieldError = true;
        }
        handleFieldStyle(this, fieldError);
    });
    return !anyError;
}

function formatFloatFields() {
    $("input.floatNumber[type=text]").each(function(index) {
        $(this).val($(this).val().replace(".", ","));
    });
}

function handleFieldStyle(field, error) {
	if (error) {
		$(field).addClass("error");
	} else if ($(field).hasClass("error")) {
		$(field).removeClass("error");
	}
}

///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// End of formating measure fields /////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
