package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class FactoryManager {
    private static FactoryDAO factoryDao;
    
    private FactoryManager() {
    }

    public static boolean checkFactoryExists(int id) {
        boolean bExists = true;
        try {
            FactoryManager.getFactory(id);
            bExists = true;
        } catch (FactoryNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }
    
    public static boolean checkFactoryExists(String name) {
        boolean bExists = true;
        try {
            FactoryManager.getFactory(name);
            bExists = true;
        } catch (FactoryNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }

    public static void saveFactory(Factory f) {
        factoryDao.saveFactory(f);
    }
    
    public static Factory getFactory(int id) throws FactoryNotFoundException {
        return factoryDao.getFactory(id);
    }
    
    public static Factory getFactory(String name) throws FactoryNotFoundException {
        return factoryDao.getFactory(name);
    }
    
    public static List<Factory> getAllFactories() {
        return factoryDao.getAll();
    }
    
    public static void removeFactory(int id) {
        factoryDao.removeFactory(id);
    }
    
    public void setFactoryDao(FactoryDAO factoryDao) {
        FactoryManager.factoryDao = factoryDao;
    }
}
