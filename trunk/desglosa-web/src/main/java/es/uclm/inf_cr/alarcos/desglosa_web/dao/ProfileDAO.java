package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.WordUtils;

import es.uclm.inf_cr.alarcos.desglosa_web.control.ProfileManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.DeleteProfileException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.XMLAgent;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile.ProfileFilter;

public class ProfileDAO {
    
    public Metaclass getProfile(String profileName) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        // Get profile folder full path
        String fullFileName = Utilities.getRealPathToWebApplicationContext(ProfileManager.PROFILE_FOLDER);
        // Return unmarshaled metaclass
        return XMLAgent.unmarshal(fullFileName + "\\" + profileName, Metaclass.class);
    }
    
    public Map<String, String> getProfiles(String entity) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        Map<String, String> profileNames = new HashMap<String, String>();
        // Create a filename filter
        FilenameFilter filter = new ProfileFilter(entity);
        // Get profile folder full path
        String fullFileName = Utilities.getRealPathToWebApplicationContext(ProfileManager.PROFILE_FOLDER);
        File directory = new File(fullFileName);
        // Get all filenames in directory that applies filename filter
        // restrictions
        String[] children = directory.list(filter);
        // Unmarshal metaclasses
        for (String fileName : children) {
            Metaclass mc = XMLAgent.unmarshal(fullFileName + "\\" + fileName, Metaclass.class);
            // Check that entity is ok
            String[] entityParts = mc.getEntityName().split("\\.");
            if (WordUtils.uncapitalize(entityParts[entityParts.length - 1]).equals(entity))
                profileNames.put(fileName, mc.getDescription());
        }
        return profileNames;
    }
    
    public List<Profile> getProfiles() throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        List<Profile> profiles = new ArrayList<Profile>();
        // Get profile folder full path
        String fullFileName = Utilities.getRealPathToWebApplicationContext(ProfileManager.PROFILE_FOLDER);
        File directory = new File(fullFileName);
        // Get all filenames in directory that applies filename filter
        // restrictions
        String[] children = directory.list();
        // Unmarshal metaclasses
        for (String fileName : children) {
            Metaclass mc = XMLAgent.unmarshal(fullFileName + "\\" + fileName, Metaclass.class);
            profiles.add(new Profile(fileName, mc));
        }
        return profiles;
    }

    public void removeProfile(String filename) throws DeleteProfileException {
        String path = Utilities.getRealPathToWebApplicationContext(ProfileManager.PROFILE_FOLDER);
        String fullPath = path + "\\" + filename;
        File file = new File(fullPath);
        if (!file.delete()) {
            throw new DeleteProfileException();
        }
    }
    

}
