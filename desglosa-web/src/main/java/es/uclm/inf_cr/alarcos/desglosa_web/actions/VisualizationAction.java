package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ChartDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.SubprojectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;

public class VisualizationAction extends ActionSupport {
	private int id;
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	private ProjectDAO projectDao;
	private SubprojectDAO subprojectDao;
	private ChartDAO chartDao;
	private MeasureDAO measureDao;
	
	private List<Factory> factories;
	private List<Company> companies;
	private List<Project> projects;
	private List<Subproject> subprojects;

	public int getId() {
		return id;
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
	
	public void setChartDao(ChartDAO chartDao) {
		this.chartDao = chartDao;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getFactories();
		companies = companyDao.getCompanies();
		projects = projectDao.getProjects();
		return SUCCESS;
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
		
		return SUCCESS;
	}
	
	public String getSubprojectsAndProfilesByProjectIdJSON() {
		if (id == 0) {
			subprojects = subprojectDao.getSubprojects();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			subprojects = subprojectDao.findByNamedQuery("findSubprojectsByProjectId", queryParams);
		}
		for (Subproject sp : subprojects) {
			try {
				sp.setProfile((Profile)XMLAgent.unmarshal(ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + sp.getProfileName(), Profile.class));
				
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
			}
		}
		
		return SUCCESS;
	}

}
