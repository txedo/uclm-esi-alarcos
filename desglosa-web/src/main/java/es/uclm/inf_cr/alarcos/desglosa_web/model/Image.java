package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	private int id;
	private String filename;
	private String contentType;
	private byte[] data;
	
	public Image() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column
	public String getFilename() {
		return filename;
	}

	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}

	@Column
	public byte[] getData() {
		return data;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	

}
