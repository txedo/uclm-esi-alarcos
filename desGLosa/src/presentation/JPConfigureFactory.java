package presentation;

import java.awt.Dimension;

import org.jdesktop.application.Application;


public class JPConfigureFactory extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1766981428252115851L;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	
	public JPConfigureFactory() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
