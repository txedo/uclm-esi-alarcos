package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
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
public class JPConfigureCompany extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5515867769356310203L;
	private JTextField txtCompanyName;
	private JLabel lblCompanyInformation;
	private JButton btnCancel;
	private JButton btnOk;
	private JTextArea txtCompanyInformation;
	private JLabel lblName;

	public JPConfigureCompany() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"5dlu, max(p;5dlu), 6dlu, 60dlu, 23dlu, 60dlu", 
					"5dlu, max(p;5dlu), 5dlu, max(p;5dlu), 5dlu, max(p;5dlu), max(p;5dlu)");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(456, 333));
			{
				btnOk = new JButton();
				this.add(btnOk, new CellConstraints("4, 6, 1, 1, default, default"));
				btnOk.setPreferredSize(new java.awt.Dimension(79, 23));
				btnOk.setName("btnOk");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnOkActionPerformed(evt);
					}
				});
			}
			{
				btnCancel = new JButton();
				this.add(btnCancel, new CellConstraints("6, 6, 1, 1, default, default"));
				btnCancel.setPreferredSize(new java.awt.Dimension(79, 23));
				btnCancel.setName("btnCancel");
			}
			{
				lblCompanyInformation = new JLabel();
				this.add(lblCompanyInformation, new CellConstraints("2, 4, 1, 1, default, default"));
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
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnOkActionPerformed(ActionEvent evt) {
    	try {
    		Company c = BusinessFactory.createCompany(txtCompanyName.getText(), txtCompanyInformation.getText());
			BusinessManager.addCompany(c);
			Messages.showInfoDialog(null, "Information", "Company successfully added.");
			txtCompanyName.setText("");
			txtCompanyInformation.setText("");
		} catch (CompanyAlreadyExistsException e) {
			Messages.showErrorDialog(null, "Error", "This company already exists.");
		} catch (MandatoryFieldException e) {
			Messages.showErrorDialog(null, "Error", "The company name field must be filled in.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
