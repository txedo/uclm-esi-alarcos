package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.io.File;
import java.io.FilenameFilter;

public class Profile {
    private String filename;
    private Metaclass profile;

    public Profile(String filename, Metaclass profile) {
        super();
        this.filename = filename;
        this.profile = profile;
    }

    public String getFilename() {
        return filename;
    }

    public Metaclass getProfile() {
        return profile;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setProfile(Metaclass profile) {
        this.profile = profile;
    }
    
    public static class ProfileFilter implements FilenameFilter {
        private String entity;

        public ProfileFilter(String entity) {
            this.entity = entity;
        }

        public boolean accept(File dir, String name) {
            return name.startsWith(this.entity + "-") && name.endsWith(".xml");
        }
    }

}
