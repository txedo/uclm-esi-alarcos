package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="charts")
public class Chart {
	private int id;
	private String name;
	private String type;
	private int maxCols;
	
	public Chart() {
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="type")
	public String getType() {
		return type;
	}

	@Column(name="max_cols")
	public int getMaxCols() {
		return maxCols;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMaxCols(int maxCols) {
		this.maxCols = maxCols;
	}

	@Override
	public Object clone() {
		Chart res = new Chart();
		res.setId(id);
		res.setName(name);
		res.setType(type);
		res.setMaxCols(maxCols);
		return res;
	}
	
	
}
