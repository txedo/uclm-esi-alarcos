package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;

public interface MeasureDAO extends GenericDAO<Measure, Long> {
    Measure getMeasure(int id) throws MeasureNotFoundException;

    Measure getMeasure(String name) throws MeasureNotFoundException;

    List<Measure> getMeasures();
    
    List<Measure> getMeasuresByEntity(String entity);

    void saveMeasure(Measure measure);

    void removeMeasure(int id);
}
