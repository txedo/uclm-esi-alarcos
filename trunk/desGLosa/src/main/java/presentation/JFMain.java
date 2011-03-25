package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.MandatoryFieldException;
import exceptions.FactoryNotFoundException;
import exceptions.LocationAlreadyExistsException;
import exceptions.LocationNotFoundException;
import exceptions.MapNotFoundException;
import exceptions.NoActiveMapException;
import exceptions.ProjectNotFoundException;
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
import javax.swing.JButton;
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
	private JFConfiguration configurationFrame;

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
	    	getMainFrame().setSize(1121, 632);
    	}
        {
            topPanel = new JPanel();
            FormLayout panelLayout = new FormLayout(
            		"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)", 
            		"max(p;5dlu), max(p;5dlu), max(p;5dlu)");
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(659, 404));
            {
                contentPanel = new JPanel();
                FormLayout contentPanelLayout = new FormLayout(
                		"max(p;5dlu), max(p;5dlu), max(p;5dlu), 5dlu, 138dlu", 
                		"max(p;5dlu)");
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, new CellConstraints("1, 2, 1, 1, fill, fill"));
                contentPanel.setPreferredSize(new java.awt.Dimension(1105, 522));
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
                			"46dlu, 5dlu, 79dlu, 5dlu");
                	contentPanel.add(widgetPanel, new CellConstraints("2, 1, 1, 1, fill, fill"));
                	widgetPanel.setLayout(widgetPanelLayout);
                	widgetPanel.setPreferredSize(new java.awt.Dimension(191, 381));
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
                	FormLayout infoPanelLayout = new FormLayout(
                			"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)", 
                			"max(p;5dlu)");
                	infoPanel.setLayout(infoPanelLayout);
                	contentPanel.add(infoPanel, new CellConstraints("5, 1, 1, 1, fill, fill"));
                }
            }
            {
                toolBarPanel = new JPanel();
                topPanel.add(toolBarPanel, new CellConstraints("1, 1, 1, 1, default, default"));
                BorderLayout jPanel1Layout = new BorderLayout();
                toolBarPanel.setLayout(jPanel1Layout);
                toolBarPanel.setPreferredSize(new java.awt.Dimension(1105, 30));
                {
                    toolBar = new JToolBar();
                    toolBarPanel.add(toolBar, BorderLayout.NORTH);
                    toolBar.setPreferredSize(new java.awt.Dimension(1105, 26));
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
            	topPanel.add(statusPanel, new CellConstraints("1, 3, 1, 1, default, default"));
            	statusPanel.setPreferredSize(new java.awt.Dimension(775, 17));
            	{
            		lblStatusBar = new JLabel();
            		statusPanel.add(lblStatusBar, BorderLayout.CENTER);
            		lblStatusBar.setPreferredSize(new java.awt.Dimension(1105, 28));
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
		// TODO Auto-generated catch block
	}
	
	public void updateFactoryList(int companyId) {
		// TODO Auto-generated catch block
	}

	public void updateMapList() {
		try {
			cbMaps.reload();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateProjectList() {
		try {
			cbProjects.reload();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateClickedWorldCoords(Vector2f coordinates) {
		if (isSettingCoordinates()) {
			settingCoordinates = false;
			lblStatusBar.setText("");
			GLInit.getGLCanvas().setCursor(Cursor.getDefaultCursor());
			WindowNotifier.executeOperation(WindowNotifierOperationCodes.LocationCreated, coordinates);
		}
	}
	
	public void selectFactoryByLocation(int idLocation) {
		// TODO patron observador. Se llama al seleccionar una localizacion de una factoria
		try {
			Factory factory = BusinessManager.getLocation(idLocation).getFactory();
			this.selectFactory(factory.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectProject(int id) {
//		Project project = BusinessManager.getProject(id);
//		JPInfoProject jpip = new JPInfoProject(project);
//		infoPanel.removeAll();
//		infoPanel.add(jpip, new CellConstraints("1, 1, 1, 1, fill, fill"));
//		jpip.setVisible(true);
//		infoPanel.validate();
		// TODO Auto-generated catch block
		System.err.println("Project funcionality has not been implemented yet.");
	}

	public void selectFactory(int id) {
		try {
			Factory factory = BusinessManager.getFactory(id);
			JPInfoFactory jpif = new JPInfoFactory(factory);
			infoPanel.removeAll();
			infoPanel.add(jpif, new CellConstraints("1, 1, 1, 1, fill, fill"));
			jpif.setVisible(true);
			infoPanel.validate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectTower(int id) {
		// TODO Auto-generated catch block
		System.err.println("Tower funcionality has not been implemented yet.");
	}
	
	public boolean isSettingCoordinates () {
		return this.settingCoordinates;
	}
	
	public void settingCoordinates (boolean b) {
		this.settingCoordinates = b;
	}
	
	public void setCoordinates () {
		this.settingCoordinates(true);
		lblStatusBar.setText("Click the location of the factory on the map...");
	}
	
	private void jMenuItem8ActionPerformed(ActionEvent evt) {
		configurationFrame = new JFConfiguration(this);
		configurationFrame.setLocationRelativeTo(getMainFrame());
		configurationFrame.setVisible(true);
	}
	
	private void cbProjectsActionPerformed(ActionEvent evt) {
		try {
			if (!evt.getActionCommand().equals("loadingData")) {
				if (GLSingleton.isInitiated()) {
					List<Factory> factories = new ArrayList<Factory>(((Project)cbProjects.getSelectedItem()).getInvolvedFactories());
					cbInvolvedFactories.load(factories);
					BusinessManager.highlightMapLocations(factories);
				}
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
		} catch (NoActiveMapException e) {
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
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
		} catch (NoActiveMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
