package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class View {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String chart;
	@XmlElement(name="dim")
	private List<Dimension> dimensions = new ArrayList<Dimension>();

	public View() {}

	public String getName() {
		return name;
	}

	public String getChart() {
		return chart;
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}
	
	
}
