package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;

/*
 * http://isagoksu.com/2009/development/java/creating-custom-annotations-and-making-use-of-them/
 */
public class PropertyAnnotationParser {
	public List<PropertyWrapper> parse(String clazz) throws Exception {
		return parse(Class.forName(clazz));
	}
	
	public List<PropertyWrapper> parse(Class clazz) throws Exception {
		List<PropertyWrapper> properties = new ArrayList<PropertyWrapper>();
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		
		for (Field field : fields) {
			if (field.isAnnotationPresent(Property.class)) {
				Property property = field.getAnnotation(Property.class);
				String name = property.name();
				if (name.equals("")) name = field.getName();
				String type = property.type();
				if (type.equals("")) type = field.getType().getName();
				String description = property.description();
				// add it
				properties.add(new PropertyWrapper(name, type, description));
			}
		}
		
		return properties;
	}
}
