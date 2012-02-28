package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.MeasureManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;

public class MeasureAction extends ActionSupport implements GenericActionInterface {
    private static final long serialVersionUID = -3573393418431314643L;
    private int id;
    private List<Measure> measures;
    private Measure measure;
    private final Map<String, String> entities = new HashMap<String, String>() {
        private static final long serialVersionUID = 25106226884419794L;
        {
            put(Measure.COMPANY_ENTITY, getText("label.Company"));
            put(Measure.FACTORY_ENTITY, getText("label.Factory"));
            put(Measure.PROJECT_ENTITY, getText("label.Project"));
            put(Measure.SUBPROJECT_ENTITY, getText("label.Subproject"));
            put(Measure.MARKET_ENTITY, getText("label.Market"));
        }
    };
    private final Map<String, String> types = new HashMap<String, String>() {
        private static final long serialVersionUID = 25106226884419794L;
        {
            put(Measure.FLOAT, getText("label.float"));
            put(Measure.INTEGER, getText("label.integer"));
            put(Measure.STRING, getText("label.string"));
            put(Measure.BOOLEAN, getText("label.boolean"));
        }
    };
    private final String[] reserverWords = {"size", "length", "delayed"};

    public int getId() {
        return id;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public Measure getMeasure() {
        return measure;
    }
    
    public Map<String, String> getEntities() {
        return entities;
    }
    
    public Map<String, String> getTypes() {
        return types;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public String execute() throws Exception {
        measures = MeasureManager.getAllMeasures();
        return SUCCESS;
    }
    
    public String showForm() throws MeasureNotFoundException {
        if (id > 0) {
            measure = MeasureManager.getMeasure(id);
        }
        return SUCCESS;
    }
    
    public void validateDoSave() {
        if (measure == null) {
            addActionError("error.general");
        } else {
            if (Utilities.isEmptyString(measure.getEntity())) {
                addFieldError("error.measure.entity", getText("error.measure.entity"));
            }
            if (Utilities.isEmptyString(measure.getName())) {
                addFieldError("error.measure.name", getText("error.measure.name"));
            }
            if (Utilities.isEmptyString(measure.getType())) {
                addFieldError("error.measure.type", getText("error.measure.type"));
            }
        }
    }

    public String save() throws Exception {
        MeasureManager.saveMeasure(measure);
        addActionMessage(getText("message.measure.added_successfully"));
        return SUCCESS;
    }

    public String edit() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void validateDoDelete() {
        try {
            Utilities.checkValidId(id);
        } catch (NotValidIdParameterException e) {
            addActionError("error.general");
        }
    }

    public String delete() throws Exception {
        MeasureManager.removeMeasure(id);
        addActionMessage(getText("message.measure.added_successfully"));
        return SUCCESS;
    }

    public String get() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
