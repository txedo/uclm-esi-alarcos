package presentation.utils;

import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import model.business.control.BusinessManager;
import model.business.knowledge.Map;

public class MapsComboBox extends JComboBox {
	private static final long serialVersionUID = -2463781867409116148L;

	public MapsComboBox () throws SQLException {
		ComboBoxModel cbMapsModel = new DefaultComboBoxModel();
		setModel(cbMapsModel);
		for (Map m : BusinessManager.getAllMaps()) {
			addItem(m);
		}
		setSelectedIndex(-1);
	}
}
