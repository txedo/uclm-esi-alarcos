package presentation;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Dimension;

import model.business.knowledge.Factory;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JPInfoFactory extends javax.swing.JPanel {

	private static final long serialVersionUID = -2578806940827523547L;

	public JPInfoFactory() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 83dlu, 5dlu, 92dlu", 
					"max(p;5dlu), 13dlu, 5dlu, 13dlu");
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayInformation (Factory factory) {
		
	}

}
