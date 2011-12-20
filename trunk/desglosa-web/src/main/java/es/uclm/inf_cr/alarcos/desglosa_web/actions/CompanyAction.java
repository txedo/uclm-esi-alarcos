package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.FileUtil;

public class CompanyAction extends ActionSupport implements
        GenericActionInterface {
    private static final long serialVersionUID = -8572805918582070731L;
    private int id;
    // Attributes required by Save, Delete and Edit action methods
    private Company company;
    // Attributes required by List action (default method execute)
    private List<Company> companies;
    // Required attributes to upload files
    private File upload; // The actual file
    private String uploadContentType; // The content type of the file
    private String uploadFileName; // The uploaded file name

    public int getId() {
        return id;
    }

    public Company getCompany() {
        return company;
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

    public List<Company> getCompanies() {
        return companies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(Company company) {
        this.company = company;
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
    public String execute() {
        companies = CompanyManager.getAllCompanies();
        return SUCCESS;
    }
    
    public void validateDoShowForm() {
        try {
            // Check id is valid
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            // Check there a company that exists with that id
            if (!CompanyManager.checkCompanyExists(id)) {
                addActionError(getText("error.company.id"));
            }
        } catch (NullIdParameterException e) {
            // This is expected. So show blank form
        } catch (NotValidIdParameterException e) {
            addActionError(getText("error.company.id"));
        }
        if (hasActionErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
    }

    public String showForm() throws CompanyNotFoundException {
        // if id == 0 then show a blank form
        if (id > 0) {
            company = CompanyManager.getCompany(id);
        }
        return SUCCESS;
    }

    public void validateDoSave() {
        if (company != null) {
            // Check company name is not empty
            if (GenericManager.isEmptyString(company.getName())) {
                addFieldError("error.company.name", getText("error.company.name"));
            } else {
                // Check if company name is available
                if (CompanyManager.checkCompanyExists(company.getName())) {
                    addFieldError("error.company.name", getText("error.company.already_exists"));
                }
            }
            // Director data
            if (GenericManager.isEmptyString(company.getDirector().getName())) {
                addFieldError("error.director.name", getText("error.director.name"));
            }
            if (GenericManager.isEmptyString(company.getDirector().getLastName())) {
                addFieldError("error.director.last_name", getText("error.director.last_name"));
            }
        } else {
            addActionError(getText("error.general"));
        }
    }

    public String save() {
        try {
            if (upload != null && !GenericManager.isEmptyString(uploadFileName)) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                company.getDirector().setImagePath(path);
            }
            CompanyManager.saveCompany(company);
            addActionMessage(getText("message.company.added_successfully"));
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public void validateDoEdit() {
        Company cAux;
        if (company != null) {
            try {
                // Check if company id is valid
                GenericManager.checkValidId(company.getId());
                cAux = CompanyManager.getCompany(company.getId());
                // Check company name is not empty
                if (GenericManager.isEmptyString(company.getName())) {
                    addFieldError("error.company.name", getText("error.company.name"));
                } else {
                    try {
                        // Check that there is no company with same name and different id
                        cAux = CompanyManager.getCompany(company.getName());
                        if (cAux.getId() != company.getId()) {
                            addFieldError("error.company.name", getText("error.company.already_exists"));
                        }
                    } catch (CompanyNotFoundException e) {
                        // Name not taken. Nothing to do here.
                    }
                }
                // Director data
                if (GenericManager.isEmptyString(company.getDirector().getName())) {
                    addFieldError("error.director.name", getText("error.director.name"));
                }
                if (GenericManager.isEmptyString(company.getDirector().getLastName())) {
                    addFieldError("error.director.last_name", getText("error.director.last_name"));
                }
            } catch (NotValidIdParameterException e) {
                addActionError(getText("error.company.id"));
            } catch (CompanyNotFoundException e) {
                addActionError(getText("error.company.id"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasFieldErrors()) {
            addActionError(getText("error.required_fields"));
        }
    }

    public String edit() {
        try {
            if (upload != null) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                company.getDirector().setImagePath(path);
            }
            CompanyManager.saveCompany(company);
            addActionMessage(getText("message.company.updated_successfully"));
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public void validateDoDelete() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!CompanyManager.checkCompanyExists(id)) {
                addActionError(getText("error.company.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.company.id"));
        }
        if (hasActionErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
    }

    public String delete() {
        CompanyManager.removeCompany(id);
        addActionMessage(getText("message.company.deleted_successfully"));

        return SUCCESS;
    }

    public void validateDoGet() {
        try {
            id = GenericManager.checkValidId(ServletActionContext.getRequest().getParameter("id"));
            if (!CompanyManager.checkCompanyExists(id)) {
                addActionError(getText("error.company.id"));
            }
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.company.id"));
        }
        if (hasActionErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
    }

    public String get() throws Exception {
        company = CompanyManager.getCompany(id);
        return SUCCESS;
    }

}
