package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import exceptions.CompanyAlreadyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.EmptyFieldException;
import exceptions.gl.GLSingletonNotInitializedException;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.xml.bind.JAXBException;

import model.IObserverUI;
import model.NotifyUIController;
import model.business.control.CompanyController;
import model.business.control.FactoryController;
import model.business.control.MapController;
import model.business.knowledge.Address;
import model.business.knowledge.BusinessFactory;
import model.business.knowledge.Centre;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.business.knowledge.Map;
import model.gl.GLSingleton;
import model.knowledge.Vector2f;
import model.listeners.MyAppMouseListener;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.math.RandomUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

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
/**
 * 
 */
public class JFMain extends SingleFrameApplication implements IAppCore, IObserverUI {
    private JMenuBar menuBar;
    private JTextField txtAddCompanyCompanyInformation;
    private JPanel addCompanyPanel;
    private JButton btnAddCompany;
    private JPanel configureFactoryPanel;
    private JButton btnSetCoordinates;
    private JMenuItem jMenuItem8;
    private JMenu toolsMenu;
    private JLabel lblStatusBar;
    private JButton btnAddFactory;
    private JTextField txtAddFactoryFactoryName;
    private JComboBox cbAddFactoryCompanies;
    private JLabel lblAddFactoryCompany;
    private JLabel lblAddFactoryFactory;
    private JPanel addFactoryPanel;
    private JComboBox cbConfigureFactoryFactories;
    private JLabel lblConfigureFactoryFactory;
    private JComboBox cbConfigureFactoryMaps;
    private JLabel lblConfigureFactoryMap;
    private JLabel lblAddCompanyCompanyInformation;
    private JTextField txtAddCompanyCompanyName;
    private JLabel lblAddCompanyCompanyName;
    private JLabel lblConfigureFactoryCompany;
    private JComboBox cbConfigureFactoryCompanies;
    private JScrollPane jScrollPane1;
    private JPanel statusPanel;
    private JPanel widgetPanel;
    private JPanel canvasPanel;
    private JPanel topPanel;
    private JMenuItem jMenuItem7;
    private JMenuItem jMenuItem6;
    private JMenuItem jMenuItem5;
    private JMenuItem jMenuItem4;
    private JMenu editMenu;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem1;
    private JMenu fileMenu;
    private JSeparator jSeparator;
    private JButton saveButton;
    private JButton openButton;
    private JButton newButton;
    private JToolBar toolBar;
    private JPanel toolBarPanel;
    private JPanel contentPanel;
	private boolean settingCoordinates;

    @Action
    public void open() {
    }

    @Action
    public void save() {
    }

    @Action
    public void newFile() {
    }

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
    	{
	    	getMainFrame().setSize(791, 486);
    	}
        {
            topPanel = new JPanel();
            BorderLayout panelLayout = new BorderLayout();
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(659, 404));
            {
                contentPanel = new JPanel();
                AnchorLayout contentPanelLayout = new AnchorLayout();
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, BorderLayout.CENTER);
                {
                	jScrollPane1 = new JScrollPane();
                	contentPanel.add(jScrollPane1, new AnchorConstraint(0, 0, 0, 290, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
                	jScrollPane1.setPreferredSize(new java.awt.Dimension(485, 383));
                	{
                		canvasPanel = new JPanel();
                		jScrollPane1.setViewportView(canvasPanel);
                		AnchorLayout canvasPanelLayout = new AnchorLayout();
                		canvasPanel.setLayout(canvasPanelLayout);
                		canvasPanel.setPreferredSize(new java.awt.Dimension(349, 276));
                		canvasPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                	}
                }
                {
                	widgetPanel = new JPanel();
                	AnchorLayout widgetPanelLayout = new AnchorLayout();
                	contentPanel.add(widgetPanel, new AnchorConstraint(1, 235, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                	widgetPanel.setLayout(widgetPanelLayout);
                	widgetPanel.setPreferredSize(new java.awt.Dimension(190, 320));
                	{
                		addFactoryPanel = new JPanel();
                		widgetPanel.add(addFactoryPanel, new AnchorConstraint(108, 950, 567, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                		addFactoryPanel.setLayout(null);
                		addFactoryPanel.setPreferredSize(new java.awt.Dimension(168, 97));
                		addFactoryPanel.setBorder(BorderFactory.createTitledBorder("Add new factory"));
                		{
                			lblAddFactoryFactory = new JLabel();
                			addFactoryPanel.add(lblAddFactoryFactory);
                			lblAddFactoryFactory.setBounds(6, 44, 59, 19);
                			lblAddFactoryFactory.setName("lblAddFactoryFactory");
                		}
                		{
                			lblAddFactoryCompany = new JLabel();
                			addFactoryPanel.add(lblAddFactoryCompany);
                			lblAddFactoryCompany.setBounds(6, 23, 62, 15);
                			lblAddFactoryCompany.setName("lblAddFactoryCompany");
                		}
                		{
                			ComboBoxModel cbAddFactoryCompaniesModel = new DefaultComboBoxModel();
                			cbAddFactoryCompanies = new JComboBox();
                			addFactoryPanel.add(cbAddFactoryCompanies);
                			cbAddFactoryCompanies.setModel(cbAddFactoryCompaniesModel);
                			cbAddFactoryCompanies.setBounds(78, 20, 84, 20);
                		}
                		{
                			txtAddFactoryFactoryName = new JTextField();
                			addFactoryPanel.add(txtAddFactoryFactoryName);
                			txtAddFactoryFactoryName.setBounds(78, 42, 82, 26);
                			txtAddFactoryFactoryName.setSize(82, 20);
                		}
                		{
                			btnAddFactory = new JButton();
                			addFactoryPanel.add(btnAddFactory);
                			btnAddFactory.setBounds(35, 68, 103, 23);
                			btnAddFactory.setName("btnAddFactory");
                			btnAddFactory.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					btnAddFactoryActionPerformed(evt);
                				}
                			});
                		}
                	}
                	{
                		configureFactoryPanel = new JPanel();
                		widgetPanel.add(configureFactoryPanel, new AnchorConstraint(216, 965, 585, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                		configureFactoryPanel.setPreferredSize(new java.awt.Dimension(173, 132));
                		configureFactoryPanel.setBorder(BorderFactory.createTitledBorder("Configure factory"));
                		configureFactoryPanel.setLayout(null);
                		{
                			lblConfigureFactoryCompany = new JLabel();
                			configureFactoryPanel.add(lblConfigureFactoryCompany);
                			lblConfigureFactoryCompany.setName("lblConfigureFactoryCompany");
                			lblConfigureFactoryCompany.setBounds(6, 28, 61, 14);
                		}
                		{
                			ComboBoxModel cbCompaniesModel = new DefaultComboBoxModel();
                			cbConfigureFactoryCompanies = new JComboBox();
                			configureFactoryPanel.add(cbConfigureFactoryCompanies);
                			cbConfigureFactoryCompanies.setModel(cbCompaniesModel);
                			cbConfigureFactoryCompanies.setBounds(77, 25, 82, 20);
                			cbConfigureFactoryCompanies.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					cbConfigureFactoryCompaniesActionPerformed(evt);
                				}
                			});
                		}
                		{
                			lblConfigureFactoryMap = new JLabel();
                			configureFactoryPanel.add(lblConfigureFactoryMap);
                			lblConfigureFactoryMap.setName("lblConfigureFactoryMap");
                			lblConfigureFactoryMap.setBounds(6, 77, 62, 12);
                		}
                		{
                			ComboBoxModel cbMapsModel = new DefaultComboBoxModel();
                			cbConfigureFactoryMaps = new JComboBox();
                			configureFactoryPanel.add(cbConfigureFactoryMaps);
                			cbConfigureFactoryMaps.setModel(cbMapsModel);
                			cbConfigureFactoryMaps.setBounds(78, 75, 80, 20);
                			cbConfigureFactoryMaps.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					cbConfigureFactoryMapsActionPerformed(evt);
                				}
                			});
                		}
                		{
                			btnSetCoordinates = new JButton();
                			configureFactoryPanel.add(btnSetCoordinates);
                			btnSetCoordinates.setName("btnSetCoordinates");
                			btnSetCoordinates.setBounds(31, 104, 107, 21);
                			btnSetCoordinates.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					btnSetCoordinatesActionPerformed(evt);
                				}
                			});
                		}
                		{
                			lblConfigureFactoryFactory = new JLabel();
                			configureFactoryPanel.add(lblConfigureFactoryFactory);
                			lblConfigureFactoryFactory.setBounds(6, 52, 41, 14);
                			lblConfigureFactoryFactory.setName("lblConfigureFactoryFactory");
                		}
                		{
                			ComboBoxModel cbConfigureFactoryFactoriesModel = new DefaultComboBoxModel();
                			cbConfigureFactoryFactories = new JComboBox();
                			configureFactoryPanel.add(cbConfigureFactoryFactories);
                			cbConfigureFactoryFactories.setModel(cbConfigureFactoryFactoriesModel);
                			cbConfigureFactoryFactories.setBounds(77, 49, 83, 20);
                		}
                	}
                	{
                		addCompanyPanel = new JPanel();
                		widgetPanel.add(addCompanyPanel, new AnchorConstraint(12, 950, 239, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                		addCompanyPanel.setPreferredSize(new java.awt.Dimension(168, 90));
                		addCompanyPanel.setBorder(BorderFactory.createTitledBorder("Add new company"));
                		addCompanyPanel.setLayout(null);
                		{
                			lblAddCompanyCompanyName = new JLabel();
                			addCompanyPanel.add(lblAddCompanyCompanyName);
                			lblAddCompanyCompanyName.setName("lblAddCompanyCompanyName");
                			lblAddCompanyCompanyName.setBounds(6, 20, 64, 14);
                		}
                		{
                			txtAddCompanyCompanyName = new JTextField();
                			addCompanyPanel.add(txtAddCompanyCompanyName);
                			txtAddCompanyCompanyName.setBounds(74, 17, 88, 22);
                			txtAddCompanyCompanyName.setSize(88, 20);
                			txtAddCompanyCompanyName.setName("txtAddCompanyCompanyName");
                		}
                		{
                			lblAddCompanyCompanyInformation = new JLabel();
                			addCompanyPanel.add(lblAddCompanyCompanyInformation);
                			lblAddCompanyCompanyInformation.setName("lblAddCompanyCompanyInformation");
                			lblAddCompanyCompanyInformation.setBounds(6, 40, 64, 14);
                		}
                		{
                			txtAddCompanyCompanyInformation = new JTextField();
                			addCompanyPanel.add(txtAddCompanyCompanyInformation);
                			txtAddCompanyCompanyInformation.setName("txtAddCompanyCompanyInformation");
                			txtAddCompanyCompanyInformation.setBounds(74, 37, 88, 20);
                		}
                		{
                			btnAddCompany = new JButton();
                			addCompanyPanel.add(btnAddCompany);
                			btnAddCompany.setName("btnAddCompany");
                			btnAddCompany.setBounds(22, 63, 123, 21);
                			btnAddCompany.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					btnAddCompanyActionPerformed(evt);
                				}
                			});
                		}
                	}
                }
            }
            {
                toolBarPanel = new JPanel();
                topPanel.add(toolBarPanel, BorderLayout.NORTH);
                BorderLayout jPanel1Layout = new BorderLayout();
                toolBarPanel.setLayout(jPanel1Layout);
                {
                    toolBar = new JToolBar();
                    toolBarPanel.add(toolBar, BorderLayout.CENTER);
                    {
                        newButton = new JButton();
                        toolBar.add(newButton);
                        newButton.setAction(getAppActionMap().get("newFile"));
                        newButton.setName("newButton");
                        newButton.setFocusable(false);
                    }
                    {
                        openButton = new JButton();
                        toolBar.add(openButton);
                        openButton.setAction(getAppActionMap().get("open"));
                        openButton.setName("openButton");
                        openButton.setFocusable(false);
                    }
                    {
                        saveButton = new JButton();
                        toolBar.add(saveButton);
                        saveButton.setAction(getAppActionMap().get("save"));
                        saveButton.setName("saveButton");
                        saveButton.setFocusable(false);
                    }
                }
                {
                    jSeparator = new JSeparator();
                    toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
                }
            }
            {
            	statusPanel = new JPanel();
            	BorderLayout statusPanelLayout = new BorderLayout();
            	statusPanel.setLayout(statusPanelLayout);
            	topPanel.add(statusPanel, BorderLayout.SOUTH);
            	statusPanel.setPreferredSize(new java.awt.Dimension(775, 17));
            	{
            		lblStatusBar = new JLabel();
            		statusPanel.add(lblStatusBar, BorderLayout.CENTER);
            		lblStatusBar.setPreferredSize(new java.awt.Dimension(775, 23));
            	}
            }
        }
        menuBar = new JMenuBar();
        {
            fileMenu = new JMenu();
            menuBar.add(fileMenu);
            fileMenu.setName("fileMenu");
        {
                jMenuItem1 = new JMenuItem();
                fileMenu.add(jMenuItem1);
                jMenuItem1.setAction(getAppActionMap().get("newFile"));
            }
            {
                jMenuItem2 = new JMenuItem();
                fileMenu.add(jMenuItem2);
                jMenuItem2.setAction(getAppActionMap().get("open"));
            }
            {
                jMenuItem3 = new JMenuItem();
                fileMenu.add(jMenuItem3);
                jMenuItem3.setAction(getAppActionMap().get("save"));
            }
        }
        {
            editMenu = new JMenu();
            menuBar.add(editMenu);
            editMenu.setName("editMenu");
        {
                jMenuItem4 = new JMenuItem();
                editMenu.add(jMenuItem4);
                jMenuItem4.setAction(getAppActionMap().get("copy"));
            }
            {
                jMenuItem5 = new JMenuItem();
                editMenu.add(jMenuItem5);
                jMenuItem5.setAction(getAppActionMap().get("cut"));
            }
            {
                jMenuItem6 = new JMenuItem();
                editMenu.add(jMenuItem6);
                jMenuItem6.setAction(getAppActionMap().get("paste"));
            }
            {
                jMenuItem7 = new JMenuItem();
                editMenu.add(jMenuItem7);
                jMenuItem7.setAction(getAppActionMap().get("delete"));
            }
        }
        {
        	toolsMenu = new JMenu();
        	menuBar.add(toolsMenu);
        	toolsMenu.setName("toolsMenu");
        	{
        		jMenuItem8 = new JMenuItem();
        		toolsMenu.add(jMenuItem8);
        		jMenuItem8.setName("jMenuItem8");
        		jMenuItem8.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent evt) {
        				jMenuItem8ActionPerformed(evt);
        			}
        		});
        	}
        }
        getMainFrame().setJMenuBar(menuBar);
        show(topPanel);
        NotifyUIController.attach(this);
        GLInit.init();
        GLInit.setContext(getMainFrame(), canvasPanel, new AnchorConstraint(5, 998, 998, 4, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
        settingCoordinates = false;
        GLInit.getGLCanvas().addMouseListener(new MyAppMouseListener(this, GLInit.getGLCanvas()));
        this.updateCompanyList();
        this.updateMapList();
    }

    public static void main(String[] args) {
        launch(JFMain.class, args);
    }
    
    private void btnAddCompanyActionPerformed(ActionEvent evt) {
    	System.out.println("btnAddCompany.actionPerformed, event="+evt);
    	try {
			CompanyController.addCompany(BusinessFactory.createCompany(txtAddCompanyCompanyName.getText(), txtAddCompanyCompanyInformation.getText()));
			Messages.showInfoDialog(getMainFrame(), "Information", "Company successfully added.");
			txtAddCompanyCompanyName.setText("");
			txtAddCompanyCompanyInformation.setText("");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyAlreadyExistsException e) {
			Messages.showErrorDialog(getMainFrame(), "Error", "This company already exists.");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyFieldException e) {
			Messages.showErrorDialog(getMainFrame(), "Error", "The company name field must be filled in.");
		}
    }
    
	private void btnAddFactoryActionPerformed(ActionEvent evt) {
		try {
			String name = txtAddFactoryFactoryName.getText();
			String information = txtAddFactoryFactoryName.getText() + " information";
			String director = txtAddFactoryFactoryName.getText() + " director";
			String email = txtAddFactoryFactoryName.getText() + "@email.com";
			int employees = RandomUtils.nextInt();
			Address address = BusinessFactory.createAddress(txtAddFactoryFactoryName.getText() + " street",
					txtAddFactoryFactoryName.getText() + " city",
					txtAddFactoryFactoryName.getText() + " state",
					txtAddFactoryFactoryName.getText() + " country",
					txtAddFactoryFactoryName.getText() + " zip");
			if (cbAddFactoryCompanies.getSelectedIndex() != -1){
				FactoryController.addFactory(((Company)cbAddFactoryCompanies.getSelectedItem()).getId(), BusinessFactory.createFactory(name, information, director, email, employees, address));
				Messages.showInfoDialog(getMainFrame(), "Information", "Factory successfully added.");
				txtAddFactoryFactoryName.setText("");
			}
			else {
				Messages.showErrorDialog(getMainFrame(), "Error", "You have to choose the company in which the factory will be added.");
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyFieldException e) {
			Messages.showErrorDialog(getMainFrame(), "Error", "The factory name field must be filled in.");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateCompanyList() {
		try {
			// Clean already added companies
			cbAddFactoryCompanies.setModel(new DefaultComboBoxModel());
			cbConfigureFactoryCompanies.setModel(new DefaultComboBoxModel());
			ArrayList<Company> companies = (ArrayList)CompanyController.getAllCompanies();
			for (Company c : companies) {
				cbAddFactoryCompanies.addItem(c);
				cbConfigureFactoryCompanies.addItem(c);
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateFactoryList(int companyId) {
		try {
			if (((Company)cbConfigureFactoryCompanies.getSelectedItem()).getId() == companyId) {
				// Clean already added factories
				cbConfigureFactoryFactories.setModel(new DefaultComboBoxModel());
				// Add the updated company factories				
				Company c = CompanyController.getCompany(companyId);
				for (Factory f : c.getFactories()) {
					cbConfigureFactoryFactories.addItem(f);
				}
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateMapList() {
		try {
			ArrayList<Map> maps = (ArrayList)MapController.getAllMaps();
			for (Map m : maps) {
				cbConfigureFactoryMaps.addItem(m);
			}
			cbConfigureFactoryMaps.setSelectedIndex(-1);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateClickedWorldCoords(Vector2f coordinates) {
		if (isSettingCoordinates()) {
			try {
				FactoryController.addFactoryLocation(((Company)cbConfigureFactoryCompanies.getSelectedItem()).getId(), ((Factory)cbConfigureFactoryFactories.getSelectedItem()).getId(), ((Map)cbConfigureFactoryMaps.getSelectedItem()).getId(), coordinates);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CompanyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			settingCoordinates = false;
			lblStatusBar.setText("");
			GLInit.getGLCanvas().setCursor(Cursor.getDefaultCursor());
		}
	}
	
	@Override
	public void selectCentre(Centre c) {
		// TODO patron observador. se llama al seleccionar una localizacion de uan factoria
		try {
			Company company = CompanyController.getCompany(c.getIdCompany());
			Factory factory = FactoryController.getFactory(company, c.getIdFactory());
			System.err.print(company.toString() + factory.toString());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isSettingCoordinates () {
		return this.settingCoordinates;
	}
	
	public void settingCoordinates (boolean b) {
		this.settingCoordinates = b;
	}
	
	private void btnSetCoordinatesActionPerformed(ActionEvent evt) {
		System.out.println("btnSetCoordinates.actionPerformed, event="+evt);
		if (cbConfigureFactoryCompanies.getSelectedItem() != null
				&& cbConfigureFactoryFactories.getSelectedItem() != null
				&& cbConfigureFactoryMaps.getSelectedItem() != null) {
			this.settingCoordinates = true;
			lblStatusBar.setText("Click the location of the factory on the map...");
		} else {
			Messages.showWarningDialog(getMainFrame(), "Warning", "You have to select a factory and a map to set its coordinates.");
		}
	}
	
	private void cbConfigureFactoryCompaniesActionPerformed(ActionEvent evt) {
		// Clean already added factories
		ComboBoxModel cbConfigureFactoryFactoriesModel = new DefaultComboBoxModel();
		cbConfigureFactoryFactories.setModel(cbConfigureFactoryFactoriesModel);
		// Add the selected company factories
		Company selectedCompany = (Company)cbConfigureFactoryCompanies.getSelectedItem();
		for (Factory fact : selectedCompany.getFactories()) {
			cbConfigureFactoryFactories.addItem(fact);
		}
	}
	
	private void cbConfigureFactoryMapsActionPerformed(ActionEvent evt) {
		try {
			if (GLSingleton.isInitiated()) {
				MapController.setActiveMap((Map)cbConfigureFactoryMaps.getSelectedItem());
				List<Factory> factories = FactoryController.getFactories(((Company)cbConfigureFactoryCompanies.getSelectedItem()).getId());
				List<Centre> centres =  new ArrayList<Centre>();
				List<Vector2f> locations = new ArrayList<Vector2f>();
				for (Factory fact : factories) {
					centres.add(new Centre(((Company)cbConfigureFactoryCompanies.getSelectedItem()).getId(), fact.getId()));
					int selectedMapId = ((Map)cbConfigureFactoryMaps.getSelectedItem()).getId();
					locations.add(fact.getLocations().get((Integer)selectedMapId));
				}
				MapController.setMapLocations(centres, locations);
			}
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void jMenuItem8ActionPerformed(ActionEvent evt) {
		JFConfiguration jfc = new JFConfiguration();
		jfc.setLocationRelativeTo(getMainFrame());
		jfc.setVisible(true);
	}

}
