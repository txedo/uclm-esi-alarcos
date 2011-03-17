package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;

import java.awt.Dimension;
import javax.swing.BorderFactory;

import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import org.jdesktop.application.Application;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class JPInfoCompany extends javax.swing.JPanel implements JPInfoInterface {
	private JLabel lblCompanyName;
	private JLabel lblCompanyInformation;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	
	public JPInfoCompany() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 135dlu", 
					"13dlu, max(p;5dlu), 13dlu");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(261, 89));
			this.setBorder(BorderFactory.createTitledBorder("Company information"));
			{
				lblCompanyName = new JLabel();
				this.add(lblCompanyName, new CellConstraints("2, 1, 1, 1, fill, fill"));
			}
			{
				lblCompanyInformation = new JLabel();
				this.add(lblCompanyInformation, new CellConstraints("2, 3, 1, 1, fill, fill"));
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInformation(Object obj) {
		// TODO Auto-generated method stub
		
	}

}
