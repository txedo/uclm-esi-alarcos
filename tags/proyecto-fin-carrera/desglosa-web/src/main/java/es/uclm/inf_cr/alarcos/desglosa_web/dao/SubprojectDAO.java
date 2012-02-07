package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public interface SubprojectDAO extends GenericDAO<Subproject, Long> {
    Subproject getSubproject(int id) throws SubprojectNotFoundException;

    Subproject getSubproject(String name) throws SubprojectNotFoundException;

    List<Subproject> getSubprojects();

    void saveSubproject(Subproject subproject);

    void removeSubproject(int id);
}