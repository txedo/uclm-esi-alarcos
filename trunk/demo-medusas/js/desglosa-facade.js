function selectTower(id, clickButton, clickCount) {
	handleSelectionEvent(id, clickButton, clickCount);
}

function selectionError(message) {
	alert(message);
}

function handleSelectionEvent(id, clickButton, clickCount) {
	switch (clickButton) {
		case 1:		// Left button
			handleClick(clickCount, "izquierdo", id);
			break;
		case 2:		// Middle button
			handleClick(clickCount, "central", id);
			break;
		case 3:		// Right button
			handleClick(clickCount, "derecho", id);
			break;
		default:	// Any other button
			break;
	}
}

function handleClick (clickCount, button, id) {
	switch (clickCount) {
		case 1:		// Click
			alert("Se ha detectado un clic " + button + " en el modelo " + id);
			break;
		case 2:		// Double click
			alert("Se ha detectado doble clic " + button + " en el modelo " + id);
			break;
		default:	// Ignore multiple clicks but double click
			break;
	}
}