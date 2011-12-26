package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.SubprojectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class SubprojectAction extends ActionSupport implements
        GenericActionInterface {
    private static final long serialVersionUID = -8802591889117560853L;
    private int id;
    private List<Subproject> subprojects;
    private List<Factory> factories;
    private List<Project> projects;
    private Subproject subproject;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the subprojects
     */
    public List<Subproject> getSubprojects() {
        return subprojects;
    }
    
    /**
     * @return the factories
     */
    public List<Factory> getFactories() {
        return factories;
    }

    /**
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * @return the subproject
     */
    public Subproject getSubproject() {
        return subproject;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param subprojects the subprojects to set
     */
    public void setSubprojects(List<Subproject> subprojects) {
        this.subprojects = subprojects;
    }
    
    /**
     * @param factories the factories to set
     */
    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * @param subproject the subproject to set
     */
    public void setSubproject(Subproject subproject) {
        this.subproject = subproject;
    }

    @Override
    public String execute() {
        subprojects = SubprojectManager.getAllSubprojects();
        return SUCCESS;
    }
    
    public void validateDoShowForm() {
        try {
            // Check if id is valid
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            // Check that there is a project with such id
            if (!SubprojectManager.checkSubprojectExists(id)) {
                addActionError(getText("error.subproject.id"));
            }
        } catch (NullIdParameterException e) {
            // This is expected. So show blank form
        } catch (NotValidIdParameterException e) {
            addActionError(getText("error.subproject.id"));
        }
        if (hasActionErrors()) {
            subprojects = SubprojectManager.getAllSubprojects();
        }
    }
    
    public String showForm() throws Exception {
        // Get all projects and all factories
        projects = ProjectManager.getAllProjects();
        factories = FactoryManager.getAllFactories();
        // if id == 0 then show a blank form
        if (id > 0) {
            subproject = SubprojectManager.getSubproject(id);
        }
        return SUCCESS;
    }
    
    public void validateDoSave() {
        if (subproject != null) {
            // Check that the project ID
            if (subproject.getProject() != null) {
                // If it exists, set it to the project main factory
                try {
                    subproject.setProject(ProjectManager.getProject(subproject.getProject().getId()));
                } catch (ProjectNotFoundException e) {
                    addFieldError("error.project_required", getText("error.subproject.project.required"));
                }
            } else {
                addFieldError("error.project_required", getText("error.subproject.project.required"));
            }
            // Check that the factory ID
            if (subproject.getFactory() != null) {
                // If it exists, set it to the project main factory
                try {
                    subproject.setFactory(FactoryManager.getFactory(subproject.getFactory().getId()));
                } catch (FactoryNotFoundException e) {
                    addFieldError("error.factory_required", getText("error.subproject.factory.required"));
                }
            } else {
                addFieldError("error.factory_required", getText("error.subproject.factory.required"));
            }
            // Check that required fields are filled in
            if (GenericManager.isEmptyString(subproject.getName())) {
                addFieldError("error.subproject.name", getText("error.subproject.name"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            projects = ProjectManager.getAllProjects();
            factories = FactoryManager.getAllFactories();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }
    
    public String save() {
        SubprojectManager.saveSubproject(subproject);
        addActionMessage(getText("message.subproject.added_successfully"));
        return SUCCESS;
    }
    
    public void validateDoEdit() {
        if (subproject != null) {
            try {
                // Check that project ID is valid
                GenericManager.checkValidId(subproject.getId());
                // Check that the project exists
                SubprojectManager.getSubproject(subproject.getId());
                // Check that the project ID exists
                if (subproject.getProject() != null) {
                    // If it exists, set it to the project main factory
                    subproject.setProject(ProjectManager.getProject(subproject.getProject().getId()));
                } else {
                    addFieldError("error.project_required", getText("error.subproject.project.required"));
                }
                // Check that the factory ID exits
                if (subproject.getFactory() != null) {
                    // If it exists, set it to the project main factory
                    subproject.setFactory(FactoryManager.getFactory(subproject.getFactory().getId()));
                } else {
                    addFieldError("error.factory_required", getText("error.subproject.factory.required"));
                }
            } catch (FactoryNotFoundException e) {
                addFieldError("error.factory_required", getText("error.subproject.factory.required"));
            } catch (ProjectNotFoundException e) {
                addFieldError("error.project_required", getText("error.subproject.project.required"));
            } catch (NotValidIdParameterException e) {
                addActionError(getText("error.subproject.id"));
            } catch (SubprojectNotFoundException e) {
                addActionError(getText("error.subproject.id"));
            }
            // Check that required fields are filled in
            if (GenericManager.isEmptyString(subproject.getName())) {
                addFieldError("error.subproject.name", getText("error.subproject.name"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            projects = ProjectManager.getAllProjects();
            factories = FactoryManager.getAllFactories();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }

    public String edit() {
        SubprojectManager.saveSubproject(subproject);
        addActionMessage(getText("message.subproject.updated_successfully"));
        return SUCCESS;
    }
    
    public void validateDoDelete() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!SubprojectManager.checkSubprojectExists(id)) {
                addActionError(getText("error.subproject.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.subproject.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.subproject.id"));
        }
        if (hasActionErrors()) {
            subprojects = SubprojectManager.getAllSubprojects();
        }
    }

    public String delete() {
        SubprojectManager.removeSubproject(id);
        addActionMessage(getText("message.subproject.deleted_successfully"));
        return SUCCESS;
    }
    
    public void validateDoGet() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!SubprojectManager.checkSubprojectExists(id)) {
                addActionError(getText("error.subproject.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.subproject.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.subproject.id"));
        }
        if (hasActionErrors()) {
            subprojects = SubprojectManager.getAllSubprojects();
        }
    }

    public String get() throws SubprojectNotFoundException {
        subproject = SubprojectManager.getSubproject(id);
        return SUCCESS;
    }

}
