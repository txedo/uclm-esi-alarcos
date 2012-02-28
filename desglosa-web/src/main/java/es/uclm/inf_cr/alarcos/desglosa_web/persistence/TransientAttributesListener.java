package es.uclm.inf_cr.alarcos.desglosa_web.persistence;

import java.util.List;
import java.util.Map;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.MeasureManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

public class TransientAttributesListener extends JdbcDaoSupport implements PostLoadEventListener {
    private static final long serialVersionUID = -4143306987360141117L;

    public void onPostLoad(PostLoadEvent event) {
        if (event.getEntity() instanceof Factory) {
            ((Factory) event.getEntity()).updateTransientFields();
            List<Measure> measureList = MeasureManager.getMeasuresByEntity(Measure.FACTORY_ENTITY);
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
                Map<String, Object> resulset = ((DataSourceUtil) ApplicationContextProvider.getBean("dataSourceUtil")).query(sqlQuery);
                for (int i = 0; i < measureList.size(); i++) {
                    String name = measureList.get(i).getName();
                    Object value = resulset.get(name);
                    ((Project) event.getEntity()).getMeasures().put(name, value);
                }
            }
        }
    }
}
