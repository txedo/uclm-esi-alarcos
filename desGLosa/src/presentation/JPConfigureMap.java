package presentation;

import java.awt.Dimension;

import org.jdesktop.application.Application;


public class JPConfigureMap extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965274680612432098L;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	
	public JPConfigureMap() {
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
