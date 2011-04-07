package presentation.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import model.business.control.BusinessManager;
import model.business.knowledge.Factory;

public class FactoryJList extends JList {

	private static final long serialVersionUID = 3812891999421445787L;

	public FactoryJList () {
		DefaultListModel factoryListModel = new DefaultListModel();
		setModel(factoryListModel);
	}
	
	public void loadFactories () throws SQLException {
		for (Factory f : BusinessManager.getAllFactories()) {
			((DefaultListModel)getModel()).addElement((Factory)f);
		}
	}
	
	public void addFactory (Factory factory) {
		((DefaultListModel)getModel()).addElement((Factory)factory);
	}
	
	public void removeFactory (Factory factory) {
		for (int i = 0; i < getModel().getSize(); i++) {
			if (factory.equals((Factory)getModel().getElementAt(i))) {
				((DefaultListModel)getModel()).removeElementAt(i);
			}
		}
	}
	
	public void removeAll () {
		((DefaultListModel)getModel()).removeAllElements();
	}
	
	public boolean contains (Factory factory) {
		boolean contains = false;
		for (int i = 0; i < getModel().getSize(); i++) {
			if (factory.equals((Factory)getModel().getElementAt(i))) {
				contains = true;
			}
		}
		return contains;
	}
	
	public List<Factory> toList () {
		List<Factory> result = new ArrayList<Factory>();;
		for (int i = 0; i < getModel().getSize(); i++) {
			result.add((Factory)getModel().getElementAt(i));
		}
		return result;
	}

}
