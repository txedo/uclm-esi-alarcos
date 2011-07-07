package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import model.gl.knowledge.GLFactory;

import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ChartDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.SubprojectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ChartNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Dimension;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.model.View;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;

public class VisualizationAction extends ActionSupport {
	private int id;
	private String groupBy; //company, market, factory, project
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	private ProjectDAO projectDao;
	private SubprojectDAO subprojectDao;
	private ChartDAO chartDao;
	private MeasureDAO measureDao;
	private MarketDAO marketDao;
	
	private List<Factory> factories;
	private List<Company> companies;
	private List<Project> projects;
	private List<Subproject> subprojects;
	
	private List<GLFactory> glFactories;

	public int getId() {
		return id;
	}
	
	public String getGroupBy(){
		return this.groupBy;
	}
	
	public List<Factory> getFactories() {
		return factories;
	}

	public List<Company> getCompanies() {
		return companies;
	}
	
	public List<Project> getProjects() {
		return projects;
	}
	
	public List<Subproject> getSubprojects() {
		return subprojects;
	}
	
	public void setFactoryDao(FactoryDAO factoryDao) {
		this.factoryDao = factoryDao;
	}

	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	
	public void setProjectDao(ProjectDAO projectDao) {
		this.projectDao = projectDao;
	}
	
	public void setSubprojectDao(SubprojectDAO subprojectDao) {
		this.subprojectDao = subprojectDao;
	}
	
	public void setMeasureDao(MeasureDAO measureDao) {
		this.measureDao = measureDao;
	}
	
	public void setMarketDao(MarketDAO marketDao) {
		this.marketDao = marketDao;
	}
	
	public void setChartDao(ChartDAO chartDao) {
		this.chartDao = chartDao;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setGroupBy(String groupBy){
		this.groupBy = groupBy;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getFactories();
		companies = companyDao.getCompanies();
		projects = projectDao.getProjects();
		return SUCCESS;
	}
	
	private void createGLFactories() {
		if (factories != null && factories.size() > 0) {
			glFactories = new ArrayList<GLFactory>();
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
		}
	}
	
	public String getFactoriesJSON() {
		if (id == 0) {
			factories = factoryDao.getFactories();
		}
		else {
			try {
				factories = new ArrayList<Factory>();
				factories.add(factoryDao.getFactory(id));
			} catch (FactoryNotFoundException e) {
				return ERROR;
			}
		}
		createGLFactories();
		return SUCCESS;
	}
	
	public String getFactoriesFromCompanyJSON() {
		if (id == 0) {
			factories = factoryDao.getFactories();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			factories = factoryDao.findByNamedQuery("findFactoriesByCompanyId", queryParams);
		}
		return SUCCESS;
	}
	
	public String getCompaniesJSON() {
		if (id == 0) {
			companies = companyDao.getCompanies();
		}
		else {
			try {
				companies = new ArrayList<Company>();
				companies.add(companyDao.getCompany(id));
			} catch (CompanyNotFoundException e) {
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	private void completeProjectData () {
		if (projects != null && projects.size() > 0) {
			for (Project p : projects) {
				try {
					p.setProfile((Profile)XMLAgent.unmarshal(ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + p.getProfileName(), Profile.class));
					if (p.getProfile().getViews().size() > 0) {
						View v = (View) p.getProfile().getViews().get(0).clone();
						for (Dimension d : v.getDimensions()) {
							d.setMeasure(measureDao.getMeasure(d.getMeasureKey()));
						}
						v.setChart(chartDao.getChart(v.getChartName()));
						// A continuación se hace un pequeño tweak en el que haremos una vista por cada subprojecto
						// manteniendo la misma configuración de dimensiones entre vistas y actualizando únicamente sus valores
						// correspondiendo cada conjunto de valores con un subprojecto.
						List<View> tweakedViews = new ArrayList<View>();
						for (Subproject sp : p.getSubprojects()) {
							View aux = (View) v.clone();
							aux.obtainDimensionValues(sp.getCsvData());
							tweakedViews.add(aux);
						}
						p.getProfile().setViews(tweakedViews);
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MeasureNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChartNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void completeSubprojectData () {
		if (subprojects != null && subprojects.size() > 0) {
			for (Subproject sp : subprojects) {
				try {
					sp.setProfile((Profile)XMLAgent.unmarshal(ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + sp.getProfileName(), Profile.class));
					for (View v : sp.getProfile().getViews()) {
						v.obtainDimensionValues(sp.getCsvData());
						for (Dimension d : v.getDimensions()) {
							d.setMeasure(measureDao.getMeasure(d.getMeasureKey()));
						}
						v.setChart(chartDao.getChart(v.getChartName()));
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MeasureNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChartNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getProjectsJSON() {
		if (id == 0) {
			projects = projectDao.getProjects();
		}
		else {
			try {
				projects = new ArrayList<Project>();
				projects.add(projectDao.getProject(id));
			} catch (ProjectNotFoundException e) {
				return ERROR;
			}
		}
		completeProjectData();
		return SUCCESS;
	}
	
	public String getProjectsByCompanyIdJSON() {
		if (id == 0) {
			projects = projectDao.getProjects();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			projects = projectDao.findByNamedQuery("findProjectsByCompanyId", queryParams);
		}
		completeProjectData();
		return SUCCESS;
	}
	
	public String getProjectsByFactoryIdJSON() {
		if (id == 0) {
			projects = projectDao.getProjects();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			projects = projectDao.findByNamedQuery("findProjectsByFactoryId", queryParams);
		}
		completeProjectData();
		return SUCCESS;
	}
	
	public String getSubprojectByIdJSON() {
		if (id == 0) {
			subprojects = subprojectDao.getSubprojects();
		}
		else {
			try {
				subprojects = new ArrayList<Subproject>();
				subprojects.add(subprojectDao.getSubproject(id));
			} catch (SubprojectNotFoundException e) {
				return ERROR;
			}
		}
		completeSubprojectData();
		return SUCCESS;
	}
}
