package es.uclm.inf_cr.alarcos.desglosa_web.util;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class MyHashMapEntryType {
    @XmlAttribute(name = "label") // @XmlElement and @XmlValue are also fine
    private String key; 
    @XmlAttribute(name = "color") // @XmlElement and @XmlValue are also fine
    private String value;

    public MyHashMapEntryType() {}

    public MyHashMapEntryType(Map.Entry<String,String> e) {
       key = e.getKey();
       value = e.getValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
