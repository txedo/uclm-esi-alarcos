package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.FileUtil;

public class CompanyAction extends ActionSupport implements
        GenericActionInterface {
    private static final long serialVersionUID = -8572805918582070731L;
    private static String DEFAULT_PIC = "images/anonymous.gif";
    private int id;
    // companyDao is set from applicationContext.xml
    private CompanyDAO companyDao;
    // Attributes required by Save, Delete and Edit action methods
    private Company company;
    // Attributes required by List action (default method execute)
    private List<Company> companies;
    // Required attributes to upload files
    private File upload; // The actual file
    private String uploadContentType; // The content type of the file
    private String uploadFileName; // The uploaded file name

    public void setCompanyDao(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

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
    public String execute() throws Exception {
        companies = companyDao.getAll();
        return SUCCESS;
    }

    public String showForm() throws Exception {
        // validateDoShowForm is not necessary since it would do the same as
        // this method
        String result = SUCCESS;
        // Get the company id attribute from URL
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getParameter("id") != null) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
                // If id <= 0, then ERROR
                if (id <= 0) {
                    addActionError(getText("error.company.id"));
                } else {
                    try {
                        // Check if the company id exists and place it in value
                        // stack
                        company = companyDao.getCompany(id);
                    } catch (CompanyNotFoundException e) {
                        addActionError(getText("error.company.id"));
                    }
                }
            } catch (NumberFormatException nfe) {
                addActionError(getText("error.company.id"));
            }
        } // Else, show a blank form
        if (hasActionErrors()) {
            result = ERROR;
            companies = companyDao.getAll();
        }
        return result;
    }

    public void validateDoSave() {
        if (company != null) {
            // Check company name is not empty
            if (company.getName().trim().length() == 0) {
                addFieldError("error.company.name",
                        getText("error.company.name"));
            } else {
                // Check company name is not already taken
                try {
                    companyDao.getCompany(company.getName());
                    addFieldError("error.company.name",
                            getText("error.company.already_exists"));
                } catch (CompanyNotFoundException e) {
                    // Name not taken. Nothing to do here
                }
            }
            // Director data
            if (company.getDirector().getName().trim().length() == 0) {
                addFieldError("error.director.name",
                        getText("error.director.name"));
            }
            if (company.getDirector().getLastName().trim().length() == 0) {
                addFieldError("error.director.last_name",
                        getText("error.director.last_name"));
            }
        } else {
            addActionError(getText("error.general"));
        }
    }

    public String save() {
        try {
            String path = DEFAULT_PIC;
            if (upload != null)
                path = FileUtil.uploadFile(uploadFileName, upload);
            company.getDirector().setImagePath(path);
            companyDao.saveCompany(company);
            addActionMessage(getText("message.company.added_successfully"));
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public void validateDoEdit() {
        Company cAux;
        if (company != null) {
            // Check if company id is valid
            if (company.getId() <= 0)
                addActionError(getText("error.company.id"));
            try {
                cAux = companyDao.getCompany(company.getId());
                // Check company name is not empty
                if (company.getName().trim().length() == 0) {
                    addFieldError("error.company.name",
                            getText("error.company.name"));
                } else {
                    // Check that there is no company with same name and
                    // different id
                    try {
                        cAux = companyDao.getCompany(company.getName());
                        if (cAux.getId() != company.getId()) {
                            addFieldError("error.company.name",
                                    getText("error.company.already_exists"));
                        }
                    } catch (CompanyNotFoundException e) {
                        // Name not taken. Nothing to do here.
                    }
                }
                // Director data
                if (company.getDirector().getName().trim().length() == 0) {
                    addFieldError("error.director.name",
                            getText("error.director.name"));
                }
                if (company.getDirector().getLastName().trim().length() == 0) {
                    addFieldError("error.director.last_name",
                            getText("error.director.last_name"));
                }
            } catch (CompanyNotFoundException e1) {
                addActionError(getText("error.company.id"));
            }
        } else {
            addActionError(getText("error.general"));
        }
        if (hasFieldErrors()) {
            addActionError(getText("error.required_fields"));
        }
    }

    public String edit() throws Exception {
        try {
            if (upload != null) {
                String path = FileUtil.uploadFile(uploadFileName, upload);
                company.getDirector().setImagePath(path);
            }
            companyDao.saveCompany(company);
            addActionMessage(getText("message.company.updated_successfully"));
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public void validateDoDelete() {
        try {
            int id = GenericManager.checkValidId(ServletActionContext.getRequest());
            CompanyManager.checkCompanyExists(id);
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (CompanyNotFoundException e) {
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
            int id = GenericManager.checkValidId(ServletActionContext.getRequest());
            company = CompanyManager.checkCompanyExists(id);
        } catch (NullIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (NotValidIdParameterException e1) {
            addActionError(getText("error.company.id"));
        } catch (CompanyNotFoundException e) {
            addActionError(getText("error.company.id"));
        }
        if (hasActionErrors()) {
            companies = CompanyManager.getAllCompanies();
        }
    }

    public String get() throws Exception {
        // Company was retrieved at validateDoGet but we want to be sure so...
        if (company == null) {
            company = CompanyManager.getCompany(id);
        }
        addActionMessage(getText("message.company.found"));

        return SUCCESS;
    }

}
