package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public class DBUnitUtils extends DBTestCase {
    private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/desglosadb";
    private final static String USERNAME = "desglosaadmin";
    private final static String PASSWORD = "nimdaasolgsed";
    
    private static DBUnitUtils _instance = null;
    
    private DBUnitUtils() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER_CLASS);  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, CONNECTION_URL);  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USERNAME);  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD); 
    }

    public static DBUnitUtils getInstance() {
        if(_instance == null) {
            _instance = new DBUnitUtils();
        }
        return _instance;
    }

    protected static void writeDataSet() throws SQLException, UnsupportedEncodingException, FileNotFoundException, DataSetException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        
        IDatabaseConnection connection = new DatabaseConnection( conn );
        
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        //Specify the SQL to run to retrieve the data
        partialDataSet.addTable("companies", "SELECT * FROM companies");
        
        //Specify the location of the flat file(XML)
        //FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream("temp.xml"));
        MyXmlDataSetWriter datasetWriter = new MyXmlDataSetWriter(new OutputStreamWriter(new FileOutputStream("src/test/resources/sample-data.xm"), "UTF8"));
        
        //Export the data
        datasetWriter.write(partialDataSet);
        
        //IDataSet dataSet = new XmlDataSet(new InputStreamReader(new FileInputStream("src/test/resources/sample-data.xml")));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(new InputStreamReader(new FileInputStream("src/test/resources/sample-data.xml")));
    }
    
    /* (non-Javadoc)
     * @see org.dbunit.DatabaseTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }

    /** {@inheritDoc} */  
    protected DatabaseOperation getSetUpOperation() throws Exception {  
        return DatabaseOperation.CLEAN_INSERT;  
    }  

    /* (non-Javadoc)
     * @see org.dbunit.DatabaseTestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    /** {@inheritDoc} */  
    protected DatabaseOperation getTearDownOperation() throws Exception {  
        return DatabaseOperation.DELETE_ALL;  
    }  
}