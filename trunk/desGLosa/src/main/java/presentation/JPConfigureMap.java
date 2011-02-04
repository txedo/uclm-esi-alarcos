package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.FactoryNotFoundException;
import exceptions.LocationNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.MapNotFoundException;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import model.business.control.BusinessManager;
import model.business.knowledge.Factory;
import model.business.knowledge.Location;
import model.business.knowledge.Map;

import org.jdesktop.application.Application;

import presentation.utils.FactoryTree;
import presentation.utils.MapsComboBox;



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
public class JPConfigureMap extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965274680612432098L;
	private JComboBox cbMaps;
	private JLabel lblMap;
	private JScrollPane jScrollPane1;
	private JButton btnRemove;
	private JButton btnSet;
	private JLabel lblStatusBar;
	private JLabel lblCoordinates;
	private JPanel jPanelTable;
	private JTable jTableLocation;
	private JTree jTree1;
	private final String [] columnNames = {"X" , "Y"};
	private final TableModel emptyLocationTableModel = new DefaultTableModel(
			new String [][] { {"", ""} },
			columnNames);
	private final String defaultStatusBarText = "Choose a factory and a map to check its coordinates.";
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	
	public JPConfigureMap() {
		super();
		initGUI();
		jTableLocation.setFocusable(false);
		jTableLocation.setShowGrid(true);
		jTableLocation.setEnabled(false);
		jPanelTable.add(jTableLocation.getTableHeader(), BorderLayout.PAGE_START);
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 112dlu, max(p;5dlu), max(p;5dlu), 5dlu, 92dlu", 
					"5dlu, 12dlu, 5dlu, 12dlu, 3dlu, 27dlu, 3dlu, 13dlu, 5dlu, 23dlu, 12dlu");
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(506, 433));
			{
				lblMap = new JLabel();
				this.add(lblMap, new CellConstraints("4, 2, 1, 1, default, default"));
				lblMap.setName("lblMap");
			}
			{
				cbMaps = new MapsComboBox();
				this.add(cbMaps, new CellConstraints("6, 2, 1, 1, default, default"));
				cbMaps.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbMapsActionPerformed(evt);
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, new CellConstraints("2, 2, 1, 10, default, default"));
				{
					jTree1 = new FactoryTree();
					jScrollPane1.setViewportView(jTree1);
					jTree1.addTreeSelectionListener(new TreeSelectionListener() {
						public void valueChanged(TreeSelectionEvent evt) {
							jTree1ValueChanged(evt);
						}
					});
				}
			}
			{
				jPanelTable = new JPanel();
				BorderLayout jPanelTableLayout = new BorderLayout();
				jPanelTable.setLayout(jPanelTableLayout);
				this.add(jPanelTable, new CellConstraints("4, 6, 3, 1, default, default"));
				{
					jTableLocation = new JTable();
					jPanelTable.add(jTableLocation, BorderLayout.CENTER);
					jTableLocation.setModel(emptyLocationTableModel);
					jTableLocation.setPreferredSize(new java.awt.Dimension(249, 46));
				}
			}
			{
				lblCoordinates = new JLabel();
				this.add(lblCoordinates, new CellConstraints("4, 4, 1, 1, default, default"));
				lblCoordinates.setName("lblCoordinates");
			}
			{
				lblStatusBar = new JLabel();
				lblStatusBar.setText(defaultStatusBarText);
				this.add(lblStatusBar, new CellConstraints("4, 7, 3, 2, fill, fill"));
				lblStatusBar.setName("lblStatusBar");
			}
			{
				btnSet = new JButton();
				this.add(btnSet, new CellConstraints("4, 10, 1, 1, default, default"));
				btnSet.setName("btnSet");
			}
			{
				btnRemove = new JButton();
				this.add(btnRemove, new CellConstraints("6, 10, 1, 1, default, default"));
				btnRemove.setName("btnRemove");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jTree1ValueChanged(TreeSelectionEvent evt) {
		handleLocation();	
	}
	
	private void cbMapsActionPerformed(ActionEvent evt) {
		handleLocation();
	}

	private void handleLocation() {
		try {
			Object selectedObject = ((DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent()).getUserObject();
			if (selectedObject instanceof Factory && cbMaps.getSelectedIndex() != -1) {
				lblStatusBar.setText("");
				jTableLocation.setEnabled(true);
				Location loc = BusinessManager.getLocation((Factory)selectedObject, (Map)cbMaps.getSelectedItem());
				TableModel jTableLocationModel = new DefaultTableModel(
						new String [][] { {""+loc.getXcoord(), ""+loc.getYcoord()} },
						columnNames);
				jTableLocation.setModel(jTableLocationModel);
				// Handle button status
				btnSet.setEnabled(false);
				btnRemove.setEnabled(true);
			}
			else {
				lblStatusBar.setText(defaultStatusBarText);
				jTableLocation.setEnabled(false);
				jTableLocation.setModel(emptyLocationTableModel);
				// Handle button status
				btnSet.setEnabled(false);
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
			btnSet.setEnabled(true);
			btnRemove.setEnabled(false);
		}
	}
	


}
