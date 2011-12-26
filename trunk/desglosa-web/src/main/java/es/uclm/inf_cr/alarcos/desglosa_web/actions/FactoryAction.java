package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Director;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.FileUtil;

/* Experience shows that chaining should be used with care. If chaining is overused, 
 * an application can turn into "spaghetti code". Actions should be treated as a 
 * Transaction Script, rather than as methods in a Business Facade. Be sure to ask yourself 
 * why you need to chain from one Action to another. Is a navigational issue, or could 
 * the logic in Action2 be pushed back to a support class or business facade so that Action1 
 * can call it too?
 * 
 * Ideally, Action classes should be as short as possible. All the core logic should be pushed 
 * back to a support class or a business facade, so that Actions only call methods. Actions 
 * are best used as adapters, rather than as a class where coding logic is defined.
 */
public class FactoryAction extends ActionSupport {
    private static final long serialVersionUID = -6215763127414281847L;
    private int id;
    private List<Factory> factories;
    private List<Company> companies;
    private List<Subproject> subprojects;
    private Factory factory;
    // Required attributes to upload files
    private File upload;// The actual file
    private String uploadContentType; // The content type of the file
    private String uploadFileName; // The uploaded file name

    public int getId() {
        return id;
    }

    public Factory getFactory() {
        return factory;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public List<Company> getCompanies() {
        return companies;
    }
    
    public List<Subproject> getSubprojects() {
        return subprojects;
    }

    public File getUpload() {
        return upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    @Override
    public String execute() throws Exception {
        factories = FactoryManager.getAllFactories();
        return SUCCESS;
    }
    
    public void validateDoShowForm() {
        try {
            // Check if id is valid
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            // Check if there is a factory that exists with that id
            if (!FactoryManager.checkFactoryExists(id)) {
                addActionError(getText("error.factory.id"));
            }
        } catch (NullIdParameterException e) {
            // This is expected. So show blank form
        } catch (NotValidIdParameterException e) {
            addActionError(getText("error.factory.id"));
        }
        if (hasActionErrors()) {
            factories = FactoryManager.getAllFactories();
        }
    }

    public String showForm() throws Exception {
        // Get all the companies
        companies = CompanyManager.getAllCompanies();
        // if id == 0 then show a blank form
        if (id > 0) {
            factory = FactoryManager.getFactory(id);
        }
        return SUCCESS;
    }

    public void validateDoSave() {
        if (factory != null) {
            // Check that the company ID exists
            if (factory.getCompany() != null) {
                // If it exists, set it to factory
                try {
                    factory.setCompany(CompanyManager.getCompany(factory.getCompany().getId()));
                } catch (CompanyNotFoundException e) {
                    addActionError(getText("error.company.id"));
                }
                // Check if factory name is available
                if (FactoryManager.checkFactoryExists(factory.getName())) {
                    addFieldError("error.factory.name", getText("error.factory.already_exists"));
                }
            } else {
                addFieldError("error.company_mandatory", getText("error.company.is_mandatory"));
            }
            // Check that required fields are filled in
            // Factory data
            if (GenericManager.isEmptyString(factory.getName())) {
                addFieldError("error.factory.name", getText("error.factory.name"));
            }
            // Director data
            if (GenericManager.isEmptyString(factory.getDirector().getName())) {
                addFieldError("error.director.name", getText("error.director.name"));
            }
            if (GenericManager.isEmptyString(factory.getDirector().getLastName())) {
                addFieldError("error.director.lastName", getText("error.director.last_name"));
            }
            // Address data
            if (GenericManager.isEmptyString(factory.getAddress().getAddress())) {
                addFieldError("error.factory.address.address", getText("error.address.address"));
            }
            if (GenericManager.isEmptyString(factory.getAddress().getCity())) {
                addFieldError("error.factory.address.city", getText("error.address.city"));
            }
            if (GenericManager.isEmptyString(factory.getAddress().getCountry())) {
                addFieldError("error.factory.address.country", getText("error.address.country"));
            }
            // Location data
            if (factory.getLocation().getLatitude() == null || factory.getLocation().getLatitude().equals("") ||
                factory.getLocation().getLongitude() == null || factory.getLocation().getLongitude().equals("")) {
                addFieldError("error.factory.location", getText("error.location"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }

    public String save() {
        try {
            if (upload != null) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                factory.getDirector().setImagePath(path);
            } else {
                factory.getDirector().setImagePath(Director.DEFAULT_PIC);
            }
            FactoryManager.saveFactory(factory);
            addActionMessage(getText("message.factory.added_successfully"));
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public void validateDoEdit() {
        Factory fAux;
        if (factory != null) {
            try {
                // Check if factory ID is valid
                GenericManager.checkValidId(factory.getId());
                fAux = FactoryManager.getFactory(factory.getId());
                // Check that the company ID exists
                if (factory.getCompany() != null) {
                    // If it exists, set it to factory
                    factory.setCompany(CompanyManager.getCompany(factory.getCompany().getId()));
                } else {
                    addFieldError("error.company_mandatory", getText("error.company.is_mandatory"));
                }
                // Check factory name is not empty
                if (GenericManager.isEmptyString(factory.getName())) {
                    addFieldError("error.factory.name", getText("error.factory.name"));
                } else {
                    try {
                        // Check that there is no factory with same name and different id
                        fAux = FactoryManager.getFactory(factory.getName());
                        if (fAux.getId() != factory.getId()) {
                            addFieldError("error.factory.name", getText("error.factory.already_exists"));
                        }
                    } catch (FactoryNotFoundException e) {
                        // Name not taken. Nothing to do here.
                    }
                }
            } catch (CompanyNotFoundException e) {
                addActionError(getText("error.company.id"));
            } catch (FactoryNotFoundException e) {
                addActionError(getText("error.factory.id"));
            } catch (NotValidIdParameterException e) {
                addActionError(getText("error.factory.id"));
            }
            // Check that required fields are filled in
            // Factory data
            if (GenericManager.isEmptyString(factory.getName())) {
                addFieldError("error.factory.name", getText("error.factory.name"));
            }
            if (GenericManager.isEmptyString(factory.getEmail())) {
                addFieldError("error.factory.email", getText("error.factory.email"));
            }
            if (factory.getEmployees() < 0) {
                addFieldError("error.factory.employees", getText("error.factory.employees"));
            }
            // Director data
            if (GenericManager.isEmptyString(factory.getDirector().getName())) {
                addFieldError("error.director.name", getText("error.director.name"));
            }
            if (GenericManager.isEmptyString(factory.getDirector().getLastName())) {
                addFieldError("error.director.lastName", getText("error.director.last_name"));
            }
            // Address data
            if (GenericManager.isEmptyString(factory.getAddress().getAddress())) {
                addFieldError("error.factory.address.address", getText("error.address.address"));
            }
            if (GenericManager.isEmptyString(factory.getAddress().getCity())) {
                addFieldError("error.factory.address.city", getText("error.address.city"));
            }
            if (GenericManager.isEmptyString(factory.getAddress().getCountry())) {
                addFieldError("error.factory.address.country", getText("error.address.country"));
            }
            // Location data
            if (factory.getLocation().getLatitude() == null || factory.getLocation().getLatitude().equals("") ||
                factory.getLocation().getLongitude() == null || factory.getLocation().getLongitude().equals("")) {
                addFieldError("error.factory.location", getText("error.location"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
        if (hasFieldErrors()) {
            addFieldError("error.required_fields", getText("error.required_fields"));
        }
    }

    public String edit() {
        try {
            if (upload != null) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                factory.getDirector().setImagePath(path);
            } else {
                factory.getDirector().setImagePath(Director.DEFAULT_PIC);
            }
            FactoryManager.saveFactory(factory);
            addActionMessage(getText("message.factory.updated_successfully"));
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public void validateDoDelete() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!FactoryManager.checkFactoryExists(id)) {
                addActionError(getText("error.factory.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.factory.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.factory.id"));
        }
        if (hasActionErrors()) {
            factories = FactoryManager.getAllFactories();
        }
    }

    public String delete() {
        FactoryManager.removeFactory(id);
        addActionMessage(getText("message.factory.deleted_successfully"));
        return SUCCESS;
    }
    
    public void validateDoGet() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!FactoryManager.checkFactoryExists(id)) {
                addActionError(getText("error.factory.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.factory.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.factory.id"));
        }
        if (hasActionErrors()) {
            factories = FactoryManager.getAllFactories();
        }
    }

    public String get() throws Exception {
        factory = FactoryManager.getFactory(id);
        return SUCCESS;
    }

}
