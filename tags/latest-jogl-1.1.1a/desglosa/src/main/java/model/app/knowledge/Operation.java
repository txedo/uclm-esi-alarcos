package model.app.knowledge;


public class Operation {
	private String name;
	private String description;
	private String icon;
	private String container;
	private String type;
	
	public Operation(String name, String description, String icon, String container, String type) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.container = container;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
