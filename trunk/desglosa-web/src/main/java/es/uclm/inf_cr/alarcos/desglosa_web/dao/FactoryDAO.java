package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public interface FactoryDAO extends GenericDAO<Factory, Long> {
    Factory getFactory(int id) throws FactoryNotFoundException;
    Factory getFactory(String name) throws FactoryNotFoundException;
    List<Factory> getFactories();
    void saveFactory(Factory factory);
    void removeFactory(int id);
}
