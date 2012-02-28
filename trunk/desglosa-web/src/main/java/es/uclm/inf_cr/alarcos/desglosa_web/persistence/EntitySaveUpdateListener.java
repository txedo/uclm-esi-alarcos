package es.uclm.inf_cr.alarcos.desglosa_web.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

public class EntitySaveUpdateListener implements PostInsertEventListener, PostUpdateEventListener {
    private static final long serialVersionUID = 7744140044988456296L;

    public void onPostUpdate(PostUpdateEvent event) {
        this.saveOrUpdate(event.getEntity());
    }

    public void onPostInsert(PostInsertEvent event) {
        this.saveOrUpdate(event.getEntity());
    }
    private void saveOrUpdate(Object entity) {
        int id = 0;
        String table = "";
        Map<String, Object> measures = new HashMap<String, Object>();
        if (entity instanceof Company) {
            id = ((Company) entity).getId();
            table = Measure.COMPANY_TABLE;
            measures = ((Company) entity).getMeasures();
        } else if (entity instanceof Factory) {
            id = ((Factory) entity).getId();
            table = Measure.FACTORY_TABLE;
            measures = ((Factory) entity).getMeasures();
        } else if (entity instanceof Project) {
            id = ((Project) entity).getId();
            table = Measure.PROJECT_TABLE;
            measures = ((Project) entity).getMeasures();
        } else if (entity instanceof Subproject) {
            id = ((Subproject) entity).getId();
            table = Measure.SUBPROJECT_TABLE;
            measures = ((Subproject) entity).getMeasures();
        } else if (entity instanceof Market) {
            id = ((Market) entity).getId();
            table = Measure.MARKET_TABLE;
            measures = ((Market) entity).getMeasures();
        }
        String sFields = "";
        Iterator<?> it = measures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            String name = (String) pairs.getKey();
            String value =((String[]) pairs.getValue())[0];
            if (value.equals("true")) {
                value = value.replace("true", "1");
            } else if (value.equals("false")) {
                value = value.replace("false", "0");
            } else {
                if (value.matches("[0-9]*\\,?[0-9]+")) {
                    value = value.replace(",", ".");
                }
            }
            value = "'" + value + "'";
            sFields += name + " = " + value + ",";
        }
        if (id != 0 && !table.equals("") && sFields.length() > 0) {
            sFields = sFields.substring(0, sFields.length()-1); // remove last colon
            String sqlQuery = "UPDATE " + table + " SET " + sFields + " WHERE id = '" + id + "';";
            int rows = ((DataSourceUtil) ApplicationContextProvider.getBean("dataSourceUtil")).update(sqlQuery);
        }
    }
}
