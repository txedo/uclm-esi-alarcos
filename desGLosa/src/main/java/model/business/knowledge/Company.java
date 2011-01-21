package model.business.knowledge;


public class Company {
	private int id;
	private String name;
	private String information;
	
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
        sb.append( " company\n");
        sb.append( "       " + name + "\n");
        sb.append( "       " + information + "\n");
		return sb.toString();
	}
}
