package model.business.knowledge;

import java.util.List;

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
	
	private List<Centre> involvedCentres;

	public List<Centre> getInvolvedCentres() {
		return involvedCentres;
	}

	public void setInvolvedCentres(List<Centre> involvedCentres) {
		this.involvedCentres = involvedCentres;
	}
	
}
