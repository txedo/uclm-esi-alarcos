package presentation;

import java.util.Iterator;
import java.util.Map;

import model.gl.knowledge.GLAntennaBall;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLTower;
import util.AnnotationParser;

public class TestAnnotations {
    public static void main(String[] args) {
        processClass(GLTower.class);
        processClass(GLFactory.class);
        processClass(GLAntennaBall.class);
    }

    public static void processClass(String clazz) {
        try {
            System.out.println("Processing class " + clazz);
            Map<String, String> attr = AnnotationParser.parse(clazz);
            Iterator it = attr.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
                        .next();
                System.out.println(pairs.getKey() + " - " + pairs.getValue());
            }
            System.out.println();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void processClass(Class clazz) {
        try {
            System.out.println("Processing class " + clazz.getName());
            Map<String, String> attr = AnnotationParser.parse(clazz);
            Iterator it = attr.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
                        .next();
                System.out.println(pairs.getKey() + " - " + pairs.getValue());
            }
            System.out.println();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
