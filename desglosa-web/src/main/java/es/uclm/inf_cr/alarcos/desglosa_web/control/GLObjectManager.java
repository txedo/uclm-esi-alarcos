package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.WordUtils;

import model.gl.knowledge.GLObject;
import model.util.City;
import model.util.Neighborhood;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.EntityNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.GroupByOperationNotSupportedException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.IncompatibleTypesException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Field;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Mapping;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Rule;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.MyHashMapEntryType;

public class GLObjectManager {
    
    public static final String GROUP_BY_COMPANY = "company";
    public static final String GROUP_BY_FACTORY = "factory";
    public static final String GROUP_BY_PROJECT = "project";
    public static final String GROUP_BY_MARKET = "market";
    public static final String NO_GROUP_BY = "";
    
    private GLObjectManager() {
    }

    public static City createGLObjects(List<?> entities, String groupBy, String profileName) throws JAXBException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException,
            EntityNotSupportedException, GroupByOperationNotSupportedException,
            NoSuchFieldException, IncompatibleTypesException {
        City c = new City();
        // Load selected profile
        Metaclass metaclass = ProfileManager.getProfileByName(profileName);

        Class<?> classModel = Class.forName(metaclass.getModelName());
        c.setModel(metaclass.getModelName());

        if (entities != null && entities.size() > 0) {
            // Create a map of list in which key is neighborhood name and value
            // is a list of flats
            Map<String, List<?>> neighborhoods = groupEntitiesBy(entities.get(0).getClass(), entities, groupBy);
            // Iterate over each list creating GLObjects while creating
            // GLNeighborhoods and GLCity
            Iterator<?> it = neighborhoods.entrySet().iterator();
            while (it.hasNext()) {
                List<GLObject> glObjects = new ArrayList<GLObject>();
                Map.Entry<String, List<?>> pairs = (Map.Entry<String, List<?>>) it.next();
                for (Object e : pairs.getValue()) {
                    Object glObj = classModel.newInstance();
                    // Do mappings using java reflection
                    for (Mapping mapping : metaclass.getMappings()) {
                        // Build setter method using Java Reflective API
                        String setterName = "set" + WordUtils.capitalize(mapping.getModelAttr().getName());
                        // Look for the setter method through all the object hierarchy
                        Class<?> superClass = classModel;
                        Method setterMethod = superClass.getMethod(setterName, mapping.getModelAttr().getParameterType());

                        // Build getter method using Java Reflective API
                        // oneWord_otherWord_anotherWord must be parsed to
                        // invoke getOneWord().getOtherWord().getAnotherWord()
                        String[] chainOfGetters = mapping.getEntityAttr().getName().split("_");
                        Class<?> subClass = e.getClass();
                        Object entityAttrValue = e;
                        for (int i = 0; i < chainOfGetters.length && entityAttrValue != null; i++) {
                            String getterPrefix = "get";
                            if (mapping.getEntityAttr().getType().equals("boolean")) {
                                getterPrefix = "is";
                            }
                            String parentGetterName = getterPrefix + WordUtils.capitalize(chainOfGetters[i]);
                            Method parentGetterMethod = subClass.getMethod(parentGetterName, null);
                            entityAttrValue = parentGetterMethod.invoke(entityAttrValue, null);
                            subClass = subClass.getDeclaredField(chainOfGetters[i]).getType();
                        }
                        if (entityAttrValue != null) {
                            // Check model attribute type and handle it in a
                            // special manner if its range or color
                            Object finalValue = null;
                            String entityAttrType = mapping.getEntityAttr().getType();
                            String modelAttrType = mapping.getModelAttr().getType();
                            if (modelAttrType.equals("float_range")) {
                                // leer las reglas y aplicarlas
                                // entityAttrType puede ser int
                                // (low-high-value), float (low-high-value),
                                // string (low-value), boolean (low-value)
                                for (Rule r : mapping.getRules()) {
                                    if (entityAttrType.equals("int") || entityAttrType.equals("float")) {
                                        String entityAttrValueType = entityAttrValue.getClass().getName();
                                        String ruleLowValueType = r.getLow().getClass().getName();
                                        String ruleHighValueType = r.getHigh().getClass().getName();
                                        if (entityAttrValueType.equals(ruleLowValueType) && ruleLowValueType.equals(ruleHighValueType)) {
                                            if (entityAttrValueType.equals("java.lang.Integer")) {
                                                if ((Integer) entityAttrValue >= (Integer) r.getLow() && (Integer) entityAttrValue <= (Integer) r.getHigh()) {
                                                    finalValue = (Float) r.getValue();
                                                }
                                            } else if (entityAttrValueType.equals("java.lang.Float")) {
                                                if ((Float) entityAttrValue >= (Float) r.getLow() && (Float) entityAttrValue <= (Float) r.getHigh()) {
                                                    finalValue = (Float) r.getValue();
                                                }
                                            }
                                        } else {
                                            throw new IncompatibleTypesException();
                                        }
                                    } else if (entityAttrType.equals("string")) {
                                        if (((String) entityAttrValue).equals((String) r.getLow())) {
                                            finalValue = (Float) r.getValue();
                                        }
                                    } else if (entityAttrType.equals("boolean")) {
                                        if (entityAttrValue == r.getLow()) {
                                            finalValue = (Float) r.getValue();
                                        }
                                    }
                                }
                            } else if (!entityAttrType.equals("hexcolor") && modelAttrType.equals("color")) {
                                for (Rule r : mapping.getRules()) {
                                    if (entityAttrType.equals("int") || entityAttrType.equals("float")) {
                                        String entityAttrValueType = entityAttrValue.getClass().getName();
                                        String ruleLowValueType = r.getLow().getClass().getName();
                                        String ruleHighValueType = r.getHigh().getClass().getName();
                                        if (ruleLowValueType.equals(ruleHighValueType)) {
                                            if (entityAttrValueType.equals("java.lang.Integer")) {
                                                if ((Integer) entityAttrValue >= (Integer) r.getLow() && (Integer) entityAttrValue <= (Integer) r.getHigh()) {
                                                    finalValue = (String) r.getValue();
                                                }
                                            } else if (entityAttrValueType.equals("java.lang.Float")) {
                                                if ((Float) entityAttrValue >= (Float) r.getLow() && (Float) entityAttrValue <= (Float) r.getHigh()) {
                                                    finalValue = (String) r.getValue();
                                                }
                                            }
                                        } else {
                                            throw new IncompatibleTypesException();
                                        }
                                    } else if (entityAttrType.equals("hexcolor")) {

                                    } else if (entityAttrType.equals("string")) {
                                        if (((String) entityAttrValue).equals((String) r.getLow())) {
                                            finalValue = (String) r.getValue();
                                        }
                                    } else if (entityAttrType.equals("boolean")) {
                                        if (entityAttrValue == r.getLow()) {
                                            finalValue = (String) r.getValue();
                                        }
                                    }
                                }
                            } else if (entityAttrType.equals("int") && modelAttrType.equals("float")) {
                                finalValue = ((Integer) entityAttrValue).floatValue();
                            } else if (entityAttrType.equals("int") && modelAttrType.equals("string")) {
                                finalValue = ((Integer) entityAttrValue).toString();
                            } else if (entityAttrType.equals("float") && modelAttrType.equals("string")) {
                                finalValue = ((Float) entityAttrValue).toString();
                            } else {
                                finalValue = entityAttrValue;
                            }
                            setterMethod.invoke(classModel.cast(glObj), finalValue);
                            if (entityAttrType.equals("float") && modelAttrType.equals("float")) {
                                // Si la asociacion mapea dos floats, puede definirse un ratio para la dimension
                                // este ratio indica el valor maximo que tomara la dimension en la visualizacion, pero es opcional
                                // Comprobar si el ratio es distinto de null, en caso afirmativo anadirlo a la city
                                if (mapping.getRatio() != null) {
                                    c.getRatios().put(mapping.getModelAttr().getName(), (Float)mapping.getRatio());
                                }
                            }
                        } else {
                            // El valor en la base de datos es null y se lanzaria IllegalArgumentException, por eso aplicamos un valor por defecto
                            setterMethod.invoke(classModel.cast(glObj), mapping.getModelAttr().generateDefaultValue());
                        }
                    }
                    // Apply constant values using java reflection too
                    for (Field field : metaclass.getConstants()) {
                        // Build setter method using Java Reflective API
                        String setterName = "set" + WordUtils.capitalize(field.getName());
                        Method setterMethod = classModel.getMethod(setterName, field.getParameterType());
                        if (field.getValue() != null) {
                            setterMethod.invoke(classModel.cast(glObj), field.getValue());
                        } else {
                            // El valor de la constante es null y se lanzaria IllegalArgumentException, por eso aplicamos un valor por defecto
                            setterMethod.invoke(classModel.cast(glObj), field.generateDefaultValue());
                        }
                    }
                    glObjects.add((GLObject) glObj);
                }
                if (glObjects.size() > 0) {
                    c.getNeighborhoods().add(new Neighborhood(pairs.getKey(), glObjects));
                }
            }

            // Configure caption
            if (metaclass.getCaptionLines().getEntry().size() > 0) {
                Map<String, String> captionLines = new HashMap<String, String>();
                for (MyHashMapEntryType mhmet : metaclass.getCaptionLines().getEntry()) {
                    captionLines.put(mhmet.getKey(), mhmet.getValue());
                }
                c.setCaptionLines(captionLines);
            }
        }

        return c;
    }

    private static Map<String, List<?>> groupEntitiesBy(Class<?> clazz, List<?> entities, String groupBy) throws EntityNotSupportedException, GroupByOperationNotSupportedException {
        String className = clazz.getSimpleName();
        Map<String, List<?>> neighborhoods = new HashMap<String, List<?>>();
        if (className.equals("Factory")) {
            neighborhoods = groupFactoriesBy(entities, groupBy);
        } else if (className.equals("Project")) {
            neighborhoods = groupProjectsBy(entities, groupBy);
        } else if (className.equals("Subproject")) {
            neighborhoods = groupSubprojectsBy(entities, groupBy);
        } else {
            throw new EntityNotSupportedException();
        }
        return neighborhoods;
    }

    private static Map<String, List<?>> groupFactoriesBy(List<?> factories, String groupBy) throws GroupByOperationNotSupportedException {
        Map<String, List<?>> neighborhoods = new HashMap<String, List<?>>();
        if (groupBy.equals(GROUP_BY_COMPANY)) {
            List<Company> availableCompanies = CompanyManager.getAllCompanies();
            for (Company comp : availableCompanies) {
                List<Factory> flats = new ArrayList<Factory>();
                for (Object f : factories) {
                    if (((Factory) f).getCompany().getId() == comp.getId()) {
                        flats.add((Factory) f);
                    }
                }
                if (flats.size() > 0)
                    neighborhoods.put(comp.getName(), flats);
            }
        } else if (groupBy.equals(GROUP_BY_MARKET)) {
            List<Market> availableMarkets = MarketManager.getAllMarkets();
            for (Market m : availableMarkets) {
                List<Factory> flats = new ArrayList<Factory>();
                for (Object f : factories) {
                    Market market = ((Factory) f).getMostRepresentativeMarket();
                    if (market != null && market.equals(m)) {
                        flats.add((Factory) f);
                    }
                }
                if (flats.size() > 0) {
                    neighborhoods.put(m.getName(), flats);
                }
            }
            // Hago una segunda pasada para crear un barrio sin nombre en caso de que haya factorias sin mercado de especializacion
            List<Factory> orphanFactories = new ArrayList<Factory>();
            for (Object f : factories) {
                Market market = ((Factory) f).getMostRepresentativeMarket();
                if (market == null) {
                    orphanFactories.add((Factory) f);
                }
            }
            if (orphanFactories.size() > 0) {
                neighborhoods.put("", orphanFactories);
            }
        } else if (groupBy.equals(GROUP_BY_PROJECT)) {
            List<Project> availableProjects = ProjectManager.getAllProjects();
            for (Project p : availableProjects) {
                int factoryIndex = 0;
                List<Factory> flats = new ArrayList<Factory>();
                for (Object f : factories) {
                    boolean found = false;
                    Iterator<?> it = p.getSubprojects().iterator();
                    while (it.hasNext() && !found) {
                        Subproject spaux = (Subproject) it.next();
                        if (spaux.getFactory().getId() == ((Factory) f).getId()) {
                            flats.add((Factory) f);
                            found = true;
                        }
                    }
                    factoryIndex++;
                }
                if (flats.size() > 0)
                    neighborhoods.put(p.getName(), flats);
            }
        } else if (groupBy.equals(NO_GROUP_BY)) {
            neighborhoods.put("", factories);
        } else {
            throw new GroupByOperationNotSupportedException();
        }
        return neighborhoods;
    }

    private static Map<String, List<?>> groupProjectsBy(List<?> projects, String groupBy) throws GroupByOperationNotSupportedException {
        Map<String, List<?>> neighborhoods = new HashMap<String, List<?>>();
        if (groupBy.equals(GROUP_BY_COMPANY)) {
            List<Company> availableCompanies = CompanyManager.getAllCompanies();
            for (Company comp : availableCompanies) {
                List<Project> flats = new ArrayList<Project>();
                for (Object p : projects) {
                    boolean found = false;
                    Iterator<?> it = ((Project) p).getSubprojects().iterator();
                    while (it.hasNext() && !found) {
                        Subproject spaux = (Subproject) it.next();
                        if (spaux.getFactory().getCompany().getId() == comp.getId()) {
                            flats.add((Project) p);
                            found = true;
                        }
                    }
                }
                if (flats.size() > 0) {
                    neighborhoods.put(comp.getName(), flats);
                }
            }
        } else if (groupBy.equals(GROUP_BY_MARKET)) {
            List<Market> availableMarkets = MarketManager.getAllMarkets();
            for (Market m : availableMarkets) {
                List<Project> flats = new ArrayList<Project>();
                for (Object p : projects) {
                    if (((Project) p).getMarket().equals(m))
                        flats.add((Project) p);
                }
                if (flats.size() > 0) {
                    neighborhoods.put(m.getName(), flats);
                }
            }
        } else if (groupBy.equals(GROUP_BY_FACTORY)) {
            List<Factory> availableFactories = FactoryManager.getAllFactories();
            for (Factory f : availableFactories) {
                List<Project> flats = new ArrayList<Project>();
                for (Object p : projects) {
                    boolean found = false;
                    Iterator<?> it = ((Project) p).getSubprojects().iterator();
                    while (it.hasNext() && !found) {
                        Subproject spaux = (Subproject) it.next();
                        if (spaux.getFactory().getId() == f.getId()) {
                            flats.add((Project) p);
                            found = true;
                        }
                    }
                }
                if (flats.size() > 0) {
                    neighborhoods.put(f.getName(), flats);
                }
            }
        } else if (groupBy.equals(NO_GROUP_BY)) {
            neighborhoods.put("", projects);
        } else {
            throw new GroupByOperationNotSupportedException();
        }
        return neighborhoods;
    }

    private static Map<String, List<?>> groupSubprojectsBy(List<?> subprojects, String groupBy) throws GroupByOperationNotSupportedException {
        Map<String, List<?>> neighborhoods = new HashMap<String, List<?>>();
        if (groupBy.equals(GROUP_BY_COMPANY)) {
            List<Company> availableCompanies = CompanyManager.getAllCompanies();
            for (Company comp : availableCompanies) {
                List<Subproject> flats = new ArrayList<Subproject>();
                for (Object sp : subprojects) {
                    if (((Subproject) sp).getFactory().getCompany().getId() == comp.getId()) {
                        flats.add((Subproject) sp);
                    }
                }
                if (flats.size() > 0)
                    neighborhoods.put(comp.getName(), flats);
            }
        } else if (groupBy.equals(GROUP_BY_MARKET)) {
            List<Market> availableMarkets = MarketManager.getAllMarkets();
            for (Market m : availableMarkets) {
                List<Subproject> flats = new ArrayList<Subproject>();
                for (Object sp : subprojects) {
                    if (((Subproject) sp).getProject().getMarket().equals(m))
                        flats.add((Subproject) sp);
                }
                if (flats.size() > 0) {
                    neighborhoods.put(m.getName(), flats);
                }
            }
        } else if (groupBy.equals(GROUP_BY_FACTORY)) {
            List<Factory> availableFactories = FactoryManager.getAllFactories();
            for (Factory f : availableFactories) {
                List<Subproject> flats = new ArrayList<Subproject>();
                for (Object sp : subprojects) {
                    if (f.getId() == ((Subproject )sp).getFactory().getId()) {
                        flats.add((Subproject) sp);
                    }
                }
                if (flats.size() > 0) {
                    neighborhoods.put(f.getName(), flats);
                }
            }
        } else if (groupBy.equals(GROUP_BY_PROJECT)) {
            List<Project> availableProjects = ProjectManager.getAllProjects();
            for (Project p : availableProjects) {
                List<Subproject> flats = new ArrayList<Subproject>();
                for (Object sp : subprojects) {
                    if (p.getId() == ((Subproject) sp).getProject().getId()) {
                        flats.add((Subproject) sp);
                    }
                }
                if (flats.size() > 0) {
                    neighborhoods.put(p.getName(), flats);
                }
            }
        } else if (groupBy.equals(NO_GROUP_BY)) {
            neighborhoods.put("", subprojects);
        } else {
            throw new GroupByOperationNotSupportedException();
        }
        return neighborhoods;
    }

}
