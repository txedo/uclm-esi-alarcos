package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.util.Calendar;
import java.util.Random;

import es.uclm.inf_cr.alarcos.desglosa_web.control.MarketManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MarketNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Address;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Director;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Location;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class testUtils {
    private testUtils() {
    }
    
    public static Company generateRandomCompany() {
        Company c = new Company();
        long generator = testUtils.getGenerator();
        c.setName("test company " + generator);
        c.setInformation("test information for test company " + generator);
        c.setDirector(testUtils.generateRandomDirector());
        // This method does not generate the factories.
        // Developer must generate them outside this method, associate the company to them and save them using FactoryDAO
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
        l.setLatitude(new Float(testUtils.getRandomFloat(100)).toString());
        l.setLongitude(new Float(testUtils.getRandomFloat(100)).toString());
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
    
    public static Factory generateRandomFactory(Company company) {
        Factory f = new Factory();
        long generator = testUtils.getGenerator();
        f.setName("test factory " + generator);
        f.setInformation("test information for test factory " + generator);
        f.setEmail(generator + "@email.es");
        f.setEmployees(testUtils.getRandomInt(100));
        f.setAddress(testUtils.generateRandomAddress());
        f.setCompany(company);
        f.setDirector(testUtils.generateRandomDirector());
        f.setLocation(testUtils.generateRandomLocation());
        return f;
    }
    
    public static Project generateRandomProject(Factory mainFactory) {
        Project p = new Project();
        long generator = testUtils.getGenerator();
        p.setName("test project name " + generator);
        p.setCode("test project code " + generator);
        p.setPlan("test project plan " + generator);
        p.setMainFactory(mainFactory);
        try {
            p.setMarket(MarketManager.getMarket(1));
        } catch (MarketNotFoundException e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public static Subproject generateRandomSubproject(Factory factory, Project project) {
        Subproject sp = new Subproject();
        sp.setFactory(factory);
        sp.setProject(project);
        sp.setName("test subproject name " + testUtils.getGenerator());
        return sp;
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
