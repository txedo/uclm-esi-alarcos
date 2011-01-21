package model.business.knowledge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Factory {
	private int id;
	private Company company;
	private String name;
	private String information;
	private String director;
	private String email;
	private int employees;
	private Address address;
	private Set<Location> locations = new HashSet<Location>();
	
	public Factory() {}
	
	public Factory(String name, String information, String director,
			String email, int employees, Address address) {
		this.name = name;
		this.information = information;
		this.director = director;
		this.email = email;
		this.employees = employees;
		this.address = address;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void addLocation (Location loc) {
		locations.add(loc);
	}
	
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public Location getLocation(int mapId){
		boolean found = false;
		Location res = null;
		Iterator it = this.locations.iterator();
		while (!found && it.hasNext()) {
			Location aux = (Location)it.next();
			if (aux.getMap().getId() == mapId) {
				res = aux;
				found = true;
			}
		}
		return res;
	}
	
	public Set<Location> getLocations() {
		return locations;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInformation() {
		return information;
	}
	
	public void setInformation(String information) {
		this.information = information;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getEmployees() {
		return employees;
	}
	
	public void setEmployees(int employees) {
		this.employees = employees;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.company.toString());
		sb.append(" factory\n");
		sb.append("       " + name + "\n");
		sb.append("       " + information + "\n");
		sb.append("       " + director + " (" + email + ")\n");
		sb.append("       " + employees + "\n");
		sb.append("       " + address + "\n");
		sb.append("       locations\n");
		for (Iterator i = locations.iterator(); i.hasNext(); ) {
			Location loc = (Location)i.next();
			sb.append(loc.getXcoord() + ", " + loc.getYcoord() + "\n");
		}
		return sb.toString();
	}
}
