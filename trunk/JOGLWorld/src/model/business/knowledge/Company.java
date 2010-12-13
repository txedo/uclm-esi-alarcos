package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company {
	@XmlAttribute private int id;
	private String name;
	private String information;
	@XmlElement ( name="factory" , required=false) private List<Factory> factories = new ArrayList<Factory>();
	
	public Company(){}
	
	public Company(String name, String information) {
		this.name = name;
		this.information = information;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public int getLastFactoryId () {
		int lastId = 0;
		if (factories.size() != 0)
			lastId = ((Factory)factories.get(factories.size()-1)).getId();
		return ++lastId;
	}
	
	public List<Factory> getFactories() {
		return factories;
	}

	public void addFactory (Factory factory) {
		factories.add(factory);
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
	
	public String toString (){
		StringBuffer sb = new StringBuffer();
		sb.append(this.name);
//        sb.append( " company\n");
//        sb.append( "       " + name + "\n");
//        sb.append( "       " + information + "\n");
//        sb.append( "       " + factories + "\n");
		return sb.toString();
	}
}
