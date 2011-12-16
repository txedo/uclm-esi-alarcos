package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.util.Calendar;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Director;

public class testUtils {
    private testUtils() {
    }
    
    public static Company generateRandomCompany() {
        Company c = new Company();
        long generator = Calendar.getInstance().getTimeInMillis();
        c.setName("test company " + generator);
        c.setInformation("test information for test company " + generator);
        c.setDirector(testUtils.generateRandomDirector());
        return c;
    }
    
    public static Director generateRandomDirector() {
        Director d = new Director();
        long generator = Calendar.getInstance().getTimeInMillis();
        d.setName("test director " + generator);
        d.setLastName("test last name " + generator);
        return d;
    }
}
