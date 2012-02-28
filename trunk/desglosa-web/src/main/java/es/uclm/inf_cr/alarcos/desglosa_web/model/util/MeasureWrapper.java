package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

public class MeasureWrapper {
    private String name;
    private String type;
    private boolean annotated;
    private boolean base;
    private String description;
    
    public MeasureWrapper(String name, String type, boolean base, boolean annotated, String description) {
        this.name = name;
        this.type = type;
        this.annotated = annotated;
        this.base = base;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getAnnotated() {
        return annotated;
    }

    public boolean isBase() {
        return base;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAnnotated(boolean annotated) {
        this.annotated = annotated;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
