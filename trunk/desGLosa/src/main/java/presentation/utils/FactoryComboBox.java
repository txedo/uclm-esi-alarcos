package presentation.utils;

import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import model.business.control.BusinessManager;
import model.business.knowledge.Factory;

public class FactoryComboBox extends JComboBox {

	private static final long serialVersionUID = 249932414605599479L;

	public FactoryComboBox () throws SQLException {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		for (Factory f : BusinessManager.getAllFactories()) {
			addItem(f);
		}
		setSelectedIndex(-1);
	}
}
