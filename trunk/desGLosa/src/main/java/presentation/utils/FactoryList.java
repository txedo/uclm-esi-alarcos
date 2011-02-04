package presentation.utils;

import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

import model.business.control.BusinessManager;

public class FactoryList extends JList {

	private static final long serialVersionUID = 3812891999421445787L;

	public FactoryList () throws SQLException {
		ListModel factoryListModel = new DefaultListModel();
		setModel(factoryListModel);
		setListData(BusinessManager.getAllFactories().toArray());
	}
}
