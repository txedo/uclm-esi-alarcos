package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;

import org.jdesktop.application.Application;

import presentation.utils.FactoryComboBox;
import presentation.utils.FactoryList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;

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
public class JPConfigureProject extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1886335960423908741L;
	private JLabel lblInvolvedFactories;
	private JLabel lblAvailableFactories;
	private JButton btnAddInvolvedFactory;
	private JButton btnRemoveInvolvedFactory;
	private JList jListInvolvedFactories;
	private JComboBox cbFactories;
	private JLabel lblWorkingFactory;
	private JList jListAvailableFactories;
	
	public JPConfigureProject() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 65dlu, max(p;5dlu), 45dlu, 5dlu, 69dlu", 
					"10dlu, 13dlu, max(p;5dlu), 13dlu, 14dlu, 13dlu, 5dlu, max(p;15dlu), 29dlu");
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				lblInvolvedFactories = new JLabel();
				this.add(lblInvolvedFactories, new CellConstraints("2, 4, 1, 1, default, default"));
				lblInvolvedFactories.setName("lblInvolvedFactories");
			}
			{
				lblAvailableFactories = new JLabel();
				this.add(lblAvailableFactories, new CellConstraints("6, 4, 1, 1, default, default"));
				lblAvailableFactories.setName("lblAvailableFactories");
			}
			{
				btnAddInvolvedFactory = new JButton();
				this.add(btnAddInvolvedFactory, new CellConstraints("4, 6, 1, 1, default, default"));
				btnAddInvolvedFactory.setName("btnAddInvolvedFactory");
			}
			{
				btnRemoveInvolvedFactory = new JButton();
				this.add(btnRemoveInvolvedFactory, new CellConstraints("4, 8, 1, 1, default, default"));
				btnRemoveInvolvedFactory.setName("btnRemoveInvolvedFactory");
			}
			{
				ListModel jListInvolvedFactoriesModel = new DefaultComboBoxModel();
				jListInvolvedFactories = new JList();
				this.add(jListInvolvedFactories, new CellConstraints("2, 5, 1, 5, default, default"));
				jListInvolvedFactories.setModel(jListInvolvedFactoriesModel);
			}
			{
				jListAvailableFactories = new FactoryList();
				this.add(jListAvailableFactories, new CellConstraints("6, 5, 1, 5, default, default"));
			}
			{
				lblWorkingFactory = new JLabel();
				this.add(lblWorkingFactory, new CellConstraints("2, 2, 1, 1, default, default"));
				lblWorkingFactory.setName("lblWorkingFactory");
			}
			{
				cbFactories = new FactoryComboBox();
				this.add(cbFactories, new CellConstraints("4, 2, 3, 1, default, default"));
				cbFactories.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbFactoriesActionPerformed(evt);
					}
				});
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cbFactoriesActionPerformed(ActionEvent evt) {
		if (cbFactories.getSelectedIndex() != -1) {
			for (int i = 0; i < jListAvailableFactories.getModel().getSize(); i++) {
				if (jListAvailableFactories.getModel().getElementAt(i).equals(cbFactories.getSelectedItem())) {
					DefaultListModel;
				}
			}
		}
	}

}
