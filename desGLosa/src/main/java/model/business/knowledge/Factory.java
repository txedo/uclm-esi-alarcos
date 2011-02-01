package model.business.knowledge;


public class Factory {
	private int id;
	private Company company;
	private String name;
	private String information;
	private Director director;
	private String email;
	private int employees;
	private Address address;
	
	public Factory() {}
	
	public Factory(String name, String information, Director director,
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
	
	public Director getDirector() {
		return director;
	}
	
	public void setDirector(Director director) {
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

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public Object clone() {
		Factory result = new Factory(this.name, this.information, (Director)this.director.clone(), this.email, this.employees, (Address)this.getAddress().clone());
		result.setId(this.getId());
		result.setCompany((Company)this.getCompany().clone());
		return result;
	}
	
	
}
