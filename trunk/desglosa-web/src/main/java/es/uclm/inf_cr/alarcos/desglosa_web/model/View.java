package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class View {
	@XmlAttribute
	private int id;
	@XmlAttribute
	private int level;
	@XmlAttribute
	private String name;
	@XmlAttribute(name="chart")
	private String chartName;
	@XmlTransient
	private Chart chart;
	@XmlElement(name="dim")
	private List<Dimension> dimensions = new ArrayList<Dimension>();

	public View() {}

	public int getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public String getChartName() {
		return chartName;
	}
	
	public Chart getChart() {
		return chart;
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	
	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	public void obtainDimensionValues(String csvData) throws NumberFormatException {
		String [] columns = csvData.split(";");
		for (Dimension d : dimensions) {
			if (d.getType().equals(Dimension.PERCENT)) {
				String [] fields = d.getCsvCol().split(",");
				int dividend = Integer.parseInt(fields[0]);
				int divisor = Integer.parseInt(fields[1]);
				d.setValue(Double.parseDouble(columns[dividend-1])/Double.parseDouble(columns[divisor-1])*100.0);
			} else if (d.getType().equals(Dimension.EXPR)) {
				// TODO Pattern.compile
			} else {
				d.setValue(Double.parseDouble(columns[Integer.parseInt(d.getCsvCol())-1]));
			}
		}
	}

	@Override
	public Object clone() {
		View res = new View();
		res.setId(id);
		res.setLevel(level);
		res.setName(name);
		res.setChartName(chartName);
		res.setChart((Chart) chart.clone());
		res.setDimensions(new ArrayList<Dimension>(dimensions));
		return res;
	}
	
}
