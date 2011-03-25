package presentation.utils;

import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

import model.business.control.BusinessManager;
import model.business.knowledge.Map;

public class MapsJList extends JList {
	private static final long serialVersionUID = -2097561223083564437L;

	public MapsJList () {
		ListModel lMapsModel = new DefaultListModel();
		setModel(lMapsModel);
		setSelectedIndex(-1);
	}
	
	public MapsJList (boolean init) throws SQLException {
		ListModel lMapsModel = new DefaultListModel();
		setModel(lMapsModel);
		setSelectedIndex(-1);
		if (init) load();
	}
	
	public void load () throws SQLException {
		for (Map m : BusinessManager.getAllMaps()) {
			((DefaultListModel)getModel()).addElement(m);
		}
	}
	
	public void load (List<Map> maps) {
		ListModel lMapsModel = new DefaultListModel();
		setModel(lMapsModel);
		for (Map m : maps) {
			((DefaultListModel)getModel()).addElement(m);
		}
	}
	
	public void reload () throws SQLException {
		Map oldSelection = (Map)getSelectedValue();
		ListModel lMapsModel = new DefaultListModel();
		setModel(lMapsModel);
		this.load();
		boolean found = false;
		for (int i = 0; i < ((DefaultListModel)getModel()).getSize() && !found; i++) {
			if (((DefaultListModel)getModel()).get(i).equals(oldSelection)) {
				setSelectedIndex(i);
				found = true;
			}
		}
		if (!found) setSelectedIndex(-1);
	}
}
