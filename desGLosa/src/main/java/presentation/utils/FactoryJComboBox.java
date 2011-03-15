package presentation.utils;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import exceptions.CompanyNotFoundException;

import model.business.control.BusinessManager;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;

public class FactoryJComboBox extends JComboBox {

	private static final long serialVersionUID = 249932414605599479L;

	public FactoryJComboBox () {
		this.init();
		setSelectedIndex(-1);
	}
	
	public FactoryJComboBox (boolean init) throws SQLException {
		this.init();
		setSelectedIndex(-1);
		if (init) this.load();
	}
	
	private void init () {
		ComboBoxModel cbFactoriesModel = new DefaultComboBoxModel();
		setModel(cbFactoriesModel);
	}
	
	public void load () throws SQLException {
		this.init();
		for (Factory f : BusinessManager.getAllFactories()) {
			addItem(f);
		}
	}
	
	public void load (int idCompany) throws SQLException, CompanyNotFoundException {
		this.init();
		Company c = BusinessManager.getCompany(idCompany);
		for (Factory f : BusinessManager.getFactories(c)) {
			addItem(f);
		}
	}
	
	public void load (List<Factory> factories) {
		this.init();
		for (Factory f : factories) {
			addItem(f);
		}
	}
}
