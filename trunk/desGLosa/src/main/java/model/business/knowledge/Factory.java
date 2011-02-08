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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + employees;
		result = prime * result + id;
		result = prime * result
				+ ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factory other = (Factory) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (employees != other.employees)
			return false;
		if (id != other.id)
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
