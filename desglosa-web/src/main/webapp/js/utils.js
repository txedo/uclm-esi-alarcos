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