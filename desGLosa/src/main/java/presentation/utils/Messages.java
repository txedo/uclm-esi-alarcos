package presentation.utils;

import java.awt.Container;

import javax.swing.JOptionPane;

/**
 * Clase con métodos estáticos para mostrar cuadros de diálogo.
 */
public class Messages {
	
	public static void showInfoDialog(Container parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showErrorDialog(Container parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarningDialog(Container parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static boolean showQuestionDialog(Container parent, String title, String message) {
		return (JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
	}
	
}
