package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
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
    private static String DEFAULT_PIC = "images/anonymous.gif";
    private int id;
    // factoryDao is set from applicationContext.xml
    private FactoryDAO factoryDao;
    private CompanyDAO companyDao;
    // Required attributes by List Action
    private List<Factory> factories;
    private List<Company> companies;
    // Required attributes by Save Action
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

    public void setFactoryDao(FactoryDAO factoryDao) {
        this.factoryDao = factoryDao;
    }

    public void setCompanyDao(CompanyDAO companyDao) {
        this.companyDao = companyDao;
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
        factories = factoryDao.getAll();
        return SUCCESS;
    }

    public String showForm() throws Exception {
        String result = SUCCESS;
        // Get all the companies
        companies = companyDao.getAll();
        // Get the factory id attribute from URL
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            // If id <= 0, then ERROR
            if (id <= 0) {
                addActionError(getText("error.factory.id"));
                result = ERROR;
            } else {
                try {
                    // Check if the factory id exists and place it in value
                    // stack
                    factory = factoryDao.getFactory(id);
                } catch (FactoryNotFoundException e) {
                    addActionError(getText("error.factory.id"));
                    result = ERROR;
                }
            }
        } // Else, show a blank form
        return result;
    }

    public void validateDoSave() {
        if (factory != null) {
            try {
                // Check that the company ID exists
                Company c;
                if (factory.getCompany() != null) {
                    c = companyDao.getCompany(factory.getCompany().getId());
                    // If it exists, set it to factory
                    factory.setCompany(c);
                } else {
                    addFieldError("error.company_mandatory",
                            getText("error.company.is_mandatory"));
                }
                // Check that factory name is already taken
                factoryDao.getFactory(factory.getName());
                addFieldError("error.factory.name",
                        getText("error.factory.already_exists"));
                // If factory name is available, then throw and catch
                // FactoryNotFoundException
            } catch (CompanyNotFoundException e) {
                addActionError(getText("error.company.id"));
            } catch (FactoryNotFoundException e) {
                // Name not taken. Nothing to do here.
            }
            // Check that required fields are filled in
            // Factory data
            if (factory.getName().trim().length() == 0) {
                addFieldError("error.factory.name",
                        getText("error.factory.name"));
            }
            // Director data
            if (factory.getDirector().getName().trim().length() == 0) {
                addFieldError("error.director.name",
                        getText("error.director.name"));
            }
            if (factory.getDirector().getLastName().trim().length() == 0) {
                addFieldError("error.director.lastName",
                        getText("error.director.last_name"));
            }
            // Address data
            if (factory.getAddress().getAddress().trim().length() == 0) {
                addFieldError("error.factory.address.address",
                        getText("error.address.address"));
            }
            if (factory.getAddress().getCity().trim().length() == 0) {
                addFieldError("error.factory.address.city",
                        getText("error.address.city"));
            }
            if (factory.getAddress().getCountry().trim().length() == 0) {
                addFieldError("error.factory.address.country",
                        getText("error.address.country"));
            }
            // Location data
            if (factory.getLocation().getLatitude() == 0.0f
                    || factory.getLocation().getLongitude() == 0.0f) {
                addFieldError("error.factory.location",
                        getText("error.location"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            companies = companyDao.getAll();
        }
        if (hasFieldErrors()) {
            addFieldError("error.mandatory_fields",
                    getText("error.mandatory_fields"));
        }
    }

    public String save() {
        try {
            String path = DEFAULT_PIC;
            if (upload != null) {
                path = FileUtil.uploadFile(uploadFileName, upload);
            }
            factory.getDirector().setImagePath(path);
            factoryDao.saveFactory(factory);
            addActionMessage(getText("message.factory.added_successfully"));
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public void validateDoEdit() {
        Factory fAux;
        if (factory != null) {
            // Check if factory ID is valid
            try {
                if (factory.getId() <= 0) {
                    addActionError(getText("error.factory.id"));
                }
                fAux = factoryDao.getFactory(factory.getId());
            } catch (FactoryNotFoundException e) {
                addActionError(getText("error.factory.id"));
            }
            try {
                // Check that the company ID exists
                Company c;
                if (factory.getCompany() != null) {
                    c = companyDao.getCompany(factory.getCompany().getId());
                    // If it exists, set it to factory
                    factory.setCompany(c);
                } else {
                    addFieldError("error.company_mandatory",
                            getText("error.company.is_mandatory"));
                }
                // Check that there is no company with same name and different
                // id
                fAux = factoryDao.getFactory(factory.getName());
                if (fAux.getId() != factory.getId()) {
                    addFieldError("error.factory.name",
                            getText("error.factory.already_exists"));
                }
                // If factory name is available, then throw and catch
                // FactoryNotFoundException
            } catch (CompanyNotFoundException e) {
                addActionError(getText("error.company.id"));
            } catch (FactoryNotFoundException e) {
                // Name not taken. Nothing to do here.
            }
            // Check that required fields are filled in
            // Factory data
            if (factory.getName().trim().length() == 0) {
                addFieldError("error.factory.name",
                        getText("error.factory.name"));
            }
            // Director data
            if (factory.getDirector().getName().trim().length() == 0) {
                addFieldError("error.director.name",
                        getText("error.director.name"));
            }
            if (factory.getDirector().getLastName().trim().length() == 0) {
                addFieldError("error.director.lastName",
                        getText("error.director.last_name"));
            }
            // Address data
            if (factory.getAddress().getAddress().trim().length() == 0) {
                addFieldError("error.factory.address.address",
                        getText("error.address.address"));
            }
            if (factory.getAddress().getCity().trim().length() == 0) {
                addFieldError("error.factory.address.city",
                        getText("error.address.city"));
            }
            if (factory.getAddress().getCountry().trim().length() == 0) {
                addFieldError("error.factory.address.country",
                        getText("error.address.country"));
            }
            // Location data
            if (factory.getLocation().getLatitude() == 0.0f
                    || factory.getLocation().getLongitude() == 0.0f) {
                addFieldError("error.factory.location",
                        getText("error.location"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasActionErrors() || hasErrors() || hasFieldErrors()) {
            companies = companyDao.getAll();
        }
        if (hasFieldErrors()) {
            addFieldError("error.mandatory_fields",
                    getText("error.mandatory_fields"));
        }
    }

    public String edit() {
        try {
            if (upload != null) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                factory.getDirector().setImagePath(path);
            }
            factoryDao.saveFactory(factory);
            addActionMessage(getText("message.factory.updated_successfully"));
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public void validateDoDelete() {
        // Get the company id attribute from URL
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getParameter("id") != null) {
            id = Integer.parseInt(request.getParameter("id"));
            // If id <= 0, then ERROR
            if (id <= 0) {
                addActionError(getText("error.factory.id"));
            } else {
                try {
                    // Check if the factory id exists
                    factoryDao.getFactory(id);
                } catch (FactoryNotFoundException e) {
                    addActionError(getText("error.factory.id"));
                }
            }
        } else {
            addActionError(getText("error.factory.id"));
        }
        if (hasActionErrors()) {
            factories = factoryDao.getAll();
        }
    }

    public String delete() {
        factoryDao.removeFactory(id);
        addActionMessage(getText("message.factory.deleted_successfully"));

        return SUCCESS;
    }

}
