package presentation;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.CompanyAlreadyExistsException;
import exceptions.MandatoryFieldException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.business.control.BusinessManager;
import model.business.knowledge.BusinessFactory;
import model.business.knowledge.Company;

import org.jdesktop.application.Application;

import presentation.utils.Messages;

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
public class JPConfigureCompany extends javax.swing.JPanel implements JPConfigureInterface, WindowNotifierObserverInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5515867769356310203L;
	private JTextField txtCompanyName;
	private JLabel lblCompanyInformation;
	private JButton btnCreate;
	private JButton btnReset;
	private JTextArea txtCompanyInformation;
	private JLabel lblName;

	public JPConfigureCompany() {
		super();
		initGUI();
		txtCompanyName.grabFocus();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"5dlu, max(p;5dlu), 5dlu, 58dlu, 36dlu, p:grow, 5dlu", 
					"5dlu, max(p;5dlu), 5dlu, max(p;5dlu), 5dlu, max(p;5dlu)");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(410, 180));
			{
				btnCreate = new JButton();
				this.add(btnCreate, new CellConstraints("4, 6, 1, 1, default, default"));
				btnCreate.setPreferredSize(new java.awt.Dimension(79, 23));
				btnCreate.setName("btnCreate");
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnCreateActionPerformed(evt);
					}
				});
			}
			{
				lblCompanyInformation = new JLabel();
				this.add(lblCompanyInformation, new CellConstraints("2, 4, 1, 1, default, top"));
				lblCompanyInformation.setPreferredSize(new java.awt.Dimension(124, 16));
				lblCompanyInformation.setName("lblCompanyInformation");
			}
			{
				txtCompanyInformation = new JTextArea();
				this.add(txtCompanyInformation, new CellConstraints("4, 4, 3, 1, default, default"));
				txtCompanyInformation.setPreferredSize(new java.awt.Dimension(207, 93));
				txtCompanyInformation.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			}
			{
				txtCompanyName = new JTextField();
				this.add(txtCompanyName, new CellConstraints("4, 2, 3, 1, default, default"));
				txtCompanyName.setPreferredSize(new java.awt.Dimension(207, 23));
			}
			{
				lblName = new JLabel();
				this.add(lblName, new CellConstraints("2, 2, 1, 1, default, default"));
				lblName.setName("lblName");
				lblName.setPreferredSize(new java.awt.Dimension(111, 19));
			}
			{
				btnReset = new JButton();
				this.add(btnReset, new CellConstraints("6, 6, 1, 1, default, default"));
				btnReset.setName("btnReset");
				btnReset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnResetActionPerformed(evt);
					}
				});
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnCreateActionPerformed(ActionEvent evt) {
    	try {
    		Company c = BusinessFactory.createCompany(txtCompanyName.getText(), txtCompanyInformation.getText());
			BusinessManager.addCompany(c);
			Messages.getInstance().showInfoDialog(null, "Information", "Company successfully added.");
			txtCompanyName.setText("");
			txtCompanyInformation.setText("");
		} catch (CompanyAlreadyExistsException e) {
			Messages.getInstance().showErrorDialog(null, "Error", "This company already exists.");
		} catch (MandatoryFieldException e) {
			Messages.getInstance().showErrorDialog(null, "Error", "The company name field must be filled in.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setApplicationCore (IAppCore applicationCore) {
	}

	public boolean isApplicationCoreNeeded () {
		return false;
	}

	public void manageOperation(WindowNotifierOperationCodes code, Object... objects) {
		// TODO Auto-generated method stub
		
	}
	
	private void btnResetActionPerformed(ActionEvent evt) {
		txtCompanyName.setText("");
		txtCompanyInformation.setText("");
		txtCompanyName.grabFocus();
	}

}
