package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.gl.knowledge.GLAntennaBall;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.caption.Caption;
import model.util.City;
import model.util.Neighborhood;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class GLObjectManager {
	private CompanyDAO companyDao;
	private FactoryDAO factoryDao;
	private ProjectDAO projectDao;
	private MarketDAO marketDao;
	
	public City createGLProjects(List<Project> projects, String groupBy) {
		Caption caption = new Caption();
		Map<String, String> captionLines = new HashMap<String, String>();
		float maxSize = 0.0f;
		City c = new City();
		List<GLAntennaBall> glProjects = new ArrayList<GLAntennaBall>();
		
		if (projects != null && projects.size() > 0) {
			for (Project p : projects) {
				GLAntennaBall glp = new GLAntennaBall();
				// Set project id -> id
				glp.setId(p.getId());
				glp.setLabel(p.getName());
				glp.setProgression(p.isAudited());
				glp.setLeftChildBallValue(p.getRepairedIncidences());
				glp.setRightChildBallValue(p.getTotalIncidences() - p.getRepairedIncidences());
				glp.setColor(p.getMarket().getColor());
				float size = p.getSize();
				glp.setParentBallRadius(size);
				// maxSize will be used later to normalize sizes
				if (maxSize < size) maxSize = size;
				captionLines.put(p.getMarket().getName(), p.getMarket().getColor());
				glProjects.add(glp);
			}
			// Normalize project size
			for (GLAntennaBall gla : glProjects) {
				gla.setParentBallRadius(gla.getParentBallRadius()*GLAntennaBall.MAX_SIZE/maxSize);
			}
			// Configure caption
			caption = new Caption();
			caption.addLines(captionLines);
			if (caption.getLines().size() > 0) c.setCaption(caption);
			// Configure neighborhoods
			// projects and glProjects lists are correlative
			List <GLObject> flats;
			if (groupBy.equals("company")) {
				List <Company> availableCompanies = companyDao.getAll();
				for (Company comp : availableCompanies) {
					int projectIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Project p : projects) {
						boolean found = false;
						Iterator it = p.getSubprojects().iterator();
						while (it.hasNext() && !found) {
							Subproject spaux = (Subproject)it.next();
							if (spaux.getFactory().getCompany().getId() == comp.getId()) {
								flats.add(glProjects.get(projectIndex));
								found = true;
							}
						}
						projectIndex++;
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(comp.getName(), flats));
				}
			} else if (groupBy.equals("market")) {
				List <Market> availableMarkets = marketDao.getAll();
				for (Market m : availableMarkets) {
					int projectIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Project p : projects) {
						GLAntennaBall gla = glProjects.get(projectIndex++);
						if (gla.getColor().equals(m.getColor())) {
							flats.add(gla);
						}
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(m.getName(), flats));
				}
			} else if (groupBy.equals("factory")) {
				List <Factory> availableFactories = factoryDao.getAll();
				for (Factory f : availableFactories) {
					int projectIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Project p : projects) {
						boolean found = false;
						Iterator it = p.getSubprojects().iterator();
						while (it.hasNext() && !found) {
							Subproject spaux = (Subproject)it.next();
							if (spaux.getFactory().getId() == f.getId()) {
								flats.add(glProjects.get(projectIndex));
								found = true;
							}
						}
						projectIndex++;
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(f.getName(), flats));
				}
			} else { // no group by
				flats = new ArrayList<GLObject>();
				flats.addAll(glProjects);
				c.getNeighborhoods().add(new Neighborhood(flats));
			}
			// Finally, neighborhoods positions are calculated
			c.placeNeighborhoods();
		}
		return c;
	}
	
	public City createGLFactories(List<Factory> factories, String groupBy) {
		City c = new City();
		List<GLFactory> glFactories = new ArrayList<GLFactory>();
		if (factories != null && factories.size() > 0) {
			for (Factory f : factories) {
				GLFactory glf = new GLFactory();
				// Set factory id -> id
				glf.setId(f.getId());
				// Set number of projects -> smokestack height
				Map<String, Object> queryParams = new HashMap<String, Object>();
				queryParams.put("id", f.getId());
				glf.setSmokestackHeight(projectDao.findByNamedQuery("findProjectsByFactoryId", queryParams).size());
				// Set factory specialized market -> smokestack color
				List<Market> markets = marketDao.findByNamedQuery("findMostRepresentativeMarketByFactoryId", queryParams);
				if (markets.size() > 0) {
					Market market = markets.get(0);
					glf.setSmokestackColor(market.getColor());
				}
				else glf.setSmokestackColor("000000");
				// Set number of employees -> scale
				int employees = f.getEmployees();
				float scale = GLFactory.SMALL;
				if (employees < 150) scale = GLFactory.MEDIUM;
				else if (employees > 500) scale = GLFactory.BIG;
				glf.setScale(scale);
				// Add the glFactory to the glFactories list
				glFactories.add(glf);
			}
			
			// factories and glFactories lists are correlative
			List <GLObject> flats;
			if (groupBy.equals("company")) {
				List <Company> availableCompanies = companyDao.getAll();
				for (Company comp : availableCompanies) {
					int factoryIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Factory f : factories) {
						if (f.getCompany().getId() == comp.getId()) {
							flats.add(glFactories.get(factoryIndex++));
						}
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(comp.getName(), flats));
				}
			} else if (groupBy.equals("market")) {
				List <Market> availableMarkets = marketDao.getAll();
				for (Market m : availableMarkets) {
					int factoryIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Factory f : factories) {
						GLFactory glfaux = glFactories.get(factoryIndex++);
						if (glfaux.getSmokestackColor().equals(m.getColor())) {
							flats.add(glfaux);
						}
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(m.getName(), flats));
				}
			} else if (groupBy.equals("project")) {
				List <Project> availableProjects = projectDao.getAll();
				for (Project p : availableProjects) {
					int factoryIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Factory f : factories) {
						boolean found = false;
						Iterator it = p.getSubprojects().iterator();
						while (it.hasNext() && !found) {
							Subproject spaux = (Subproject)it.next();
							if (spaux.getFactory().getId() == f.getId()) {
								flats.add(glFactories.get(factoryIndex));
								found = true;
							}
						}
						factoryIndex++;
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(p.getName(), flats));
				}
			} else { // no group by
				flats = new ArrayList<GLObject>();
				flats.addAll(glFactories);
				c.getNeighborhoods().add(new Neighborhood(flats));
			}
			// Finally, neighborhoods positions are calculated
			c.placeNeighborhoods();
		}
		return c;
	}

	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public void setFactoryDao(FactoryDAO factoryDao) {
		this.factoryDao = factoryDao;
	}

	public void setProjectDao(ProjectDAO projectDao) {
		this.projectDao = projectDao;
	}

	public void setMarketDao(MarketDAO marketDao) {
		this.marketDao = marketDao;
	}
	
	
}
