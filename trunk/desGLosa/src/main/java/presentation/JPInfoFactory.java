package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

import javax.swing.WindowConstants;

import model.business.knowledge.Factory;

import org.jdesktop.application.Application;

import presentation.utils.Messages;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

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
public class JPInfoFactory extends javax.swing.JPanel implements JPInfoInterface {
	private static final long serialVersionUID = 7897432537712177667L;
	private JLabel lblFactoryName;
	private JLabel lblFactoryInfo;
	private JLabel lblContact;
	private JPInfoCompany companyPanel;
	private JLabel lblEmployees;

	public JPInfoFactory() {
		super();
		initGUI();
	}
	
	public JPInfoFactory(Factory factory) {
		super();
		initGUI();
		this.displayInformation(factory);
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(220, 300));
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 112dlu", 
					"max(p;15dlu), 5dlu, 13dlu, max(p;5dlu), 13dlu, max(p;5dlu), max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu)");
			this.setLayout(thisLayout);
			this.setBorder(BorderFactory.createTitledBorder("Factory information"));
			{
				lblFactoryName = new JLabel();
				this.add(lblFactoryName, new CellConstraints("2, 3, 1, 1, fill, fill"));
			}
			{
				lblFactoryInfo = new JLabel();
				this.add(lblFactoryInfo, new CellConstraints("2, 5, 1, 1, fill, fill"));
			}
			{
				lblEmployees = new JLabel();
				this.add(lblEmployees, new CellConstraints("2, 9, 1, 1, fill, fill"));
				lblEmployees.setName("lblEmployees");
			}
			{
				companyPanel = new JPInfoCompany();
				this.add(companyPanel, new CellConstraints("2, 1, 1, 1, fill, fill"));
			}
			{
				lblContact = new JLabel();
				this.add(lblContact, new CellConstraints("2, 7, 1, 1, default, default"));
				lblContact.setName("lblContact");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInformation(Object obj) {
		if (obj instanceof Factory) {
			Factory factory = (Factory)obj;
			companyPanel.displayInformation(factory.getCompany());
			lblFactoryName.setText(factory.getName());
			lblFactoryInfo.setText(factory.getInformation());
			lblContact.setText("Contact: " + factory.getEmail());
			lblEmployees.setText(factory.getEmployees() + " employees.");
		} else {
			Messages.getInstance().showErrorDialog(getRootPane(), "Runtime error", "Passed argument is not a factory.");
		}
	}

}
