package presentation.utils;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import model.business.control.BusinessManager;
import model.business.knowledge.Map;

public class MapsJComboBox extends JComboBox {
	private static final long serialVersionUID = -2463781867409116148L;

	public MapsJComboBox () {
		ComboBoxModel cbMapsModel = new DefaultComboBoxModel();
		setModel(cbMapsModel);
		setSelectedIndex(-1);
	}
	
	public MapsJComboBox (boolean init) throws SQLException {
		ComboBoxModel cbMapsModel = new DefaultComboBoxModel();
		setModel(cbMapsModel);
		setSelectedIndex(-1);
		if (init) load();
	}
	
	public void load () throws SQLException {
		for (Map m : BusinessManager.getAllMaps()) {
			addItem(m);
		}
	}
	
	public void load (List<Map> maps) {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		for (Map m : maps) {
			addItem(m);
		}
	}
}
