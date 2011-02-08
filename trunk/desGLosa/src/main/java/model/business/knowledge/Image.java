package model.business.knowledge;

import java.util.Arrays;


public class Image {

	private int id;
	private String filename;
	private String contentType;
	private byte[] data;
	
	/**
	 * 
	 */
	public Image() {
	}
	
	/**
	 * @param filename
	 * @param contenType
	 */
	public Image(String filename, String contentType) {
		this.filename = filename;
		this.contentType = contentType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public Object clone() {
		Image result = new Image(this.getFilename(), this.getContentType());
		result.setId(this.getId());
		if (this.getData() != null) result.setData(this.getData().clone());
		return result;
	}

	@Override
	public String toString() {
		return "Image [filename=" + filename + ", contentType=" + contentType
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + Arrays.hashCode(data);
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + id;
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
		Image other = (Image) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (!Arrays.equals(data, other.data))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
