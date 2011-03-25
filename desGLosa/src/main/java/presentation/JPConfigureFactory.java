package presentation;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.MandatoryFieldException;
import exceptions.FactoryAlreadyExistsException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import model.business.control.BusinessManager;
import model.business.knowledge.Address;
import model.business.knowledge.BusinessFactory;
import model.business.knowledge.Company;
import model.business.knowledge.Director;
import model.business.knowledge.Factory;

import org.jdesktop.application.Application;

import presentation.utils.ComponentHandler;
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
public class JPConfigureFactory extends javax.swing.JPanel implements JPConfigureInterface, WindowNotifierObserverInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1766981428252115851L;
	private JPanel factoryPanel;
	private JLabel lblFactoryName;
	private JTextField txtFactoryName;
	private JTextField txtZip;
	private JTextField txtName;
	private JSpinner txtEmployees;
	private JComboBox cbCompanies;
	private JLabel txtCompany;
	private JButton btnCancel;
	private JButton btnOk;
	private JButton btnBrowse;
	private JTextField txtPicture;
	private JTextField txtLastSurname;
	private JTextField txtSurname;
	private JLabel lblPicture;
	private JLabel lblState;
	private JLabel lblSurname;
	private JLabel lblLastSurname;
	private JLabel lblName;
	private JPanel directorPanel;
	private JTextField txtState;
	private JTextField txtCountry;
	private JLabel lblCountry;
	private JLabel lblZip;
	private JTextField txtCity;
	private JTextField txtStreet;
	private JLabel lblFactoryInformation;
	private JLabel lblCity;
	private JLabel lblStreet;
	private JPanel addressPanel;
	private JLabel lblEmployees;
	private JTextField txtEmail;
	private JLabel lblEmail;
	private JTextPane txtFactoryInformation;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	
	public JPConfigureFactory() {
		super();
		initGUI();
		
		try {
			fillCompanies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cbCompanies.setSelectedIndex(-1);
		enableInputFields(false);
	}
	
	private void fillCompanies() throws SQLException {
		// Clean already added companies
		cbCompanies.setModel(new DefaultComboBoxModel());
		ArrayList<Company> companies = (ArrayList<Company>)BusinessManager.getAllCompanies();
		for (Company c : companies) {
			cbCompanies.addItem(c);
		}
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 79dlu, 5dlu, 79dlu, 45dlu, 4dlu, 47dlu", 
					"5dlu, max(p;15dlu), max(p;5dlu), 119dlu, 5dlu, 5dlu, 65dlu, 5dlu, 5dlu, 85dlu, 10dlu, max(p;15dlu)");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(473, 563));
			{
				factoryPanel = new JPanel();
				FormLayout factoryPanelLayout = new FormLayout(
						"max(p;5dlu), 77dlu, 11dlu, 155dlu", 
						"15dlu, 5dlu, 15dlu, 27dlu, 5dlu, 15dlu, 3dlu, 15dlu");
				factoryPanel.setLayout(factoryPanelLayout);
				this.add(factoryPanel, new CellConstraints("2, 4, 6, 1, default, top"));
				factoryPanel.setBorder(BorderFactory.createTitledBorder("Basic factory information"));
				factoryPanel.setPreferredSize(new java.awt.Dimension(436, 191));
				{
					lblFactoryName = new JLabel();
					factoryPanel.add(lblFactoryName, new CellConstraints("2, 1, 1, 1, default, default"));
					lblFactoryName.setName("lblFactoryName");
				}
				{
					txtFactoryName = new JTextField();
					factoryPanel.add(txtFactoryName, new CellConstraints("4, 1, 1, 1, default, default"));
					txtFactoryName.setName("txtFactoryName");
				}
				{
					lblFactoryInformation = new JLabel();
					factoryPanel.add(lblFactoryInformation, new CellConstraints("2, 3, 1, 1, default, default"));
					lblFactoryInformation.setName("lblFactoryInformation");
				}
				{
					txtFactoryInformation = new JTextPane();
					factoryPanel.add(txtFactoryInformation, new CellConstraints("4, 3, 1, 2, default, default"));
					txtFactoryInformation.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					lblEmail = new JLabel();
					factoryPanel.add(lblEmail, new CellConstraints("2, 6, 1, 1, default, default"));
					lblEmail.setName("lblEmail");
				}
				{
					txtEmail = new JTextField();
					factoryPanel.add(txtEmail, new CellConstraints("4, 6, 1, 1, default, default"));
				}
				{
					lblEmployees = new JLabel();
					factoryPanel.add(lblEmployees, new CellConstraints("2, 8, 1, 1, default, default"));
					lblEmployees.setName("lblEmployees");
				}
				{
					SpinnerNumberModel txtEmployeesModel = new SpinnerNumberModel();
					txtEmployees = new JSpinner();
					factoryPanel.add(txtEmployees, new CellConstraints("4, 8, 1, 1, default, default"));
					txtEmployees.setModel(txtEmployeesModel);
				}
			}
			{
				addressPanel = new JPanel();
				FormLayout addressPanelLayout = new FormLayout(
						"max(p;5dlu), 59dlu, max(p;5dlu), 73dlu, 5dlu, max(p;15dlu), 5dlu, 70dlu", 
						"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;15dlu)");
				addressPanel.setLayout(addressPanelLayout);
				this.add(addressPanel, new CellConstraints("2, 6, 6, 2, default, top"));
				addressPanel.setBorder(BorderFactory.createTitledBorder("Address information"));
				{
					lblStreet = new JLabel();
					addressPanel.add(lblStreet, new CellConstraints("2, 1, 1, 1, default, default"));
					lblStreet.setName("lblStreet");
				}
				{
					lblCity = new JLabel();
					addressPanel.add(lblCity, new CellConstraints("2, 3, 1, 1, default, default"));
					lblCity.setName("lblCity");
				}
				{
					txtStreet = new JTextField();
					addressPanel.add(txtStreet, new CellConstraints("4, 1, 5, 1, default, default"));
				}
				{
					txtCity = new JTextField();
					addressPanel.add(txtCity, new CellConstraints("4, 3, 1, 1, default, default"));
				}
				{
					lblZip = new JLabel();
					addressPanel.add(lblZip, new CellConstraints("6, 3, 1, 1, default, default"));
					lblZip.setName("lblZip");
				}
				{
					txtZip = new JTextField();
					addressPanel.add(txtZip, new CellConstraints("8, 3, 1, 1, default, default"));
				}
				{
					lblCountry = new JLabel();
					addressPanel.add(lblCountry, new CellConstraints("6, 5, 1, 1, default, default"));
					lblCountry.setName("lblCountry");
				}
				{
					txtCountry = new JTextField();
					addressPanel.add(txtCountry, new CellConstraints("8, 5, 1, 1, default, default"));
				}
				{
					lblState = new JLabel();
					addressPanel.add(lblState, new CellConstraints("2, 5, 1, 1, default, default"));
					lblState.setName("lblState");
				}
				{
					txtState = new JTextField();
					addressPanel.add(txtState, new CellConstraints("4, 5, 1, 1, default, default"));
				}
			}
			{
				directorPanel = new JPanel();
				FormLayout directorPanelLayout = new FormLayout(
						"max(p;5dlu), 52dlu, 5dlu, 117dlu, 5dlu, 62dlu", 
						"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;15dlu), 5dlu, max(p;15dlu)");
				directorPanel.setLayout(directorPanelLayout);
				this.add(directorPanel, new CellConstraints("2, 9, 6, 2, default, top"));
				directorPanel.setPreferredSize(new java.awt.Dimension(436, 146));
				directorPanel.setBorder(BorderFactory.createTitledBorder("Director information"));
				{
					lblName = new JLabel();
					directorPanel.add(lblName, new CellConstraints("2, 1, 1, 1, default, default"));
					lblName.setName("lblName");
				}
				{
					lblSurname = new JLabel();
					directorPanel.add(lblSurname, new CellConstraints("2, 3, 1, 1, default, default"));
					lblSurname.setName("lblSurname");
				}
				{
					lblLastSurname = new JLabel();
					directorPanel.add(lblLastSurname, new CellConstraints("2, 5, 1, 1, default, default"));
					lblLastSurname.setName("lblLastSurname");
				}
				{
					lblPicture = new JLabel();
					directorPanel.add(lblPicture, new CellConstraints("2, 7, 1, 1, default, default"));
					lblPicture.setName("lblPicture");
				}
				{
					txtName = new JTextField();
					directorPanel.add(txtName, new CellConstraints("4, 1, 3, 1, default, default"));
				}
				{
					txtSurname = new JTextField();
					directorPanel.add(txtSurname, new CellConstraints("4, 3, 3, 1, default, default"));
				}
				{
					txtLastSurname = new JTextField();
					directorPanel.add(txtLastSurname, new CellConstraints("4, 5, 3, 1, default, default"));
				}
				{
					txtPicture = new JTextField();
					directorPanel.add(txtPicture, new CellConstraints("4, 7, 1, 1, default, default"));
				}
				{
					btnBrowse = new JButton();
					directorPanel.add(btnBrowse, new CellConstraints("6, 7, 1, 1, default, default"));
					btnBrowse.setName("btnBrowse");
				}
			}
			{
				btnOk = new JButton();
				this.add(btnOk, new CellConstraints("5, 12, 1, 1, default, default"));
				btnOk.setName("btnOk");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnOkActionPerformed(evt);
					}
				});
			}
			{
				btnCancel = new JButton();
				this.add(btnCancel, new CellConstraints("7, 12, 1, 1, default, default"));
				btnCancel.setName("btnCancel");
			}
			{
				txtCompany = new JLabel();
				this.add(txtCompany, new CellConstraints("2, 2, 1, 1, default, default"));
				txtCompany.setName("txtCompany");
			}
			{
				ComboBoxModel cbCompaniesModel = new DefaultComboBoxModel();
				cbCompanies = new JComboBox();
				this.add(cbCompanies, new CellConstraints("4, 2, 4, 1, default, default"));
				cbCompanies.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbCompaniesActionPerformed(evt);
					}
				});
				cbCompanies.setModel(cbCompaniesModel);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cleanInputFields () {
		// Basic factory information
		txtFactoryName.setText("");
		txtFactoryInformation.setText("");
		txtEmail.setText("");
		txtEmployees.setValue(0);
		// Address information
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtCountry.setText("");
		txtZip.setText("");
		// Director information
		txtName.setText("");
		txtSurname.setText("");
		txtLastSurname.setText("");
		txtPicture.setText("");
	}
	
	private void enableInputFields (boolean b) {
		// Basic factory information
		txtFactoryName.setEnabled(b);
		txtFactoryInformation.setEnabled(b);
		txtEmail.setEnabled(b);
		txtEmployees.setEnabled(b);
		// Address information
		txtStreet.setEnabled(b);
		txtCity.setEnabled(b);
		txtState.setEnabled(b);
		txtCountry.setEnabled(b);
		txtZip.setEnabled(b);
		// Director information
		txtName.setEnabled(b);
		txtSurname.setEnabled(b);
		txtLastSurname.setEnabled(b);
		txtPicture.setEnabled(b);
		btnBrowse.setEnabled(b);
		
		btnOk.setEnabled(b);
	}
	
	private void btnOkActionPerformed(ActionEvent evt) {
		try {
			if (cbCompanies.getSelectedIndex() == -1) throw new MandatoryFieldException ("A company must be selected in order to create a factory.");
			// Factory validation and verification
			ComponentHandler.handleInputTextField(txtFactoryName, true, "The factory name is mandatory.");
	
			// Address validation and verification
			ComponentHandler.handleInputTextField(txtStreet, true, "The street is mandatory.");
			ComponentHandler.handleInputTextField(txtCity, true, "The city is mandatory.");
			ComponentHandler.handleInputTextField(txtZip, true, "The zip is mandatory.");
			ComponentHandler.handleInputTextField(txtState, true, "The state is mandatory.");
			ComponentHandler.handleInputTextField(txtCountry, true, "The country is mandatory.");
			Address address = BusinessFactory.createAddress(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtCountry.getText(), txtZip.getText());
			
			// Director validation and verification
			ComponentHandler.handleInputTextField(txtName, true, "The director name is mandatory.");
			ComponentHandler.handleInputTextField(txtSurname, true, "The director last surname is mandatory.");
			String picture = "src/main/resources/anonymous.jpg";
			if (!txtPicture.getText().equals("")) picture = txtPicture.getText();
			Director director = BusinessFactory.createDirector(txtName.getText(), txtSurname.getText(), txtLastSurname.getText(), picture);
	
			// Finally, create the factory
			Factory factory = BusinessFactory.createFactory(txtFactoryName.getText(), txtFactoryInformation.getText(), director, txtEmail.getText(), (Integer)txtEmployees.getValue(), address);
			factory.setCompany((Company)cbCompanies.getSelectedItem());
			
			if (BusinessManager.addFactory(factory)) {
				Messages.getInstance().showInfoDialog(null, "Information", "Factory successfully added.");
				cleanInputFields();
			}
		} catch (IOException e) {
			Messages.getInstance().showErrorDialog(null, "Error", "Cannot open the picture file.");
		} catch (MandatoryFieldException e) {
			Messages.getInstance().showErrorDialog(null, "Error", e.getLocalizedMessage());
		} catch (SQLException e) {
			// TODO autogenerated block code
			e.printStackTrace();
		} catch (FactoryAlreadyExistsException e) {
			Messages.getInstance().showErrorDialog(null, "Error", txtFactoryName.getText() + " already exists.");
		}
	}
	
	private void cbCompaniesActionPerformed(ActionEvent evt) {
		if (cbCompanies.getSelectedIndex() != -1) {
			enableInputFields(true);
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

}
