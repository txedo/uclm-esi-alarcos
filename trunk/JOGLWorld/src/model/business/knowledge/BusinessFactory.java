package model.business.knowledge;

public class BusinessFactory {
	public static Company createCompany (String name, String information) {
		return new Company (name, information);
	}
	
	public static Factory createFactory (String name, String information, String director, String email, int employees, Address address) {
		return new Factory(name, information, director, email, employees, address);
	}
	
	public static Address createAddress (String street, String city, String state, String country, String zip) {
		return new Address(street, city, state, country, zip);
	}
	
}
