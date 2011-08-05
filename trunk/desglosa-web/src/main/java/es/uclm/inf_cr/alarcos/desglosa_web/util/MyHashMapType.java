package es.uclm.inf_cr.alarcos.desglosa_web.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyHashMapType {
    public List<MyHashMapEntryType> entry = new ArrayList<MyHashMapEntryType>();
    
    public MyHashMapType(Map<String,String> map) {
        for( Map.Entry<String,String> e : map.entrySet() )
            entry.add(new MyHashMapEntryType(e));
    }

	public MyHashMapType() {}
}
