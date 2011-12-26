package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import model.util.City;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GLObjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.SubprojectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.EntityNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.GroupByOperationNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class VisualizationAction extends ActionSupport {
    private static final long serialVersionUID = 2840789582526466159L;
    private int id;
    private boolean generateGLObjects;
    private String groupBy = ""; // company, market, factory, project
    private String profileFileName;
    private City city;
    private GLObjectManager glObjectManager;

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

    public void setProfileFileName(String profileFileName) {
        this.profileFileName = profileFileName;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setGlObjectManager(GLObjectManager glObjectManager) {
        this.glObjectManager = glObjectManager;
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
        factories = FactoryManager.getAllFactories();
        companies = CompanyManager.getAllCompanies();
        projects = ProjectManager.getAllProjects();
        return SUCCESS;
    }

    public String factoryById() {
        if (id == 0) {
            factories = FactoryManager.getAllFactories();
        } else {
            try {
                factories = new ArrayList<Factory>();
                factories.add(FactoryManager.getFactory(id));
            } catch (FactoryNotFoundException e) {
                return ERROR;
            }
        }
        if (generateGLObjects) {
            entity2model(factories);
        }
        return SUCCESS;
    }

    public String factoriesByCompanyId() {
        if (id == 0) {
            factories = FactoryManager.getAllFactories();
        } else {
            try {
                factories = new ArrayList<Factory>(CompanyManager.getCompany(id).getFactories());
            } catch (CompanyNotFoundException e) {
                return ERROR;
            }
        }
        if (generateGLObjects) {
            entity2model(factories);
        }
        return SUCCESS;
    }

    public String companyById() {
        if (id == 0) {
            companies = CompanyManager.getAllCompanies();
        } else {
            try {
                companies = new ArrayList<Company>();
                companies.add(CompanyManager.getCompany(id));
            } catch (CompanyNotFoundException e) {
                return ERROR;
            }
        }
        return SUCCESS;
    }

    public String projectsByCompanyId() {
        if (id == 0) {
            projects = ProjectManager.getAllProjects();
        } else {
            projects = ProjectManager.getDevelopingProjectsByCompanyId(id);
        }
        if (generateGLObjects) {
            entity2model(projects);
        }
        return SUCCESS;
    }

    public String projectsByFactoryId() {
        if (id == 0) {
            projects = ProjectManager.getAllProjects();
        } else {
            projects = ProjectManager.getDevelopingProjectsByFactoryId(id);
        }
        if (generateGLObjects) {
            entity2model(projects);
        }
        return SUCCESS;
    }

    public String projectById() {
        if (id == 0) {
            projects = ProjectManager.getAllProjects();
        } else {
            try {
                projects = new ArrayList<Project>();
                projects.add(ProjectManager.getProject(id));
            } catch (ProjectNotFoundException e) {
                return ERROR;
            }
        }
        if (generateGLObjects) {
            entity2model(projects);
        }
        return SUCCESS;
    }

    public String subprojectsByCompanyId() {
        if (id == 0) {
            subprojects = SubprojectManager.getAllSubprojects();
        } else {
            subprojects = SubprojectManager.getDevelopingSubprojectsByCompanyId(id);
        }
        if (generateGLObjects) {
            entity2model(subprojects);
        }
        return SUCCESS;
    }

    public String subprojectsByFactoryId() {
        if (id == 0) {
            subprojects = SubprojectManager.getAllSubprojects();
        } else {
            try {
                subprojects = new ArrayList<Subproject>(FactoryManager.getFactory(id).getSubprojects());
            } catch (FactoryNotFoundException e) {
                return ERROR;
            }
        }
        if (generateGLObjects) {
            entity2model(subprojects);
        }
        return SUCCESS;
    }

    public String subprojectsByProjectId() {
        if (id == 0) {
            subprojects = SubprojectManager.getAllSubprojects();
        } else {
            try {
                subprojects = new ArrayList<Subproject>(ProjectManager.getProject(id).getSubprojects());
            } catch (ProjectNotFoundException e) {
                return ERROR;
            }
        }
        if (generateGLObjects) {
            entity2model(subprojects);
        }
        return SUCCESS;
    }

    public String subprojectById() {
        if (id == 0) {
            subprojects = SubprojectManager.getAllSubprojects();
        } else {
            try {
                subprojects = new ArrayList<Subproject>();
                subprojects.add(SubprojectManager.getSubproject(id));
            } catch (SubprojectNotFoundException e) {
                return ERROR;
            }
        }
        return SUCCESS;
    }

    private void entity2model(List<?> entities) {
        try {
            city = glObjectManager.createGLObjects(entities, groupBy, profileFileName);
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
}
