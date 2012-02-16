package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.ArrayList;
import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureDuplicatedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureAnnotationParser;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;

public class MeasureManager {
    private static MeasureDAO measureDao;
    
    private MeasureManager() {
    }

    public void setMeasureDao(MeasureDAO measureDao) {
        MeasureManager.measureDao = measureDao;
    }

    public static List<Measure> getAllMeasures() {
        return measureDao.getMeasures();
    }

    public static Measure getMeasure(int id) throws MeasureNotFoundException {
        return measureDao.getMeasure(id);
    }
    
    private static Measure getMeasure(String name) throws MeasureNotFoundException {
        return measureDao.getMeasure(name);
    }
    
    public static List<Measure> getMeasuresByEntity(String entity) {
        return measureDao.getMeasuresByEntity(entity);
    }
    
    public static void getAllBaseMeasuresByEntity(String entity) {
        MeasureAnnotationParser.parseBaseMeasures(entity);
    }
    
    public static void getAllMeasuresByEntity(String entity) {
        MeasureAnnotationParser.parseMeasures(entity);
    }
    
    public static List<PropertyWrapper> measures2WrappedProperties(List<Measure> measures) {
        List<PropertyWrapper> pw = new ArrayList<PropertyWrapper>();
        for (Measure m : measures) {
            pw.add(new PropertyWrapper(m.getName(), m.getType(), m.getDescription()));
        }
        return pw;
    }

    public static void saveMeasure(Measure measure) throws MeasureDuplicatedException {
        measure.inferDbTable();
        // Check the measure does not exist in database for the same entity
        Measure aux;
        try {
            aux = MeasureManager.getMeasure(measure.getName());
            if (aux.getName().equals(measure.getName()) && aux.getEntity().equals(measure.getEntity())) {
                throw new MeasureDuplicatedException();
            }
        } catch (MeasureNotFoundException e) {
            // do nothing
        }
        if (measure.getDescription().equals("")) {
            measure.setDescription("No description has been set.");
        }
        measureDao.saveMeasure(measure);
    }

    public static void removeMeasure(int id) {
        measureDao.removeMeasure(id);
    }
    
}
