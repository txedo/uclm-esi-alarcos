package es.uclm.inf_cr.alarcos.desglosa_web.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping {
    @XmlElement
    private Field entityAttr;
    @XmlElement
    private Field modelAttr;
    @XmlElement
    private Object value;
    @XmlElement
    private Object ratio;
    @XmlElement(name = "rule")
    private List<Rule> rules = new ArrayList<Rule>();
    
    public Mapping () {}
    
    public Mapping(Field entityAttr, Field modelAttr, Object value, Object ratio, List<Rule> rules) {
        this.entityAttr = entityAttr;
        this.modelAttr = modelAttr;
        this.value = value;
        this.ratio = ratio;
        this.rules = rules;
    }

    public Field getEntityAttr() {
        return entityAttr;
    }

    public Field getModelAttr() {
        return modelAttr;
    }

    public Object getValue() {
        return value;
    }

    public Object getRatio() {
        return ratio;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setEntityAttr(Field entityAttr) {
        this.entityAttr = entityAttr;
    }

    public void setModelAttr(Field modelAttr) {
        this.modelAttr = modelAttr;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setRatio(Object ratio) {
        this.ratio = ratio;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

}
