package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;

public class DataSourceUtil extends JdbcDaoSupport {
	public static final String DEF_TABLES_BY_TABLENAME_QUERY = "DESCRIBE ?";
	
	
    private class TablesByTablenameMapping extends MappingSqlQuery {
        protected TablesByTablenameMapping(DataSource ds) {
            super(ds, tablesByTablenameQuery);
            declareParameter(new SqlParameter(Types.VARCHAR));
            compile();
        }

        protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
            String username = rs.getString(1);
            String password = rs.getString(2);
            boolean enabled = rs.getBoolean(3);
            UserDetails user = new User(username, password, enabled, true, true, true,
                    new GrantedAuthority[] {new GrantedAuthorityImpl("HOLDER")});

            return user;
        }
    }
}
