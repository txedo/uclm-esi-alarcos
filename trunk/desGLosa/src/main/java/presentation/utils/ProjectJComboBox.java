package presentation.utils;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import model.business.control.BusinessManager;
import model.business.knowledge.Project;

public class ProjectJComboBox extends JComboBox {

	private static final long serialVersionUID = -3899353103224120764L;
	
	public ProjectJComboBox () {
		ComboBoxModel cbProjectModel = new DefaultComboBoxModel();
		setModel(cbProjectModel);
		setSelectedIndex(-1);
	}
	
	public ProjectJComboBox (boolean init) throws SQLException {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		setSelectedIndex(-1);
		if (init) load();
	}
	
	public void load () throws SQLException {
		for (Project p : BusinessManager.getAllProjects()) {
			addItem(p);
		}
	}
	
	public void load (List<Project> projects) {
		removeAllItems();
		for (Project p : projects) {
			addItem(p);
		}
	}
	
	public void reload () throws SQLException {
		Project oldSelection = (Project)getSelectedItem();
		removeAllItems();
		setSelectedIndex(-1);
//		this.setActionCommand("update");
		String previousAction = this.getActionCommand();
		this.setActionCommand("loadingData");
		this.load();
		this.setActionCommand(previousAction);
		boolean found = false;
		if (oldSelection != null) {
			for (int i = 0; i < getItemCount() && !found; i++) {
				if (getItemAt(i).equals(oldSelection)) {
					setSelectedIndex(i);
					found = true;
				}
			}
		} else setSelectedIndex(-1);
	}
}
