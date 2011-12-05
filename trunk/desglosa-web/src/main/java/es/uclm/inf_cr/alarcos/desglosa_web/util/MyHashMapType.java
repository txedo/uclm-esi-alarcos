package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MyHashMapType {
    private List<MyHashMapEntryType> entry = new ArrayList<MyHashMapEntryType>();

    public MyHashMapType(Map<String, String> map) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            entry.add(new MyHashMapEntryType(e));
        }
    }

    public MyHashMapType() {
    }

    public List<MyHashMapEntryType> getEntry() {
        return entry;
    }

    public void setEntry(List<MyHashMapEntryType> entry) {
        this.entry = entry;
    }

}
