package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import exceptions.CompanyAlreadyExistsException;
import exceptions.gl.GLSingletonNotInitializedException;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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
import model.business.control.MapController;
import model.business.knowledge.BusinessFactory;
import model.business.knowledge.Company;
import model.business.knowledge.Map;
import model.gl.GLSingleton;

import org.apache.commons.configuration.ConfigurationException;
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
public class JFMain extends SingleFrameApplication implements IObserverUI {
    private JMenuBar menuBar;
    private JTextField txtCompanyInformation;
    private JPanel companyPanel;
    private JButton btnAddFactory;
    private JPanel factoryPanel;
    private JButton btnSetCoordinates;
    private JComboBox cbMaps;
    private JLabel lblMap;
    private JLabel lblCompanyInformation;
    private JTextField txtCompanyName;
    private JLabel lblCompanyName;
    private JLabel lblCompanies;
    private JComboBox cbCompanies;
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
	    	getMainFrame().setSize(675, 416);
    	}
        {
            topPanel = new JPanel();
            BorderLayout panelLayout = new BorderLayout();
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            {
                contentPanel = new JPanel();
                AnchorLayout contentPanelLayout = new AnchorLayout();
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, BorderLayout.CENTER);
                {
                	jScrollPane1 = new JScrollPane();
                	contentPanel.add(jScrollPane1, new AnchorConstraint(1, 1001, 1001, 307, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                	jScrollPane1.setPreferredSize(new java.awt.Dimension(347, 273));
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
                		factoryPanel = new JPanel();
                		widgetPanel.add(factoryPanel, new AnchorConstraint(132, 950, 585, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                		factoryPanel.setPreferredSize(new java.awt.Dimension(168, 105));
                		factoryPanel.setBorder(BorderFactory.createTitledBorder("Add new factory"));
                		{
                			lblCompanies = new JLabel();
                			factoryPanel.add(lblCompanies);
                			lblCompanies.setName("lblCompanies");
                			lblCompanies.setPreferredSize(new java.awt.Dimension(84, 14));
                		}
                		{
                			ComboBoxModel cbCompaniesModel = new DefaultComboBoxModel();
                			cbCompanies = new JComboBox();
                			factoryPanel.add(cbCompanies);
                			cbCompanies.setModel(cbCompaniesModel);
                			cbCompanies.setPreferredSize(new java.awt.Dimension(55, 20));
                		}
                		{
                			lblMap = new JLabel();
                			factoryPanel.add(lblMap);
                			lblMap.setName("lblMap");
                		}
                		{
                			ComboBoxModel cbMapsModel = new DefaultComboBoxModel();
                			cbMaps = new JComboBox();
                			factoryPanel.add(cbMaps);
                			cbMaps.setModel(cbMapsModel);
                			cbMaps.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					cbMapsActionPerformed(evt);
                				}
                			});
                		}
                		{
                			btnSetCoordinates = new JButton();
                			factoryPanel.add(btnSetCoordinates);
                			btnSetCoordinates.setName("btnSetCoordinates");
                		}
                	}
                	{
                		companyPanel = new JPanel();
                		widgetPanel.add(companyPanel, new AnchorConstraint(12, 950, 239, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                		companyPanel.setPreferredSize(new java.awt.Dimension(168, 109));
                		companyPanel.setBorder(BorderFactory.createTitledBorder("Add new company"));
                		{
                			lblCompanyName = new JLabel();
                			companyPanel.add(lblCompanyName);
                			lblCompanyName.setName("lblCompanyName");
                		}
                		{
                			txtCompanyName = new JTextField();
                			companyPanel.add(txtCompanyName);
                			txtCompanyName.setPreferredSize(new java.awt.Dimension(65, 20));
                		}
                		{
                			lblCompanyInformation = new JLabel();
                			companyPanel.add(lblCompanyInformation);
                			lblCompanyInformation.setName("lblCompanyInformation");
                		}
                		{
                			txtCompanyInformation = new JTextField();
                			companyPanel.add(txtCompanyInformation);
                			txtCompanyInformation.setName("txtCompanyInformation");
                			txtCompanyInformation.setPreferredSize(new java.awt.Dimension(81, 20));
                		}
                		{
                			btnAddFactory = new JButton();
                			companyPanel.add(btnAddFactory);
                			btnAddFactory.setPreferredSize(new java.awt.Dimension(59, 21));
                			btnAddFactory.setName("btnAddFactory");
                			btnAddFactory.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					btnAddFactoryActionPerformed(evt);
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
            	topPanel.add(statusPanel, BorderLayout.SOUTH);
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
        getMainFrame().setJMenuBar(menuBar);
        show(topPanel);
        NotifyUIController.attach(this);
        GLInit.init();
        GLInit.setContext(getMainFrame(), canvasPanel, new AnchorConstraint(5, 998, 998, 4, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
        this.updateCompanyList();
        this.updateMapList();
    }

    public static void main(String[] args) {
        launch(JFMain.class, args);
    }
    
    private void btnAddFactoryActionPerformed(ActionEvent evt) {
    	System.out.println("btnAddFactory.actionPerformed, event="+evt);
    	try {
			CompanyController.addCompany(BusinessFactory.createCompany(txtCompanyName.getText(), txtCompanyInformation.getText()));
			Messages.showInfoDialog(getMainFrame(), "Information", "Company successfully added.");
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
		}
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateCompanyList() {
		try {
			ArrayList<Company> companies = (ArrayList)CompanyController.getAllCompanies();
			for (Company c : companies) {
				cbCompanies.addItem(c);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateMapList() {
		try {
			cbMaps.addItem("");
			ArrayList<Map> maps = (ArrayList)MapController.getAllMaps();
			for (Map m : maps) {
				cbMaps.addItem(m);
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
	
	private void cbMapsActionPerformed(ActionEvent evt) {
		try {
			if (!cbMaps.getSelectedItem().equals(""))
				if (GLSingleton.isInitiated())
					MapController.setActiveMap((Map)cbMaps.getSelectedItem());
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
