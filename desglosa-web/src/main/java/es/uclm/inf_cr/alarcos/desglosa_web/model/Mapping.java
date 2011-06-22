package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="mappings")
public class Mapping {
	private int id;
	private Profile profile;
	private int colData;
	private int colChart;
	private String title;
	private String type;
	private String description;
	private Measure measure;
	
	public Mapping(){}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
    @JoinColumn(name="profile_id", nullable=false)
	public Profile getProfile() {
		return this.profile;
	}

	@Column(name="col_data")
	public int getColData() {
		return colData;
	}

	@Column(name="col_chart")
	public int getColChart() {
		return colChart;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	@Column(name="type")
	public String getType() {
		return type;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="measure_id")
	public Measure getMeasure() {
		return measure;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setColData(int colData) {
		this.colData = colData;
	}

	public void setColChart(int colChart) {
		this.colChart = colChart;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	
	
}
