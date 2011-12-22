package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;

public class ProjectAction extends ActionSupport implements
        GenericActionInterface {
    private static final long serialVersionUID = -8863064072322275286L;
    private int id;
    private List<Project> projects;
    private List<Factory> factories;
    private Project project;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * @return the factories
     */
    public List<Factory> getFactories() {
        return factories;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String execute() {
        projects = ProjectManager.getAllProjects();
        return SUCCESS;
    }
    
    public void validateDoShowForm() {
        try {
            // Check if id is valid
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            // Check that there is a project with such id
            if (!ProjectManager.checkProjectExists(id)) {
                addActionError(getText("error.project.id"));
            }
        } catch (NullIdParameterException e) {
            // This is expected. So show blank form
        } catch (NotValidIdParameterException e) {
            addActionError(getText("error.project.id"));
        }
        if (hasActionErrors()) {
            projects = ProjectManager.getAllProjects();
        }
    }
    
    public String showForm() throws Exception {
        // Get all factories
        factories = FactoryManager.getAllFactories();
        // if id == 0 then show a blank form
        if (id > 0) {
            project = ProjectManager.getProject(id);
        }
        return SUCCESS;
    }
    
    public void validateDoSave() {
        if (project != null) {
            // Check that the factory ID exits
            if (project.getMainFactory() != null) {
                // If it exists, set it to the project main factory
                try {
                    project.setMainFactory(FactoryManager.getFactory(project.getMainFactory().getId()));
                } catch (FactoryNotFoundException e) {
                    addActionError(getText("error.factory.id"));
                    addFieldError("error.factory_required", getText("error.factory.required"));
                }
            } else {
                addActionError(getText("error.factory.id"));
                addFieldError("error.factory_required", getText("error.factory.required"));
            }
            // Check that required fields are filled in
            if (GenericManager.isEmptyString(project.getName())) {
                addFieldError("error.project.name", getText("error.project.name"));
            }
            if (GenericManager.isEmptyString(project.getCode())) {
                addFieldError("error.project.code", getText("error.project.code"));
            }
            if (GenericManager.isEmptyString(project.getPlan())) {
                addFieldError("error.project.plan", getText("error.project.plan"));
            }
            try {
                GenericManager.checkValidId(project.getMarket().getId());
            } catch (NotValidIdParameterException e) {
                addFieldError("error.project.market", getText("error.project.market"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            factories = FactoryManager.getAllFactories();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }
    
    public String save() {
        ProjectManager.saveProject(project);
        addActionMessage(getText("message.project.added_successfully"));
        return SUCCESS;
    }
    
    public void validateDoEdit() {
        if (project != null) {
            try {
                // Check that project ID is valid
                GenericManager.checkValidId(project.getId());
                // Check that the project exists
                ProjectManager.getProject(project.getId());
                // Check that the factory ID exits
                if (project.getMainFactory() != null) {
                    // If it exists, set it to the project main factory
                    project.setMainFactory(FactoryManager.getFactory(project.getMainFactory().getId()));
                } else {
                    addActionError(getText("error.factory.id"));
                }
            } catch (FactoryNotFoundException e) {
                addActionError(getText("error.factory.id"));
                addFieldError("error.factory_required", getText("error.factory.required"));
            } catch (ProjectNotFoundException e) {
                addActionError(getText("error.project.id"));
            } catch (NotValidIdParameterException e) {
                addActionError(getText("error.project.id"));
            }
            // Check that required fields are filled in
            if (GenericManager.isEmptyString(project.getName())) {
                addFieldError("error.project.name", getText("error.project.name"));
            }
            if (GenericManager.isEmptyString(project.getCode())) {
                addFieldError("error.project.code", getText("error.project.code"));
            }
            if (GenericManager.isEmptyString(project.getPlan())) {
                addFieldError("error.project.plan", getText("error.project.plan"));
            }
            try {
                GenericManager.checkValidId(project.getMarket().getId());
            } catch (NotValidIdParameterException e) {
                addFieldError("error.project.market", getText("error.project.market"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            factories = FactoryManager.getAllFactories();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }

    public String edit() {
        ProjectManager.saveProject(project);
        addActionMessage(getText("message.project.updated_successfully"));
        return SUCCESS;
    }
    
    public void validateDoDelete() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!ProjectManager.checkProjectExists(id)) {
                addActionError(getText("error.project.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.project.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.project.id"));
        }
        if (hasActionErrors()) {
            projects = ProjectManager.getAllProjects();
        }
    }

    public String delete() {
        ProjectManager.removeProject(id);
        addActionMessage(getText("message.project.deleted_successfully"));
        return SUCCESS;
    }
    
    public void validateDoGet() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!ProjectManager.checkProjectExists(id)) {
                addActionError(getText("error.project.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.project.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.project.id"));
        }
        if (hasActionErrors()) {
            projects = ProjectManager.getAllProjects();
        }
    }

    public String get() throws ProjectNotFoundException {
        project = ProjectManager.getProject(id);
        return SUCCESS;
    }

}
