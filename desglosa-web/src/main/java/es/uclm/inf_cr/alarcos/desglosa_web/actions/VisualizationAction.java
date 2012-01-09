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
import es.uclm.inf_cr.alarcos.desglosa_web.exception.IncompatibleTypesException;
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
        subprojects = SubprojectManager.getAllSubprojects();
        return SUCCESS;
    }

    public String factoryById() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                factories = FactoryManager.getAllFactories();
            } else {
                factories = new ArrayList<Factory>();
                factories.add(FactoryManager.getFactory(id));
            }
            if (generateGLObjects) {
                result = entity2model(factories);
            }
        } catch (FactoryNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    public String factoriesByCompanyId() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                factories = FactoryManager.getAllFactories();
            } else {
                factories = new ArrayList<Factory>(CompanyManager.getCompany(id).getFactories());
            }
            if (generateGLObjects) {
                result = entity2model(factories);
            }
        } catch (CompanyNotFoundException e) {
            result = ERROR;
        }
        return result;
    }
    
    public String factoriesByProjectId() {
        // This action returns involved factories in a project
        String result = SUCCESS;
        if (id == 0) {
            factories = FactoryManager.getAllFactories();
        } else {
            factories = new ArrayList<Factory>(FactoryManager.getFactoriesInvolvedInProject(id));
        }
        if (generateGLObjects) {
            result = entity2model(factories);
        }
        return result;
    }

    public String companyById() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                companies = CompanyManager.getAllCompanies();
            } else {
                companies = new ArrayList<Company>();
                companies.add(CompanyManager.getCompany(id));
            }
            if (generateGLObjects) {
                result = entity2model(companies);
            }
        } catch (CompanyNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    public String projectsByCompanyId() {
        String result = SUCCESS;
        if (id == 0) {
            projects = ProjectManager.getAllProjects();
        } else {
            projects = ProjectManager.getDevelopingProjectsByCompanyId(id);
        }
        if (generateGLObjects) {
            result = entity2model(projects);
        }
        return result;
    }

    public String projectsByFactoryId() {
        String result = SUCCESS;
        if (id == 0) {
            projects = ProjectManager.getAllProjects();
        } else {
            projects = ProjectManager.getDevelopingProjectsByFactoryId(id);
        }
        if (generateGLObjects) {
            result = entity2model(projects);
        }
        return result;
    }

    public String projectById() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                projects = ProjectManager.getAllProjects();
            } else {
                projects = new ArrayList<Project>();
                projects.add(ProjectManager.getProject(id));
            }
            if (generateGLObjects) {
                result = entity2model(projects);
            }
        } catch (ProjectNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    public String subprojectsByCompanyId() {
        String result = SUCCESS;
        if (id == 0) {
            subprojects = SubprojectManager.getAllSubprojects();
        } else {
            subprojects = SubprojectManager.getDevelopingSubprojectsByCompanyId(id);
        }
        if (generateGLObjects) {
            result = entity2model(subprojects);
        }
        return result;
    }

    public String subprojectsByFactoryId() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                subprojects = SubprojectManager.getAllSubprojects();
            } else {
                subprojects = new ArrayList<Subproject>(FactoryManager.getFactory(id).getSubprojects());
            }
            if (generateGLObjects) {
                result = entity2model(subprojects);
            }
        } catch (FactoryNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    public String subprojectsByProjectId() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                subprojects = SubprojectManager.getAllSubprojects();
            } else {
                subprojects = new ArrayList<Subproject>(ProjectManager.getProject(id).getSubprojects());
            }
            if (generateGLObjects) {
                result = entity2model(subprojects);
            }
        } catch (ProjectNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    public String subprojectById() {
        String result = SUCCESS;
        try {
            if (id == 0) {
                subprojects = SubprojectManager.getAllSubprojects();
            } else {
                subprojects = new ArrayList<Subproject>();
                subprojects.add(SubprojectManager.getSubproject(id));
            }
            if (generateGLObjects) {
                result = entity2model(subprojects);
            }
        } catch (SubprojectNotFoundException e) {
            result = ERROR;
        }
        return result;
    }

    private String entity2model(List<?> entities) {
        String result = ERROR;
        try {
            city = GLObjectManager.createGLObjects(entities, groupBy, profileFileName);
            result = SUCCESS;
        } catch (SecurityException e) {
            addActionError(getText("exception.security"));
        } catch (IllegalArgumentException e) {
            addActionError(getText("exception.illegal_argument"));
        } catch (JAXBException e) {
            addActionError(getText("exception.jaxb"));
        } catch (IOException e) {
            addActionError(getText("exception.io"));
        } catch (InstantiationException e) {
            addActionError(getText("exception.instantiation"));
        } catch (IllegalAccessException e) {
            addActionError(getText("exception.illegal_access"));
        } catch (ClassNotFoundException e) {
            addActionError(getText("exception.class_not_found"));
        } catch (NoSuchMethodException e) {
            addActionError(getText("exception.no_such_method"));
        } catch (InvocationTargetException e) {
            addActionError(getText("exception.invocation_target"));
        } catch (EntityNotSupportedException e) {
            addActionError(getText("exception.entity_not_supported"));
        } catch (GroupByOperationNotSupportedException e) {
            addActionError(getText("exception.group_by_operation_not_supported"));
        } catch (NoSuchFieldException e) {
            addActionError(getText("exception.no_such_field"));
        } catch (IncompatibleTypesException e) {
            addActionError(getText("exception.incompatible_types"));
        }
        return result;
    }
}
