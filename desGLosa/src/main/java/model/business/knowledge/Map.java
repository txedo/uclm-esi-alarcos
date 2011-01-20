package model.business.knowledge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Map {
	@XmlAttribute
	private int id;
	@XmlAttribute
	private int parentId;
	private String label;
	private String filename;
	private String hashcode;
	
	public Map() {
	}
	
	public Map(int id, int parentId, String label, String filename,
			String hashcode) {
		this.id = id;
		this.parentId = parentId;
		this.label = label;
		this.filename = filename;
		this.hashcode = hashcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer();
		sb.append(this.label);
		return sb.toString();
	}
}
