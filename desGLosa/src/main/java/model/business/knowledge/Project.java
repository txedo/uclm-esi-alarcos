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

	@Override
	public String toString() {
		return "[" + code + "] " + planName;
	}

	@Override
	public Object clone() {
		Project result = new Project();
		result.setId(this.getId());
		result.setCode(this.getCode());
		result.setPlanName(this.getPlanName());
		result.setWorkingFactory((Factory)this.getWorkingFactory().clone());
		result.setInvolvedFactories(this.getInvolvedFactories());
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + id;
		result = prime
				* result
				+ ((involvedFactories == null) ? 0 : involvedFactories
						.hashCode());
		result = prime * result
				+ ((planName == null) ? 0 : planName.hashCode());
		result = prime * result
				+ ((workingFactory == null) ? 0 : workingFactory.hashCode());
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
		Project other = (Project) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		if (involvedFactories == null) {
			if (other.involvedFactories != null)
				return false;
		} else if (!involvedFactories.equals(other.involvedFactories))
			return false;
		if (planName == null) {
			if (other.planName != null)
				return false;
		} else if (!planName.equals(other.planName))
			return false;
		if (workingFactory == null) {
			if (other.workingFactory != null)
				return false;
		} else if (!workingFactory.equals(other.workingFactory))
			return false;
		return true;
	}
	
	
	
}
