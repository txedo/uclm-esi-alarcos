package presentation;

import java.awt.Dimension;

import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class JPConfigureProject extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1886335960423908741L;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JPConfigureProject());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public JPConfigureProject() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
