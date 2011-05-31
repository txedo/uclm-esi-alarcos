package presentation.utils;

import javax.swing.text.JTextComponent;

import exceptions.MandatoryFieldException;

public class ComponentHandler {
	public static void handleInputTextField (JTextComponent textComponent, boolean mandatory, String errorMessage) throws MandatoryFieldException {
		if (textComponent.getText().equals("") && mandatory) {
			textComponent.requestFocus();
			throw new MandatoryFieldException(errorMessage);
		}
	}
}
