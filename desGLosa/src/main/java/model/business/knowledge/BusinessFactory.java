package model.business.knowledge;

import java.io.IOException;
import java.util.regex.Pattern;

import persistence.ResourceRetriever;

public class BusinessFactory {
	public static Company createCompany (String name, String information) {
		return new Company (name, information);
	}
	
	public static Factory createFactory (String name, String information, Director director, String email, int employees, Address address) {
		return new Factory(name, information, director, email, employees, address);
	}
	
	public static Director createDirector (String name, String firstSurname, String secondSurname, String pathToImage) throws IOException {
		Director result = new Director();
		result.setName(name);
		result.setFirstSurname(firstSurname);
		result.setSecondSurname(secondSurname);
		result.setPicture(createImage(pathToImage));
		return result;
	}
	
	public static Image createImage (String path) throws IOException {
		Image result = null;
		boolean matches = false;
		String fileSeparator = "\\";
		String extensions = ".(bmp|gif|jpg|jpeg|png)";
		Pattern winPattern = Pattern.compile("([a-zA-Z]:(\\\\|/))?(([a-zA-Z0-9]+(\\\\|/))*[a-zA-Z0-9]+)?"+extensions);
		Pattern unixPattern = Pattern.compile("(/)?(([a-zA-Z0-9]+/)*[a-zA-Z0-9]+)?"+extensions);
		if (ResourceRetriever.isResourceAvailable(path)) {
			if (unixPattern.matcher(path).matches()) {
				fileSeparator = "/";
				matches = true;
			} else if (winPattern.matcher(path).matches()) {
				fileSeparator = "\\";
				matches = true;
			}
			if (matches) {
				String [] parts = path.split(fileSeparator);
				String file = new String((String)parts[parts.length-1]);
				result = new Image(file.split("\\.")[0], "image/"+file.split("\\.")[1]);
				result.setData(ResourceRetriever.getResourceAsByteArray(path));
			}
		}

		return result;
	}
	
	public static Address createAddress (String street, String city, String state, String country, String zip) {
		return new Address(street, city, state, country, zip);
	}
	
}
