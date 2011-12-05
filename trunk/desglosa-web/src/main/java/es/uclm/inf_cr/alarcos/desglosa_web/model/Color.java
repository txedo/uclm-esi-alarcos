package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Color {
    private String acceptable;
    private String peripheral;
    private String nonAcceptable;

    public Color() {
    }

    public String getAcceptable() {
        return acceptable;
    }

    public String getPeripheral() {
        return peripheral;
    }

    public String getNonAcceptable() {
        return nonAcceptable;
    }

    public void setAcceptable(String acceptable) {
        this.acceptable = acceptable;
    }

    public void setPeripheral(String peripheral) {
        this.peripheral = peripheral;
    }

    public void setNonAcceptable(String nonAcceptable) {
        this.nonAcceptable = nonAcceptable;
    }

}
