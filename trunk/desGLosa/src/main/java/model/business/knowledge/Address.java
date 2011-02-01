package model.business.knowledge;


public class Address {
	private int id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zip;
	
	public Address() {
		this.id = -1;
	}
	
	public Address(String street, String city, String state, String country,
			String zip) {
		this.id = -1;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", zip=" + zip
				+ "]";
	}

	@Override
	public Object clone() {
		Address result = new Address(this.street, this.city, this.state, this.country, this.zip);
		result.setId(this.getId());
		return result;
	}
	
}
