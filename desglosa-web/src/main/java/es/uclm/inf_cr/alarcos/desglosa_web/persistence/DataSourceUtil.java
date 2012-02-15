package es.uclm.inf_cr.alarcos.desglosa_web.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DataSourceUtil extends JdbcDaoSupport {   

    public DataSourceUtil() {
    }

    @Override
    protected void initDao() throws Exception {
        this.setJdbcTemplate(new JdbcTemplate(getDataSource()));
    }

    public void alterTableByAddingColumn(String table, String name, String type, String defaultValue) {
        getJdbcTemplate().execute("ALTER TABLE " + table  + " ADD COLUMN " + name + " " + type + "  DEFAULT " + defaultValue);
    }

    public void alterTableByRemovingColumn(String table, String name) {
        getJdbcTemplate().execute("ALTER TABLE " + table  + " DROP COLUMN " + name);
    }
}
