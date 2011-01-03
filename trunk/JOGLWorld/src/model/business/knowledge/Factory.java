package model.business.knowledge;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import model.knowledge.Vector2f;

@XmlAccessorType(XmlAccessType.FIELD)
public class Factory {
	@XmlAttribute private int id;
	private String name;
	private String information;
	private String director;
	private String email;
	private int employees;
	@XmlElement private Address address;
	@XmlElement @XmlJavaTypeAdapter(LocationMapAdapter.class)
	private Map<Integer, Vector2f> locations = new HashMap<Integer,Vector2f>();
	
	public Factory() {
	}
	
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
	
	public void addLocation (int id, Vector2f coordinates) {
		locations.put(id, coordinates);
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
		sb.append(name);
//		sb.append(" factory\n");
//		sb.append("       " + name + "\n");
//		sb.append("       " + information + "\n");
//		sb.append("       " + director + " (" + email + ")\n");
//		sb.append("       " + employees + "\n");
//		sb.append("       " + address + "\n");
//		sb.append("       locations\n");
//		for (Iterator i = locations.keySet().iterator(); i.hasNext(); ){
//			int mapId = (Integer)i.next();
//			Vector2f coords = locations.get(mapId);
//			sb.append("        map " + mapId + " (" + coords.getX() + "," + coords.getY() + ")\n");
//		}
		return sb.toString();
	}
}
