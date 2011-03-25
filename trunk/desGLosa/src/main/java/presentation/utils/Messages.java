package presentation.utils;

import java.awt.Container;

import javax.swing.JOptionPane;

public class Messages {
	private final int INFO_DIALOG = 1;
	private final int ERROR_DIALOG = 2;
	private final int WARNING_DIALOG = 3;
	private final int QUESTION_DIALOG = 4;	// Not threaded because of option feedback
	
	static private Messages _instance = null;
	
	protected Messages () {}
	
	static public Messages getInstance() {
		if (null == _instance) {
			_instance = new Messages();
		}
		return _instance;
	}
	
	public void showInfoDialog(Container parent, String title, String message) {
		Thread t = new Thread(new ThreadedMessage(this.INFO_DIALOG, parent, title, message));
		t.start();
	}

	public void showErrorDialog(Container parent, String title, String message) {
		Thread t = new Thread(new ThreadedMessage(this.ERROR_DIALOG, parent, title, message));
		t.start();
	}
	
	public void showWarningDialog(Container parent, String title, String message) {
		Thread t = new Thread(new ThreadedMessage(this.WARNING_DIALOG, parent, title, message));
		t.start();
	}
	
	public boolean showQuestionDialog(Container parent, String title, String message) {
		return (JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
	}
	
	private class ThreadedMessage implements Runnable {
		private int type;
		private Container parent;
		private String title;
		private String message;

		public ThreadedMessage (int type, Container parent, String title, String message) {
			this.type = type;
			this.parent = parent;
			this.title = title;
			this.message = message;
		}
		
		public void run() {
			// INFO_DIALOG is default type
			switch (this.type) {
			case ERROR_DIALOG:
				JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
				break;
			case WARNING_DIALOG:
				JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
		
	}
	
}
