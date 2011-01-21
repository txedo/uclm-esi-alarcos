package model.business.knowledge;


public class Address {
	private int id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zip;
	
	public Address() {
	}
	
	public Address(String street, String city, String state, String country,
			String zip) {
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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
        sb.append( " address\n");
        sb.append( "       " + street + "\n");
        sb.append( "       " + zip + " " + city + " (" + state + ")\n");
        sb.append( "       " + country + "\n");
		return sb.toString();
	}
}
