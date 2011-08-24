package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import model.util.City;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.GLObjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.SubprojectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.EntityNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.GroupByOperationNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class VisualizationAction extends ActionSupport {
	private int id;
	private boolean generateGLObjects;
	private String groupBy = ""; //company, market, factory, project
	private City city;
	private GLObjectManager glObjectManager;
	
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	private ProjectDAO projectDao;
	private SubprojectDAO subprojectDao;
	private MarketDAO marketDao;
	
	private List<Factory> factories;
	private List<Company> companies;
	private List<Project> projects;
	private List<Subproject> subprojects;

	public int getId() {
		return id;
	}

	public boolean isGenerateGLObjects() {
		return generateGLObjects;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public City getCity() {
		return city;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setGenerateGLObjects(boolean generateGLObjects) {
		this.generateGLObjects = generateGLObjects;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setGlObjectManager(GLObjectManager glObjectManager) {
		this.glObjectManager = glObjectManager;
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

	public void setMarketDao(MarketDAO marketDao) {
		this.marketDao = marketDao;
	}

	public void setFactories(List<Factory> factories) {
		this.factories = factories;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void setSubprojects(List<Subproject> subprojects) {
		this.subprojects = subprojects;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getFactories();
		companies = companyDao.getCompanies();
		projects = projectDao.getProjects();
		return SUCCESS;
	}
	
	public String factoryById() {
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
		if (generateGLObjects) city = glObjectManager.createGLFactories(factories, groupBy);
		return SUCCESS;
	}
	
	public String factoriesByCompanyId() {
		if (id == 0) {
			factories = factoryDao.getFactories();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			factories = factoryDao.findByNamedQuery("findFactoriesByCompanyId", queryParams);
		}
		if (generateGLObjects) city = glObjectManager.createGLFactories(factories, groupBy);
		return SUCCESS;
	}
	
	public String companyById() {
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
	
	public String projectById() {
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
//		if (generateGLObjects) city = glObjectManager.createGLProjects(projects, groupBy);
		if (generateGLObjects) {
			try {
				city = glObjectManager.createGLObjects(projects, groupBy, "asdf-1314183992774");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntityNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GroupByOperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String projectsByCompanyId() {
		if (id == 0) {
			projects = projectDao.getProjects();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			projects = projectDao.findByNamedQuery("findProjectsByCompanyId", queryParams);
		}
//		if (generateGLObjects) city = glObjectManager.createGLProjects(projects, groupBy);
		if (generateGLObjects) {
			try {
				city = glObjectManager.createGLObjects(projects, groupBy, "asdf-1314183992774");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntityNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GroupByOperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String projectsByFactoryId() {
		if (id == 0) {
			projects = projectDao.getProjects();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id", id);
			projects = projectDao.findByNamedQuery("findProjectsByFactoryId", queryParams);
		}
//		if (generateGLObjects) city = glObjectManager.createGLProjects(projects, groupBy);
		if (generateGLObjects) {
			try {
				city = glObjectManager.createGLObjects(projects, groupBy, "asdf-1314183992774");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntityNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GroupByOperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String subprojectById() {
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
		return SUCCESS;
	}
}
