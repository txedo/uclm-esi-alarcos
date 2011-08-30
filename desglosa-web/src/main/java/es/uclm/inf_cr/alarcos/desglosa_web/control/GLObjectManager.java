package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.WordUtils;
import org.springframework.web.context.ContextLoader;

import model.gl.knowledge.GLAntennaBall;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.util.City;
import model.util.Neighborhood;
import model.util.Color;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.EntityNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.GroupByOperationNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Field;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Mapping;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.util.MyHashMapEntryType;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;


public class GLObjectManager {
	
	private CompanyDAO companyDao;
	private FactoryDAO factoryDao;
	private ProjectDAO projectDao;
	private MarketDAO marketDao;
	
	public City createGLObjects(List entities, String groupBy, String profileName) throws JAXBException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, EntityNotSupportedException, GroupByOperationNotSupportedException, NoSuchFieldException {
		City c = new City();
		
		// Load selected profile
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + profileName + ".xml";
		Metaclass metaclass = XMLAgent.unmarshal(path, Metaclass.class);
		// Create a list of metaclass model objects
		Class classModel = Class.forName(metaclass.getModelName());
		List<Class<?>> objects = new ArrayList<Class<?>>();
		// Create a entity map where <key,value> is <column,value>. We have to access database
		String entityTable = metaclass.getEntityName();
		
		if (entities != null && entities.size() > 0) {
			// Create a map of list in which key is neighborhood name and value is a list of flats
			Map<String,List<?>> neighborhoods = groupEntitiesBy(entities.get(0).getClass(), entities, groupBy);
			// Iterate over each list creating GLObjects while creating GLNeighborhoods and GLCity
			Iterator it = neighborhoods.entrySet().iterator();
			while (it.hasNext()) {
				List<GLObject> glObjects = new ArrayList<GLObject>();
				Map.Entry<String, List<?>> pairs = (Map.Entry<String, List<?>>)it.next();
				for (Object e : pairs.getValue()) {
					Object glObj = classModel.newInstance();
					// Do mappings using java reflection
					for (Mapping mapping : metaclass.getMappings()) {
						// Build setter method using Java Reflective API
						String setterName = "set" + WordUtils.capitalize(mapping.getModelAttr().getName());
						// Look for the setter method through all the object hierarchy
						Class superClass = classModel;
						Method setterMethod = superClass.getMethod(setterName, mapping.getModelAttr().getParameterType());
						
						// Build getter method using Java Reflective API
						// oneWord_otherWord_anotherWord must be parsed to invoke getOneWord().getOtherWord().getAnotherWord()
						String[] chainOfGetters = mapping.getEntityAttr().getName().split("_");
						Class subClass = e.getClass();
						Object value = e;
						for (int i = 0; i < chainOfGetters.length && value != null; i++) {
							String getterPrefix = "get";
							if (i == chainOfGetters.length-1) {
								if (mapping.getModelAttr().getType().equals("boolean")) getterPrefix = "is";
							}
							String parentGetterName = getterPrefix + WordUtils.capitalize(chainOfGetters[i]);
							Method parentGetterMethod = subClass.getMethod(parentGetterName, null);
							value = parentGetterMethod.invoke(value, null);
							subClass = subClass.getDeclaredField(chainOfGetters[i]).getType();
						}
						if (value != null) {
							setterMethod.invoke(classModel.cast(glObj), value);
						} else {
							// TODO El valor en la base de datos es null y va a lanzar IllegalArgumentException
						}
					}
					// Apply constant values using java reflection too
					for (Field field : metaclass.getConstants()) {
						// Build setter method using Java Reflective API
						String setterName = "set" + WordUtils.capitalize(field.getName());
						Method setterMethod = classModel.getMethod(setterName, field.getParameterType());
						setterMethod.invoke(classModel.cast(glObj), field.getValue());
					}
					glObjects.add((GLObject)glObj);
				}
				if (glObjects.size() > 0) c.getNeighborhoods().add(new Neighborhood(pairs.getKey(), glObjects));
			}
			
			// Normalize
			
			// Configure caption
			if (metaclass.getCaptionLines().getEntry().size() > 0) {
				Map<String,String> captionLines = new HashMap<String,String>();
				for (MyHashMapEntryType mhmet : metaclass.getCaptionLines().getEntry()) {
					captionLines.put(mhmet.getKey(), mhmet.getValue());
				}
				c.setCaptionLines(captionLines);
			}
			
			// Finally, neighborhoods positions are calculated
			c.placeNeighborhoods();
		}
		
		return c;
	}
	
	private Map<String,List<?>> groupEntitiesBy(Class<?> clazz, List<?> entities, String groupBy) throws EntityNotSupportedException, GroupByOperationNotSupportedException {
		String className = clazz.getSimpleName();
		Map<String,List<?>> neighborhoods = new HashMap<String,List<?>>();
		if (className.equals("Factory")) {
			neighborhoods = groupFactoriesBy(entities, groupBy);
		} else if (className.equals("Project")) {
			neighborhoods = groupProjectsBy(entities, groupBy);
		} else if (className.equals("Subproject")) {
			neighborhoods = groupSubprojectsBy(entities, groupBy);
		} else {
			throw new EntityNotSupportedException();
		}
		return neighborhoods;
	}
	
	private Map<String,List<?>> groupFactoriesBy(List<?> factories, String groupBy) throws GroupByOperationNotSupportedException {
		Map<String,List<?>> neighborhoods = new HashMap<String,List<?>>();
		if (groupBy.equals("company")) {
			List <Company> availableCompanies = companyDao.getAll();
			for (Company comp : availableCompanies) {
				List<Factory> flats = new ArrayList<Factory>();
				for (Object f : factories) {
					if (((Factory)f).getCompany().getId() == comp.getId()) {
						flats.add((Factory)f);
					}
				}
				if (flats.size() > 0) neighborhoods.put(comp.getName(), flats);
			}
		} else if (groupBy.equals("market")) {
			List <Market> availableMarkets = marketDao.getAll();
			for (Market m : availableMarkets) {
				List<Factory> flats = new ArrayList<Factory>();
				for (Object f : factories) {
					if (((Factory)f).getMostRepresentativeMarket().equals(m)) flats.add((Factory)f);
				}
				if (flats.size() > 0) neighborhoods.put(m.getName(), flats);
			}
		} else if (groupBy.equals("project")) {
			List <Project> availableProjects = projectDao.getAll();
			for (Project p : availableProjects) {
				int factoryIndex = 0;
				List<Factory> flats = new ArrayList<Factory>();
				for (Object f : factories) {
					boolean found = false;
					Iterator it = p.getSubprojects().iterator();
					while (it.hasNext() && !found) {
						Subproject spaux = (Subproject)it.next();
						if (spaux.getFactory().getId() == ((Factory)f).getId()) {
							flats.add((Factory)f);
							found = true;
						}
					}
					factoryIndex++;
				}
				if (flats.size() > 0) neighborhoods.put(p.getName(), flats);
			}
		} else if (groupBy.equals("")) {
			neighborhoods.put("", factories);
		} else throw new GroupByOperationNotSupportedException();
		return neighborhoods;
	}

	private Map<String,List<?>> groupProjectsBy(List<?> projects, String groupBy) throws GroupByOperationNotSupportedException {
		Map<String,List<?>> neighborhoods = new HashMap<String,List<?>>();
		if (groupBy.equals("company")) {
			List <Company> availableCompanies = companyDao.getAll();
			for (Company comp : availableCompanies) {
				List<Project> flats = new ArrayList<Project>();
				for (Object p : projects) {
					boolean found = false;
					Iterator it = ((Project)p).getSubprojects().iterator();
					while (it.hasNext() && !found) {
						Subproject spaux = (Subproject)it.next();
						if (spaux.getFactory().getCompany().getId() == comp.getId()) {
							flats.add((Project)p);
							found = true;
						}
					}
				}
				if (flats.size() > 0) neighborhoods.put(comp.getName(), flats);
			}
		} else if (groupBy.equals("market")) {
			List <Market> availableMarkets = marketDao.getAll();
			for (Market m : availableMarkets) {
				List<Project> flats = new ArrayList<Project>();
				for (Object p : projects) {
					if (((Project)p).getMarket().equals(m)) flats.add((Project)p);
				}
				if (flats.size() > 0) neighborhoods.put(m.getName(), flats);
			}
		} else if (groupBy.equals("factory")) {
			List <Factory> availableFactories = factoryDao.getAll();
			for (Factory f : availableFactories) {
				List<Project> flats = new ArrayList<Project>();
				for (Object p : projects) {
					boolean found = false;
					Iterator it = ((Project)p).getSubprojects().iterator();
					while (it.hasNext() && !found) {
						Subproject spaux = (Subproject)it.next();
						if (spaux.getFactory().getId() == f.getId()) {
							flats.add((Project)p);
							found = true;
						}
					}
				}
				if (flats.size() > 0) neighborhoods.put(f.getName(), flats);
			}
		} else if (groupBy.equals("")) {
			neighborhoods.put("", projects);
		} else throw new GroupByOperationNotSupportedException();
		return neighborhoods;
	}
	
	private Map<String,List<?>> groupSubprojectsBy(List<?> subprojects, String groupBy) throws GroupByOperationNotSupportedException {
		Map<String,List<?>> neighborhoods = new HashMap<String,List<?>>();
		if (groupBy.equals("company")) {

		} else if (groupBy.equals("market")) {
			
		} else if (groupBy.equals("factory")) {
			
		} else if (groupBy.equals("project")) {
			
		} else if (groupBy.equals("")) {
			neighborhoods.put("", subprojects);
		} else throw new GroupByOperationNotSupportedException();
		return neighborhoods;
	}

	public City createGLProjects(List<Project> projects, String groupBy) {
		City c = new City();
		List<GLAntennaBall> glProjects = new ArrayList<GLAntennaBall>();
		
		if (projects != null && projects.size() > 0) {
			float maxSize = 0.0f;
			Map<String, String> captionLines = new HashMap<String, String>();
			
			for (Project p : projects) {
				GLAntennaBall glp = new GLAntennaBall();
				// Set project id -> id
				glp.setId(p.getId());
				glp.setLabel(p.getName());
				glp.setProgressionMark(p.isAudited());
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
			if (captionLines.size() > 0) {
				c.setCaptionLines(captionLines);
			}
			
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
						if (p.getMarket().equals(m)) {
							GLAntennaBall gla = glProjects.get(projectIndex++);
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
				float scale = GLFactory.SMALL;
				if (f.getEmployees() >= 50 && f.getEmployees() <= 150) scale = GLFactory.MEDIUM;
				else if (f.getEmployees() > 150) scale = GLFactory.BIG;
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
							flats.add(glFactories.get(factoryIndex));
						}
						factoryIndex++;
					}
					if (flats.size() > 0) c.getNeighborhoods().add(new Neighborhood(comp.getName(), flats));
				}
			} else if (groupBy.equals("market")) {
				List <Market> availableMarkets = marketDao.getAll();
				for (Market m : availableMarkets) {
					int factoryIndex = 0;
					flats = new ArrayList<GLObject>();
					for (Factory f : factories) {
						GLFactory glfaux = glFactories.get(factoryIndex);
						if (glfaux.getSmokestackColor().equals(new Color(m.getColor()))) {
							flats.add(glfaux);
						}
						factoryIndex++;
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
