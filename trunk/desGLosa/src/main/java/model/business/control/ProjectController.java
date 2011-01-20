package model.business.control;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.CompanyNotFoundException;

import model.business.knowledge.Centre;
import model.business.knowledge.Project;
import model.knowledge.Vector2f;

public class ProjectController {
	
	public static HashMap<Centre, HashMap<Integer,Vector2f>> getInvolvedCentreLocations (Project p) throws ConfigurationException, CompanyNotFoundException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		HashMap<Centre, HashMap<Integer,Vector2f>> res = new HashMap<Centre, HashMap<Integer,Vector2f>>();
		
		for (Centre centre : p.getInvolvedCentres()) {
			res.put(centre, CentreController.getCentreLocations(centre));
		}

		return res;
	}
}
