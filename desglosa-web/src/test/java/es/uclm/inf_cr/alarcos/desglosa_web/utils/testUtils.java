package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.util.Calendar;
import java.util.Random;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Address;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Director;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Location;

public class testUtils {
    private testUtils() {
    }
    
    public static Company generateRandomCompany() {
        Company c = new Company();
        long generator = testUtils.getGenerator();
        c.setName("test company " + generator);
        c.setInformation("test information for test company " + generator);
        c.setDirector(testUtils.generateRandomDirector());
        for (int i = 0; i < 2; i++) {
            Factory factory = testUtils.generateRandomFactory();
            factory.setCompany(c);
            c.getFactories().add(factory);
        }
        return c;
    }
    
    public static Director generateRandomDirector() {
        Director d = new Director();
        long generator = testUtils.getGenerator();
        d.setName("test director " + generator);
        d.setLastName("test last name " + generator);
        return d;
    }
    
    public static Location generateRandomLocation() {
        Location l = new Location();
        l.setLatitude(new Float(testUtils.getRandomFloat(100)));
        l.setLongitude(new Float(testUtils.getRandomFloat(100)));
        return l;
    }
    
    public static Address generateRandomAddress() {
        Address a = new Address();
        long generator = testUtils.getGenerator();
        a.setAddress("address " + generator);
        a.setCity("city " + generator);
        a.setProvince("province " + generator);
        a.setCountry("country " + generator);
        a.setPostalCode("postal code " + generator);
        return a;
    }
    
    public static Factory generateRandomFactory() {
        Factory f = new Factory();
        long generator = testUtils.getGenerator();
        f.setName("test factory " + generator);
        f.setInformation("test information for test factory " + generator);
        f.setEmail(generator + "@email.es");
        f.setEmployees(testUtils.getRandomInt(100));
        f.setAddress(testUtils.generateRandomAddress());
        // f.setCompany
        f.setDirector(testUtils.generateRandomDirector());
        f.setLocation(testUtils.generateRandomLocation());
        return f;
    }
    
    public static long getGenerator() {
        long result = 0;
        try {
            Thread.sleep(100);
            result = Calendar.getInstance().getTimeInMillis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static float getRandomFloat(int n) {
        Random r = new Random();
        r.setSeed(testUtils.getGenerator());
        return r.nextFloat()*n;
    }
    
    public static int getRandomInt(int n) {
        Random r = new Random();
        r.setSeed(testUtils.getGenerator());
        return r.nextInt(n+1);
    }
}
