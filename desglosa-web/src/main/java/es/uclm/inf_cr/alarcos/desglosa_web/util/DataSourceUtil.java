package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;

public class DataSourceUtil extends JdbcDaoSupport {
	private MappingSqlQuery tablesByTablenameQueryMapping;
	private String tablesByTablenameQuery;
	public static final String DEF_TABLES_BY_TABLENAME_QUERY = "SELECT COLUMN_NAME, DATA_TYPE " +
			"FROM INFORMATION_SCHEMA.COLUMNS " +
			"WHERE TABLE_SCHEMA = 'desglosadb' " +
			"AND TABLE_NAME = ? " +
			"AND COLUMN_KEY <> 'MUL' " +
			"ORDER BY ORDINAL_POSITION;";
	
	public DataSourceUtil() {
		tablesByTablenameQuery = DEF_TABLES_BY_TABLENAME_QUERY;
	}
	
    @Override
	protected void initDao() throws Exception {
		tablesByTablenameQueryMapping = new TablesByTablenameMapping(getDataSource());
	}
    
    /** 
     * Executes the <tt>tablesByTablenameQuery</tt> and returns a list of Metaclass objects (there should normally 
     * only be one matching table). 
     */
    protected List loadTablesByTablename (String tablename) {
    	return tablesByTablenameQueryMapping.execute(tablename);
    }

	private class TablesByTablenameMapping extends MappingSqlQuery {
        protected TablesByTablenameMapping(DataSource ds) {
            super(ds, tablesByTablenameQuery);
            declareParameter(new SqlParameter(Types.VARCHAR));
            compile();
        }

        protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
        	Metaclass mc = new Metaclass();
        	while (rs.next()) {
        		String columnName = rs.getString(1);
        		String columnType = rs.getString(2);
        		if (columnType.equals("varchar")) columnType = "string";
        		if (columnType.equals("tinyint")) columnType = "boolean";
        		mc.addTableColumn(columnName, columnType, null);
        	}
        	return mc;
        }
    }
}
