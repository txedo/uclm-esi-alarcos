package es.uclm.inf_cr.alarcos.desglosa_web.control;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;

public class MeasureManager {
    private MeasureDAO measureDao;
    
    private MeasureManager() {
    }

    public void setMeasureDao(MeasureDAO measureDao) {
        this.measureDao = measureDao;
    }
    
    
}
