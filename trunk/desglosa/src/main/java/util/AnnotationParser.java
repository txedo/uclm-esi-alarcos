package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * http://isagoksu.com/2009/development/java/creating-custom-annotations-and-making-use-of-them/
 */
public class AnnotationParser {
	public Map<String,String> parse(String clazz) throws Exception {
		return parse(Class.forName(clazz));
	}
	
	public Map<String,String> parse(Class clazz) throws Exception {
		Map<String, String> attributes = new HashMap<String, String>();
		List<Field> fields = new ArrayList<Field>();
		Class superClazz = clazz.getSuperclass();
		// add superclass declared fields and navigate until root class
		while (superClazz != null) {
			fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
			superClazz = superClazz.getSuperclass();
		}
		// add class declared fields
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		
		for (Field field : fields) {
			if (field.isAnnotationPresent(GLDimension.class)) {
				GLDimension dimension = field.getAnnotation(GLDimension.class);
				String name = dimension.name();
				if (name.equals("")) name = field.getName();
				String type = dimension.type();
				if (type.equals("")) type = field.getType().getName();
				attributes.put(name, type);
			}
		}
		
		return attributes;
	}
}
