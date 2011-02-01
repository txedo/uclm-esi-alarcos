package model.business.knowledge;


public class Director {
	private int id;
	private String name;
	private String firstSurname;
	private String secondSurname;
	private Image picture;
	
	
	/**
	 * 
	 */
	public Director() {
		this.id = -1;
	}

	/**
	 * @param name
	 * @param firstSurname
	 */
	public Director(String name, String firstSurname) {
		this.id = -1;
		this.name = name;
		this.firstSurname = firstSurname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}

	@Override
	public Object clone() {
		Director result = new Director();
		result.setId(this.getId());
		result.setName(this.getName());
		result.setFirstSurname(this.getFirstSurname());
		result.setSecondSurname(this.getSecondSurname());
		result.setPicture((Image)this.getPicture().clone());
		return result;
	}

	@Override
	public String toString() {
		return "Director [id=" + id + ", name=" + name + ", firstSurname="
				+ firstSurname + ", secondSurname=" + secondSurname
				+ ", picture=" + picture + "]";
	}
	
}
