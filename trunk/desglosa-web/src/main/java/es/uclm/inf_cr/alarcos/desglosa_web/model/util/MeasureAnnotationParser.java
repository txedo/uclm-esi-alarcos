package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MeasureAnnotationParser {
        
        private MeasureAnnotationParser() {
        }
        
        public static final List<MeasureWrapper> parseBaseMeasures(final String clazz) throws SecurityException, ClassNotFoundException {
            return MeasureAnnotationParser.parseBaseMeasures(Class.forName(clazz));
        }
        
        public static final List<MeasureWrapper> parseBaseMeasures(final Class<?> clazz) throws SecurityException {
            return MeasureAnnotationParser.parse(clazz, true);
        }
        
        public static final List<MeasureWrapper> parseAllMeasures(final String clazz) throws SecurityException, ClassNotFoundException {
            return MeasureAnnotationParser.parseAllMeasures(Class.forName(clazz));
        }
        
        public static final List<MeasureWrapper> parseAllMeasures(final Class<?> clazz) throws SecurityException {
            return MeasureAnnotationParser.parse(clazz, false);
        }

        private static final List<MeasureWrapper> parse(final Class<?> clazz, boolean onlyBaseMeasures) throws SecurityException {
            List<MeasureWrapper> measures = new ArrayList<MeasureWrapper>();
            // Get all methods from the class
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            for (Field field : fields) {
                // Select those methods that has JPA @Column annotation present
                if (field.isAnnotationPresent(Measure.class)) {
                    // Read class attribute name and type, not column data
                    Measure measure = field.getAnnotation(Measure.class);
                    if ((measure.base() && onlyBaseMeasures) || !onlyBaseMeasures) {
                        measures.add(new MeasureWrapper(field.getName(), field.getType().getSimpleName(), measure.base(), true, measure.description()));
                    }
                }
            }
            return measures;
        }

}
