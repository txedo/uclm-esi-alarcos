package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

import java.util.List;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;

import es.uclm.inf_cr.alarcos.desglosa_web.control.MeasureManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.DataSourceUtil;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

public class TransientAttributesListener implements PostLoadEventListener {
    private static final long serialVersionUID = -4143306987360141117L;

    public void onPostLoad(PostLoadEvent event) {
        if (event.getEntity() instanceof Factory) {
            ((Factory) event.getEntity()).updateTransientFields();
            List<Measure> measureList = MeasureManager.getMeasuresByEntity(Measure.FACTORY_ENTITY);
            for (Measure m : measureList) {
                
            }
        }
        if (event.getEntity() instanceof Project) {
            List<Measure> measureList = MeasureManager.getMeasuresByEntity(Measure.PROJECT_ENTITY);
            String sqlColumns = "";
            for (Measure m : measureList) {
                sqlColumns += m.getName() + ",";
            }
            if (sqlColumns.length() > 0) {
                sqlColumns = sqlColumns.substring(0, sqlColumns.length()-1); // remove last colon
                String sqlQuery = "SELECT " + sqlColumns + " FROM " + Measure.PROJECT_TABLE + " WHERE id = '" + ((Project) event.getEntity()).getId() + "'";
                List<?> values = ((DataSourceUtil) ApplicationContextProvider.getBean("dataSourceUtil")).query(sqlQuery);
                System.out.println("");
            }
        }
    }
}
