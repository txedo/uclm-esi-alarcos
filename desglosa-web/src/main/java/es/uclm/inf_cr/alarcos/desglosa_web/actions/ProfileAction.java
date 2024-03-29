package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.ProfileManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.DeleteProfileException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;

public class ProfileAction extends ActionSupport implements
        GenericActionInterface {
    static final long serialVersionUID = 6496919542324618999L;
    private List<Profile> profiles;
    private Map<String, String> profileNames;
    private String filename;
    private Metaclass profile;
    private String entity;
    private String model;
    private String profileName;
    private String profileDescription;
    private String jsonMappings;
    private String jsonConstants;
    private String jsonCaptionLines;
    private List<PropertyWrapper> entityAttributes;
    private Map<String, String> modelAttributes;
    private final Map<String, String> entities = new HashMap<String, String>() {
        private static final long serialVersionUID = 1443703980383438531L;
        {
            put("es.uclm.inf_cr.alarcos.desglosa_web.model.Company", getText("label.Company"));
            put("es.uclm.inf_cr.alarcos.desglosa_web.model.Factory", getText("label.Factory"));
            put("es.uclm.inf_cr.alarcos.desglosa_web.model.Project", getText("label.Project"));
            put("es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject", getText("label.Subproject"));
            put("es.uclm.inf_cr.alarcos.desglosa_web.model.Market", getText("label.Market"));
        }
    };
    private final Map<String, String> models = new HashMap<String, String>() {
        private static final long serialVersionUID = 6918186658085961722L;
        {
            put("model.gl.knowledge.GLTower", getText("label.model.towers"));
            put("model.gl.knowledge.GLAntennaBall", getText("label.model.antennaballs"));
            put("model.gl.knowledge.GLFactory", getText("label.model.buildings"));
        }
    };
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setJsonMappings(String jsonMappings) {
        this.jsonMappings = jsonMappings;
    }

    public void setJsonConstants(String jsonConstants) {
        this.jsonConstants = jsonConstants;
    }

    public void setJsonCaptionLines(String jsonCaptionLines) {
        this.jsonCaptionLines = jsonCaptionLines;
    }

    public Map<String, String> getProfileNames() {
        return profileNames;
    }

    public Metaclass getProfile() {
        return profile;
    }

    public String getEntity() {
        return entity;
    }

    public String getModel() {
        return model;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public List<Profile> getProfiles() {
        return profiles;
    }

    public List<PropertyWrapper> getEntityAttributes() {
        return entityAttributes;
    }

    public Map<String, String> getModelAttributes() {
        return modelAttributes;
    }

    public Map<String, String> getEntities() {
        return entities;
    }

    public Map<String, String> getModels() {
        return models;
    }

    @Override
    public String execute() throws Exception {
        profiles = ProfileManager.getProfiles();
        return SUCCESS;
    }

    public String showForm() throws Exception {
        return SUCCESS;
    }

    public String loadEntityAttributes() {
        String result = ERROR;
        if (entity != null) {
            try {
                entityAttributes = ProfileManager.readEntityAttributes(entity);
                result = SUCCESS;
            } catch (ClassNotFoundException e) {
                addActionError(getText("exception.class_not_found"));
            } catch (Exception e) {
                addActionError(getText("exception.generic"));
            }
        }
        return result;
    }

    public String loadModelAttributes() {
        String result = ERROR;
        if (model != null) {
            try {
                modelAttributes = ProfileManager.readModelDimensions(model);
                result = SUCCESS;
            } catch (ClassNotFoundException e) {
                addActionError(getText("exception.class_not_found"));
            } catch (Exception e) {
                addActionError(getText("exception.generic"));
            }
        }
        return result;
    }

    public void validateDoSave() {
        if (Utilities.isEmptyString(profileName)) {
            addFieldError("error.profile.name", "error.profile.name");
        }
        if (Utilities.isEmptyString(profileDescription)) {
            addFieldError("error.profile.description", "error.profile.description");
        }
        if (Utilities.isEmptyString(model) || Utilities.isEmptyString(entity)) {
            addActionError(getText("error.entity_model_required_fields"));
        }
        if (Utilities.getJsonArraySize(jsonMappings) == 0) {
            addFieldError("error.nomappings", "error.nomappings");
        }
    }

    public String save() {
        String result = SUCCESS;
        try {
            Metaclass metaclass = ProfileManager.buildProfile(profileName, profileDescription, entity, model);
            ProfileManager.addMappings(metaclass, jsonMappings);
            ProfileManager.addConstants(metaclass, jsonConstants);
            ProfileManager.addCaption(metaclass, jsonCaptionLines);
            ProfileManager.saveProfile(metaclass, entity, profileName.trim());
            addActionMessage(getText("message.profile.created_successfully"));
        } catch (JAXBException e) {
            addActionError(getText("exception.jaxb"));
            result = ERROR;
        }
        return result;
    }

    public String edit() throws Exception {
        return null;
    }

    public String delete() {
        String result = SUCCESS;
        try {
            ProfileManager.removeProfile(filename);
            addActionMessage(getText("message.profile.delete_successfully"));
        } catch (DeleteProfileException e) {
            addActionError(getText("error.profile.notdeleted"));
            result = ERROR;
            try {
                profiles = ProfileManager.getProfiles();
            } catch (JAXBException e1) {
                addActionError(getText("exception.jaxb"));
            } catch (IOException e1) {
                addActionError(getText("exception.io"));
            } catch (InstantiationException e1) {
                addActionError(getText("exception.instantiation"));
            } catch (IllegalAccessException e1) {
                addActionError(getText("exception.illegal_access"));
            }
        }
        return result;
    }

    public String get() {
        String result = ERROR;
        try {
        // entity o profileName
            if (entity != null) {
                profileNames = ProfileManager.getProfilesForEntity(entity);
                result = SUCCESS;
            } else if (profileName != null) {
                profile = ProfileManager.getProfileByName(profileName);
                result = SUCCESS;
            }
        } catch (JAXBException e) {
            addActionError(getText("exception.jaxb"));
        } catch (IOException e) {
            addActionError(getText("exception.io"));
        } catch (InstantiationException e) {
            addActionError(getText("exception.instantiation"));
        } catch (IllegalAccessException e) {
            addActionError(getText("exception.illegal_access"));
        }
        return result;
    }

}
