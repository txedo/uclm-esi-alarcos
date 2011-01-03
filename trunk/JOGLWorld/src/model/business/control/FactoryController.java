package model.business.control;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.CompanyNotFoundException;

import model.NotifyUIController;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.knowledge.Vector2f;

public class FactoryController {
	
	static public void addFactory (int companyId, Factory f) throws ConfigurationException, CompanyNotFoundException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		Company c = CompanyController.getCompany(companyId);
		f.setId(c.getLastFactoryId());
		c.addFactory(f);
		CompanyController.updateCompany(c);
		NotifyUIController.notifyFactoryListUpdate(c.getId());
	}

	public static void addFactoryLocation(int companyId, int factoryId, int mapId, Vector2f coordinates) throws ConfigurationException, CompanyNotFoundException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		boolean found = false;
		Company c = CompanyController.getCompany(companyId);
		for (int i = 0; i < c.getFactories().size() && !found; i++) {
			if (c.getFactories().get(i).getId() == factoryId) {
				c.getFactories().get(i).addLocation(mapId, coordinates);
				found = true;
			}
		}
		CompanyController.updateCompany(c);
		// TODO notificar
	}
}
