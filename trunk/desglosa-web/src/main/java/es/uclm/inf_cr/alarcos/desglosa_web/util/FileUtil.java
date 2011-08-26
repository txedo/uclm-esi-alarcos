package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoader;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;

public class FileUtil {
	private static final String UPLOAD_FOLDER = "upload";
	private static final String PROFILE_FOLDER = "profiles";
	
	public static String uploadFile (String fileName, File upload) throws IOException {
		String fullFileName = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(UPLOAD_FOLDER);
		File theFile = new File(fullFileName + "\\" + fileName);
		FileUtils.copyFile(upload, theFile);
		return UPLOAD_FOLDER + "/" + fileName;
	}
	
	public static Metaclass getProfile (String profileName) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		// Get profile folder full path
		String fullFileName = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(PROFILE_FOLDER);
		// Return unmarshaled metaclass
		return XMLAgent.unmarshal(fullFileName  + "\\" + profileName, Metaclass.class);
	}
	
	public static Map<String,String> getProfiles (String entity) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		Map<String,String> profileNames = new HashMap<String,String>();
		// Create a filename filter
		FilenameFilter filter = new ProfileFilter(entity);
		// Get profile folder full path
		String fullFileName = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(PROFILE_FOLDER);
		File directory = new File(fullFileName);
		// Get all filenames in directory that applies filename filter restrictions
		String[] children = directory.list(filter);
		// Unmarshal metaclasses
		for (String fileName : children) {
			Metaclass mc = XMLAgent.unmarshal(fullFileName  + "\\" + fileName, Metaclass.class);
			// Check that entity is ok
			if (mc.getEntityName().equals(entity)) profileNames.put(mc.getName(), mc.getDescription());
		}
		return profileNames;
	}
	
	private static class ProfileFilter implements FilenameFilter {
		private String entity;
		
		public ProfileFilter (String entity) {
			this.entity = entity;
		}

		public boolean accept(File dir, String name) {
			return name.startsWith(this.entity + "-") && name.endsWith(".xml");
		}
	}
}
