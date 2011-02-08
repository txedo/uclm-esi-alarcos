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
		ComboBoxModel cbProjectModel = new DefaultComboBoxModel();
		setModel(cbProjectModel);
		for (Project p : projects) {
			addItem(p);
		}
	}
}
