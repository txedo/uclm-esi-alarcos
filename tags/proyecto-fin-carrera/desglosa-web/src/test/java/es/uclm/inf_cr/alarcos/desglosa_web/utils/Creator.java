package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;

public class Creator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DBUnitUtils.getInstance().writeDataSet();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
