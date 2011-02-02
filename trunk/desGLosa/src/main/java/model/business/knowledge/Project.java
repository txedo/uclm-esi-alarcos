package model.business.knowledge;

import java.util.Set;

import sun.util.calendar.LocalGregorianCalendar.Date;


public class Project {
	private int id;
	
	private ProjectStatus status;
	private String lifeCycle; // TODO enum??
	private String lifeCycleStage; // TODO enum??
	
	// TODO número de liberaciones cuatrimestre 1, 2, 3...
	private String code;
	private String planName;
	private Date start;
	private Date end;
	
	// TODO Buscar nombres para jefe, GP y director
	private String boss;
	private String gp;
	private String director;
	
	private String marketClient;
	private String finalClient;
	
	private Factory workingFactory;
	private Set<Factory> involvedFactories;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPlanName() {
		return planName;
	}
	
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	public Factory getWorkingFactory() {
		return workingFactory;
	}
	
	public void setWorkingFactory(Factory workingFactory) {
		this.workingFactory = workingFactory;
	}
	
	public Set<Factory> getInvolvedFactories() {
		return involvedFactories;
	}
	
	public void setInvolvedFactories(Set<Factory> involvedFactories) {
		this.involvedFactories = involvedFactories;
	}
	
	
}
