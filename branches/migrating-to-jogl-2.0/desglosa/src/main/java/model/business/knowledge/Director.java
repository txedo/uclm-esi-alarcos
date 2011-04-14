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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstSurname == null) ? 0 : firstSurname.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result
				+ ((secondSurname == null) ? 0 : secondSurname.hashCode());
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
		Director other = (Director) obj;
		if (firstSurname == null) {
			if (other.firstSurname != null)
				return false;
		} else if (!firstSurname.equals(other.firstSurname))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (secondSurname == null) {
			if (other.secondSurname != null)
				return false;
		} else if (!secondSurname.equals(other.secondSurname))
			return false;
		return true;
	}
	
}
