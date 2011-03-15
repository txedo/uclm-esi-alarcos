package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.FactoryNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.WorkingFactoryIsNotInvolvedFactoryException;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextField;

import model.business.control.BusinessManager;
import model.business.knowledge.BusinessFactory;
import model.business.knowledge.Factory;
import model.business.knowledge.Project;

import org.jdesktop.application.Application;

import presentation.utils.FactoryJComboBox;
import presentation.utils.FactoryJList;
import presentation.utils.Messages;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

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
	private FactoryJList jListInvolvedFactories;
	private JTextField txtProjectCode;
	private JLabel lblProjectCode;
	private JLabel lblProjectPlanName;
	private FactoryJList jListAvailableFactories;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane1;
	private JButton btnCancel;
	private JButton btnOk;
	private JTextField txtProjectPlanName;
	private FactoryJComboBox cbFactories;
	private JLabel lblWorkingFactory;
	
	private Factory workingFactory;
	
	public JPConfigureProject() throws SQLException {
		super();
		initGUI();
		jListAvailableFactories.loadFactories();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 65dlu, max(p;5dlu), 62dlu, 5dlu, 69dlu", 
					"5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, 13dlu, max(p;5dlu), 13dlu, 14dlu, 13dlu, 5dlu, max(p;15dlu), 5dlu, 13dlu, 5dlu, max(p;15dlu)");
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				lblInvolvedFactories = new JLabel();
				this.add(lblInvolvedFactories, new CellConstraints("2, 6, 1, 1, default, default"));
				lblInvolvedFactories.setName("lblInvolvedFactories");
			}
			{
				lblAvailableFactories = new JLabel();
				this.add(lblAvailableFactories, new CellConstraints("6, 6, 1, 1, default, default"));
				lblAvailableFactories.setName("lblAvailableFactories");
			}
			{
				btnAddInvolvedFactory = new JButton();
				this.add(btnAddInvolvedFactory, new CellConstraints("4, 9, 1, 1, default, default"));
				btnAddInvolvedFactory.setName("btnAddInvolvedFactory");
				btnAddInvolvedFactory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddInvolvedFactoryActionPerformed(evt);
					}
				});
			}
			{
				btnRemoveInvolvedFactory = new JButton();
				this.add(btnRemoveInvolvedFactory, new CellConstraints("4, 10, 1, 1, default, default"));
				btnRemoveInvolvedFactory.setName("btnRemoveInvolvedFactory");
				btnRemoveInvolvedFactory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnRemoveInvolvedFactoryActionPerformed(evt);
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, new CellConstraints("2, 7, 1, 6, default, default"));
				jScrollPane1.setBounds(0, 0, 114, 97);
				{
					jListInvolvedFactories = new FactoryJList();
					jScrollPane1.setViewportView(jListInvolvedFactories);
					jListInvolvedFactories.setName("jListInvolvedFactories");
				}
			}
			{
				jScrollPane2 = new JScrollPane();
				this.add(jScrollPane2, new CellConstraints("6, 7, 1, 6, default, default"));
				jScrollPane2.setBounds(40, 5, 10, 10);
				{
					jListAvailableFactories = new FactoryJList();
					jScrollPane2.setViewportView(jListAvailableFactories);
					jListAvailableFactories.setPreferredSize(new java.awt.Dimension(118, 98));
				}
			}
			{
				lblWorkingFactory = new JLabel();
				this.add(lblWorkingFactory, new CellConstraints("2, 14, 1, 1, default, default"));
				lblWorkingFactory.setName("lblWorkingFactory");
			}
			{
				cbFactories = new FactoryJComboBox();
				this.add(cbFactories, new CellConstraints("4, 14, 3, 1, default, default"));
			}
			{
				lblProjectPlanName = new JLabel();
				this.add(lblProjectPlanName, new CellConstraints("2, 4, 1, 1, default, default"));
				lblProjectPlanName.setName("lblProjectPlanName");
			}
			{
				lblProjectCode = new JLabel();
				this.add(lblProjectCode, new CellConstraints("2, 2, 1, 1, default, default"));
				lblProjectCode.setName("lblProjectCode");
			}
			{
				txtProjectCode = new JTextField();
				this.add(txtProjectCode, new CellConstraints("4, 2, 3, 1, default, default"));
			}
			{
				txtProjectPlanName = new JTextField();
				this.add(txtProjectPlanName, new CellConstraints("4, 4, 3, 1, default, default"));
			}
			{
				btnOk = new JButton();
				this.add(btnOk, new CellConstraints("4, 16, 1, 1, default, default"));
				btnOk.setName("btnOk");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnOkActionPerformed(evt);
					}
				});
			}
			{
				btnCancel = new JButton();
				this.add(btnCancel, new CellConstraints("6, 16, 1, 1, default, default"));
				btnCancel.setName("btnCancel");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void factorySelector (FactoryJList source, FactoryJList dest, Factory factory) {
		source.removeFactory(factory);
		source.validate();
		dest.addFactory(factory);
		dest.validate();
	}
	
	private void btnAddInvolvedFactoryActionPerformed(ActionEvent evt) {
		for (Object factory : jListAvailableFactories.getSelectedValues()) {
			this.factorySelector(jListAvailableFactories, jListInvolvedFactories, (Factory)factory);
		}
		cbFactories.load(jListInvolvedFactories.toList());
	}
	
	private void btnRemoveInvolvedFactoryActionPerformed(ActionEvent evt) {
		for (Object factory : jListInvolvedFactories.getSelectedValues()) {
			this.factorySelector(jListInvolvedFactories, jListAvailableFactories, (Factory)factory);	
		}
		cbFactories.load(jListInvolvedFactories.toList());
	}
	
	private void btnOkActionPerformed(ActionEvent evt) {
		try {
			Project project = BusinessFactory.createProject(txtProjectCode.getText(), txtProjectPlanName.getText(), (Factory)cbFactories.getSelectedItem(), jListInvolvedFactories.toList());
			if (BusinessManager.addProject(project)) {
				Messages.showInfoDialog(getRootPane(), "Operation successful", "The project has been created successfully.");
			}
		} catch (WorkingFactoryIsNotInvolvedFactoryException e) {
			Messages.showWarningDialog(getRootPane(), "Warning!", "Working factory has not been added as involved factory. Retry this operation, please.");
			this.factorySelector(jListAvailableFactories, jListInvolvedFactories, workingFactory);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
