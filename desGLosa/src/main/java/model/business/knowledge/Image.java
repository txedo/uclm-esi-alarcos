package model.business.knowledge;

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
	
	
}
