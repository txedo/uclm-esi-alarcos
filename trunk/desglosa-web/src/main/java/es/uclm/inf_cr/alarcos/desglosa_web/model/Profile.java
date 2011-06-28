package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="profile")
@XmlAccessorType(XmlAccessType.FIELD)
public class Profile {
	@XmlAttribute
	private String name;
	@XmlElement(name="color")
	private Color color;
	@XmlElement(name="view")
	private List<View> views = new ArrayList<View>();
	
	public Profile() {}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public List<View> getViews() {
		return views;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setViews(List<View> views) {
		this.views = views;
	}
	
}
