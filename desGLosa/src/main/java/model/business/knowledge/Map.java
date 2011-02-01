package model.business.knowledge;


public class Map {
	private int id;
	private int parentId;
	private String label;
	private String checksum;
	private Image image;
	
	public Map() {
	}
	
	public Map(int parentId, String label, String checksum) {
		this.parentId = parentId;
		this.label = label;
		this.checksum = checksum;
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

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public Object clone() {
		Map result = new Map(this.getParentId(), this.getLabel(), this.getChecksum());
		result.setId(this.getId());
		result.setImage((Image)this.getImage().clone());
		return result;
	}
	
}
