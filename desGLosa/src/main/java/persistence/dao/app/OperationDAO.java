package persistence.dao.app;

import java.util.ArrayList;
import java.util.List;

import model.app.knowledge.Operation;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import persistence.XMLAgent;

public class OperationDAO {
	private String xmlfile;
	private XMLConfiguration xmlConf;
	
	public OperationDAO () throws ConfigurationException {
		this.xmlfile = XMLAgent.getXMLFilename("operations");
		this.xmlConf = XMLAgent.getXMLConfiguration(this.xmlfile);
	}
	
	public List<Operation> getAll () {
		int entities = 0;
		List<Operation> res = new ArrayList<Operation>();
		
		entities = this.xmlConf.getList("entity.container").size();
		for (int i = 0; i < entities; i++) {
			String name = this.xmlConf.getString("entity("+i+")[@name]");
			String description = this.xmlConf.getString("entity("+i+").description");
			String icon = this.xmlConf.getString("entity("+i+").icon");
			String container = this.xmlConf.getString("entity("+i+").container");
			String type = this.xmlConf.getString("entity("+i+").container[@type]");
			res.add(new Operation(name, description, icon, container, type));
		}
		
		return res;
	}
	
}
