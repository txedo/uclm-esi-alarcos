package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;


public interface SubprojectDAO extends GenericDAO<Subproject, Long> {

	public Subproject getSubproject(int id) throws SubprojectNotFoundException;
	
	public Subproject getSubproject(String name) throws SubprojectNotFoundException;
	
	public List<Subproject> getSubprojects();
	
	public void saveSubproject(Subproject subproject);
	
	public void removeSubproject(int id);
}