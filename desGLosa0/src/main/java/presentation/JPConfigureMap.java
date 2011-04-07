package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.FactoryNotFoundException;
import exceptions.LocationAlreadyExistsException;
import exceptions.LocationNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.MapNotFoundException;
import exceptions.NoActiveMapException;
import exceptions.gl.GLSingletonNotInitializedException;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import model.business.control.BusinessManager;
import model.business.knowledge.Factory;
import model.business.knowledge.Location;
import model.business.knowledge.Map;
import model.knowledge.Vector2f;

import org.jdesktop.application.Application;

import presentation.utils.FactoryJTree;
import presentation.utils.MapsJList;
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
public class JPConfigureMap extends javax.swing.JPanel implements JPConfigureInterface, WindowNotifierObserverInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965274680612432098L;
	private MapsJList mapList;
	private JLabel lblMaps;
	private JScrollPane jScrollPane1;
	private JButton btnAdd;
	private JTable jTableLocation;
	private JLabel lblFactories;
	private JScrollPane jScrollPane2;
	private JButton btnRemove;
	private JLabel lblStatusBar;
	private JLabel lblCoordinates;
	private JPanel jPanelTable;
	private JTree factoryTree;
	private final String [] columnNames = {"X coord." , "Y coord."};
	private final DefaultTableModel emptyLocationTableModel = new DefaultTableModel(
			new String [][] { {"", ""} },
			columnNames);
	private final String defaultStatusBarText = "You must select a factory and a map.";
	private IAppCore applicationCore;
	private final boolean configurationFrameNeeded = true;
	private boolean isFactorySelected;
	private boolean isMapSelected;
	private Factory selectedFactory;
	
	public JPConfigureMap() {
		super();
		initGUI();
		this.isFactorySelected = false;
		this.isMapSelected = false;
		this.selectedFactory = null;
		try {
			mapList.load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jTableLocation.setFocusable(false);
		jTableLocation.setShowGrid(true);
		jTableLocation.setEnabled(false);
		jPanelTable.add(jTableLocation.getTableHeader(), BorderLayout.PAGE_START);
		this.handleLocation();
	}
	
	public void setApplicationCore (IAppCore applicationCore) {
		this.applicationCore = applicationCore;
	}
	
	public boolean isApplicationCoreNeeded () {
		return this.configurationFrameNeeded;
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 112dlu, max(p;5dlu), 15dlu, 5dlu, 15dlu, p:grow, 5dlu", 
					"5dlu, 13dlu, 12dlu, 37dlu, 5dlu, 12dlu, 5dlu, 10dlu, 15dlu, 5dlu, 13dlu, 12dlu, 15dlu, 5dlu, max(p;15dlu)");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(415, 433));
			{
				jScrollPane2 = new JScrollPane();
				this.add(jScrollPane2, new CellConstraints("2, 3, 1, 7, fill, fill"));
				{
					mapList = new  MapsJList();
					jScrollPane2.setViewportView(mapList);
					mapList.addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent evt) {
							mapListValueChanged(evt);
						}
					});
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, new CellConstraints("4, 3, 4, 7, default, default"));
				{
					factoryTree = new FactoryJTree();
					jScrollPane1.setViewportView(factoryTree);
					factoryTree.setPreferredSize(new java.awt.Dimension(168, 149));
					factoryTree.addTreeSelectionListener(new TreeSelectionListener() {
						public void valueChanged(TreeSelectionEvent evt) {
							factoryTreeValueChanged(evt);
						}
					});
				}
			}
			{
				jPanelTable = new JPanel();
				BorderLayout jPanelTableLayout = new BorderLayout();
				jPanelTable.setLayout(jPanelTableLayout);
				this.add(jPanelTable, new CellConstraints("2, 12, 1, 2, default, default"));
				{
					TableModel jTableLocationModel = emptyLocationTableModel;
					jTableLocation = new JTable();
					jPanelTable.add(jTableLocation);
					jTableLocation.setPreferredSize(new java.awt.Dimension(196, 45));
					jTableLocation.setModel(jTableLocationModel);
				}
			}
			{
				lblCoordinates = new JLabel();
				this.add(lblCoordinates, new CellConstraints("2, 11, 1, 1, default, default"));
				lblCoordinates.setName("lblCoordinates");
			}
			{
				lblStatusBar = new JLabel();
				lblStatusBar.setText(defaultStatusBarText);
				this.add(lblStatusBar, new CellConstraints("2, 15, 6, 1, fill, fill"));
				lblStatusBar.setName("lblStatusBar");
			}
			{
				btnAdd = new JButton();
				this.add(btnAdd, new CellConstraints("4, 13, 1, 1, fill, fill"));
				btnAdd.setName("btnAdd");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddActionPerformed(evt);
					}
				});
			}
			{
				btnRemove = new JButton();
				this.add(btnRemove, new CellConstraints("6, 13, 1, 1, fill, fill"));
				btnRemove.setName("btnRemove");
				btnRemove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnRemoveActionPerformed(evt);
					}
				});
			}
			{
				lblMaps = new JLabel();
				this.add(lblMaps, new CellConstraints("2, 2, 1, 1, default, default"));
				lblMaps.setName("lblMaps");
				lblMaps.setPreferredSize(new java.awt.Dimension(193, 164));
			}
			{
				lblFactories = new JLabel();
				this.add(lblFactories, new CellConstraints("4, 2, 4, 1, default, default"));
				lblFactories.setName("lblFactories");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateMapLocations () {
		try {
			BusinessManager.setMapLocations(BusinessManager.getAllFactories());
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
	
	private void highlightSelectedFactory () {
		try {
			List<Factory> highlightFactory = new ArrayList<Factory>();
			if (this.selectedFactory != null)
				highlightFactory.add(this.selectedFactory);
			BusinessManager.highlightMapLocations(highlightFactory);
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
	
	private void factoryTreeValueChanged(TreeSelectionEvent evt) {
		Object selectedObject = ((DefaultMutableTreeNode)factoryTree.getLastSelectedPathComponent()).getUserObject();
		if (selectedObject instanceof Factory) {
			this.isFactorySelected = true;
			this.selectedFactory = (Factory)((Factory)selectedObject).clone();
		} else {
			this.isFactorySelected = false;
			this.selectedFactory = null;
			this.highlightSelectedFactory();
		}
		this.handleLocation();
	}
	
	private void mapListValueChanged(ListSelectionEvent evt) {
		try {
			if (mapList.getSelectedIndex() != -1) {
				this.isMapSelected = true;
				BusinessManager.setActiveMap((Map)mapList.getSelectedValue());
				this.updateMapLocations();
				factoryTree.setSelectionRow(0);
				this.highlightSelectedFactory();
			} else {
				this.isMapSelected = false;
			}
			this.handleLocation();
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleLocation() {
		try {
			if (this.isMapSelected && this.isFactorySelected && this.selectedFactory != null) {
				this.updateMapLocations();
				this.highlightSelectedFactory();
				lblStatusBar.setText("");
				jTableLocation.setEnabled(true);
				Location loc = BusinessManager.getLocation(this.selectedFactory, (Map)mapList.getSelectedValue());
				jTableLocation.setModel(new DefaultTableModel(
						new String [][] { {""+loc.getXcoord(), ""+loc.getYcoord()} },
						columnNames));
				// Handle button status
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(true);
			}
			else {
				lblStatusBar.setText(defaultStatusBarText);
				jTableLocation.setEnabled(false);
				jTableLocation.setModel(emptyLocationTableModel);
				// Handle button status
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
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
			jTableLocation.setModel(emptyLocationTableModel);
			lblStatusBar.setText("This location is not configured yet.");
			btnAdd.setEnabled(true);
			btnRemove.setEnabled(false);
		}
	}
	
	private void btnAddActionPerformed(ActionEvent evt) {
		Object selectedObject = ((DefaultMutableTreeNode)factoryTree.getLastSelectedPathComponent()).getUserObject();
		if (mapList.getSelectedIndex() != -1 && selectedObject instanceof Factory) {
			this.applicationCore.setCoordinates();
		} else {
			Messages.getInstance().showWarningDialog(getRootPane(), "Warning", "You have to select a factory and a map to set its coordinates.");
		}
	}
	
	private void btnRemoveActionPerformed(ActionEvent evt) {
		Object selectedObject = ((DefaultMutableTreeNode)factoryTree.getLastSelectedPathComponent()).getUserObject();
		if (mapList.getSelectedIndex() != -1 && selectedObject instanceof Factory) {
			try {
				BusinessManager.removeLocation((Factory)selectedObject, (Map)mapList.getSelectedValue());
				Messages.getInstance().showInfoDialog(getRootPane(), "Operation successful", "Location removed.");
				this.handleLocation();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LocationNotFoundException e) {
				Messages.getInstance().showErrorDialog(getRootPane(), "Error", "This location does not exist.");
			}
		} else {
			Messages.getInstance().showWarningDialog(getRootPane(), "Warning", "You have to select a factory and a map to remove its coordinates.");
		}
	}

	public void manageOperation(WindowNotifierOperationCodes code, Object... objects) {
		switch (code) {
		case LocationCreated:
			if (objects.length == 1) {
				if (objects[0] instanceof Vector2f) {
					Vector2f coordinates = (Vector2f)objects[0];
					try {
						if (BusinessManager.addLocation(this.selectedFactory, BusinessManager.getActiveMap(), coordinates)) {
							this.handleLocation();
							Messages.getInstance().showInfoDialog(getRootPane(), "Operation successful", "A new location has been created.");
						}
					} catch (LocationAlreadyExistsException e) {
						Messages.getInstance().showErrorDialog(getRootPane(), "Error", "This location is already configured.");
					} catch (NoActiveMapException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MandatoryFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		}
	}

}
