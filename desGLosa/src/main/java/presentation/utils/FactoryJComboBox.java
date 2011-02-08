package presentation.utils;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import model.business.control.BusinessManager;
import model.business.knowledge.Factory;

public class FactoryJComboBox extends JComboBox {

	private static final long serialVersionUID = 249932414605599479L;

	public FactoryJComboBox () {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		setSelectedIndex(-1);
	}
	
	public FactoryJComboBox (boolean init) throws SQLException {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		setSelectedIndex(-1);
		if (init) loadFactories();
	}
	
	public void loadFactories () throws SQLException {
		for (Factory f : BusinessManager.getAllFactories()) {
			addItem(f);
		}
	}
	
	public void loadFactories (List<Factory> factories) {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
		for (Factory f : factories) {
			addItem(f);
		}
	}
}
