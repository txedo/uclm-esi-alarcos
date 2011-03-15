package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.CompanyNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.FactoryNotFoundException;
import exceptions.LocationAlreadyExistsException;
import exceptions.LocationNotFoundException;
import exceptions.MapNotFoundException;
import exceptions.gl.GLSingletonNotInitializedException;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
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
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import model.IObserverUI;
import model.NotifyUIManager;
import model.business.control.BusinessManager;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.business.knowledge.Map;
import model.business.knowledge.Project;
import model.gl.GLSingleton;
import model.knowledge.Vector2f;
import model.listeners.MyAppMouseListener;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import presentation.utils.FactoryJComboBox;
import presentation.utils.MapsJComboBox;
import presentation.utils.Messages;
import presentation.utils.ProjectJComboBox;



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
    private JPanel configureFactoryPanel;
    private JButton btnSetCoordinates;
    private JPanel infoPanel;
    private MapsJComboBox cbMaps;
    private JLabel lblAvailableMaps;
    private JPanel activeMapPanel;
    private FactoryJComboBox cbInvolvedFactories;
    private ProjectJComboBox cbProjects;
    private JLabel lblFactories;
    private JLabel lblProject;
    private JPanel projectPanel;
    private JMenuItem jMenuItem8;
    private JMenu toolsMenu;
    private JLabel lblStatusBar;
    private JComboBox cbConfigureFactoryFactories;
    private JLabel lblConfigureFactoryFactory;
    private MapsJComboBox cbConfigureFactoryMaps;
    private JLabel lblConfigureFactoryMap;
    private JLabel lblConfigureFactoryCompany;
    private JComboBox cbConfigureFactoryCompanies;
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
	protected void ready() {
        getMainFrame().setJMenuBar(menuBar);
        show(topPanel);
        NotifyUIManager.attach(this);
		GLInit.init();
        GLInit.setContext(getMainFrame(), canvasPanel, new AnchorConstraint(5, 998, 998, 4, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
        settingCoordinates = false;
        GLInit.getGLCanvas().addMouseListener(new MyAppMouseListener(this, GLInit.getGLCanvas()));
        try {
        	cbMaps.reload();
			cbProjects.reload();
			cbConfigureFactoryMaps.reload();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.updateCompanyList();
        this.updateMapList();
	}

	@Override
    protected void startup() {
    	{
	    	getMainFrame().setSize(1121, 613);
    	}
        {
            topPanel = new JPanel();
            AnchorLayout panelLayout = new AnchorLayout();
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(659, 404));
            {
                contentPanel = new JPanel();
                FormLayout contentPanelLayout = new FormLayout(
                		"max(p;5dlu), max(p;5dlu), max(p;5dlu)", 
                		"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)");
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, new AnchorConstraint(27, 0, 17, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
                contentPanel.setPreferredSize(new java.awt.Dimension(775, 383));
                {
                	canvasPanel = new JPanel();
                	contentPanel.add(canvasPanel, new CellConstraints("3, 1, 1, 1, fill, fill"));
                	AnchorLayout canvasPanelLayout = new AnchorLayout();
                	canvasPanel.setLayout(canvasPanelLayout);
                	canvasPanel.setPreferredSize(new java.awt.Dimension(671, 500));
                	canvasPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                	canvasPanel.setSize(671, 500);
                }
                {
                	widgetPanel = new JPanel();
                	FormLayout widgetPanelLayout = new FormLayout(
                			"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)", 
                			"46dlu, 5dlu, 79dlu, 5dlu, max(p;5dlu)");
                	contentPanel.add(widgetPanel, new CellConstraints("2, 1, 1, 1, fill, fill"));
                	widgetPanel.setLayout(widgetPanelLayout);
                	widgetPanel.setPreferredSize(new java.awt.Dimension(191, 381));
                	{
                		configureFactoryPanel = new JPanel();
                		widgetPanel.add(configureFactoryPanel, new CellConstraints("2, 5, 1, 1, default, default"));
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
                			cbConfigureFactoryMaps = new MapsJComboBox();
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
                		projectPanel = new JPanel();
                		FormLayout projectPanelLayout = new FormLayout(
                				"max(p;5dlu), 75dlu", 
                				"13dlu, 13dlu, max(p;15dlu), max(p;15dlu)");
                		projectPanel.setLayout(projectPanelLayout);
                		widgetPanel.add(projectPanel, new CellConstraints("2, 3, 1, 2, default, default"));
                		projectPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""), "Project view", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
                		{
                			lblProject = new JLabel();
                			projectPanel.add(lblProject, new CellConstraints("2, 1, 1, 1, default, default"));
                			lblProject.setName("lblProject");
                		}
                		{
                			lblFactories = new JLabel();
                			projectPanel.add(lblFactories, new CellConstraints("2, 3, 1, 1, default, default"));
                			lblFactories.setName("lblFactories");
                		}
                		{
                			cbProjects = new ProjectJComboBox();
                			projectPanel.add(cbProjects, new CellConstraints("2, 2, 1, 1, default, default"));
                			cbProjects.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					cbProjectsActionPerformed(evt);
                				}
                			});
                		}
                		{
                			cbInvolvedFactories = new FactoryJComboBox();
                			projectPanel.add(cbInvolvedFactories, new CellConstraints("2, 4, 1, 1, default, default"));
                		}
                	}
                	{
                		activeMapPanel = new JPanel();
                		FormLayout activeMapPanelLayout = new FormLayout(
                				"max(p;5dlu), 66dlu", 
                				"13dlu, 13dlu");
                		activeMapPanel.setLayout(activeMapPanelLayout);
                		widgetPanel.add(activeMapPanel, new CellConstraints("2, 1, 1, 2, default, default"));
                		activeMapPanel.setBorder(BorderFactory.createTitledBorder("Choose a map:"));
                		{
                			lblAvailableMaps = new JLabel();
                			activeMapPanel.add(lblAvailableMaps, new CellConstraints("2, 1, 1, 1, default, default"));
                			lblAvailableMaps.setName("lblAvailableMaps");
                		}
                		{
                			cbMaps = new MapsJComboBox();
                			activeMapPanel.add(cbMaps, new CellConstraints("2, 2, 1, 1, default, default"));
                			cbMaps.addActionListener(new ActionListener() {
                				public void actionPerformed(ActionEvent evt) {
                					cbMapsActionPerformed(evt);
                				}
                			});
                		}
                	}
                }
                {
                	infoPanel = new JPanel();
                	contentPanel.add(infoPanel, new CellConstraints("3, 1, 1, 1, default, default"));
                	infoPanel.setPreferredSize(new java.awt.Dimension(245, 335));
                }
            }
            {
                toolBarPanel = new JPanel();
                topPanel.add(toolBarPanel, new AnchorConstraint(1, 1000, 64, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                BorderLayout jPanel1Layout = new BorderLayout();
                toolBarPanel.setLayout(jPanel1Layout);
                toolBarPanel.setPreferredSize(new java.awt.Dimension(775, 27));
                {
                    toolBar = new JToolBar();
                    toolBarPanel.add(toolBar, BorderLayout.NORTH);
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
            	topPanel.add(statusPanel, new AnchorConstraint(961, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
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
    }
    
	public void updateCompanyList() {
		try {
			// Clean already added factories
			cbConfigureFactoryCompanies.setModel(new DefaultComboBoxModel());
			// Add the updated company factories	
			for (Company c : BusinessManager.getAllCompanies()) {
				cbConfigureFactoryCompanies.addItem(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateFactoryList(int companyId) {
		try {
			if (((Company)cbConfigureFactoryCompanies.getSelectedItem()).getId() == companyId) {
				// Clean already added factories
				cbConfigureFactoryFactories.setModel(new DefaultComboBoxModel());
				// Add the updated company factories				
				Company c = BusinessManager.getCompany(companyId);
				for (Factory f : BusinessManager.getFactories(c)) {
					cbConfigureFactoryFactories.addItem(f);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateMapList() {
		try {
			cbConfigureFactoryMaps.reload();
			cbMaps.reload();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateClickedWorldCoords(Vector2f coordinates) {
		if (isSettingCoordinates()) {
			try {
				if (BusinessManager.addLocation(((Factory)cbConfigureFactoryFactories.getSelectedItem()), ((Map)cbConfigureFactoryMaps.getSelectedItem()), coordinates)) {
					List<Factory> factories = BusinessManager.getFactories(((Company)cbConfigureFactoryCompanies.getSelectedItem()));
					BusinessManager.setMapLocations (factories);
				}
			} catch (LocationAlreadyExistsException e) {
				Messages.showErrorDialog(getMainFrame(), "Error", "This location is already configured.");
			} catch (MandatoryFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FactoryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MapNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LocationNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			settingCoordinates = false;
			lblStatusBar.setText("");
			GLInit.getGLCanvas().setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public void updateProjectList() {
		// TODO Auto-generated method stub
		
	}
	
	public void selectFactoryByLocation(int idLocation) {
		// TODO patron observador. Se llama al seleccionar una localizacion de una factoria
		try {
			Factory factory = BusinessManager.getLocation(idLocation).getFactory();
			System.err.print(factory.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectProject(int id) {
		// TODO Auto-generated method stub
		
	}

	public void selectFactory(int id) {
		// TODO Auto-generated method stub
		
	}

	public void selectTower(int id) {
		// TODO Auto-generated method stub
		
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
		try {
			for (Factory fact : BusinessManager.getFactories(selectedCompany)) {
				cbConfigureFactoryFactories.addItem(fact);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cbConfigureFactoryMapsActionPerformed(ActionEvent evt) {
		try {
			if (GLSingleton.isInitiated()) {
				BusinessManager.setActiveMap((Map)cbConfigureFactoryMaps.getSelectedItem());
				List<Factory> factories = BusinessManager.getFactories(((Company)cbConfigureFactoryCompanies.getSelectedItem()));
				BusinessManager.setMapLocations (factories);
			}
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void jMenuItem8ActionPerformed(ActionEvent evt) {
		JFConfiguration jfc = new JFConfiguration();
		jfc.setLocationRelativeTo(getMainFrame());
		jfc.setVisible(true);
	}
	
	private void cbProjectsActionPerformed(ActionEvent evt) {

		try {
			if (GLSingleton.isInitiated()) {
				List<Factory> factories = new ArrayList<Factory>(((Project)cbProjects.getSelectedItem()).getInvolvedFactories());
				cbInvolvedFactories.load(factories);
				BusinessManager.highlightMapLocations(factories);
			}
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cbMapsActionPerformed(ActionEvent evt) {
		try {
			if (GLSingleton.isInitiated()) {
				BusinessManager.setActiveMap((Map)cbMaps.getSelectedItem());
				List<Factory> factories = BusinessManager.getAllFactories();
				BusinessManager.setMapLocations(factories);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
