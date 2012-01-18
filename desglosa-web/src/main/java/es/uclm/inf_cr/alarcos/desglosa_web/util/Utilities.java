package es.uclm.inf_cr.alarcos.desglosa_web.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;
import org.springframework.web.context.ContextLoader;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureAnnotationParser;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.MeasureWrapper;

public class Utilities {
    public static final String ID = "id";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private Utilities() {
    }

    /**
     * Validate hex with regular expression
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean validateEmail(final String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean isEmptyString(String field) {
        boolean bEmpty = false;
        if (field == null) {
            bEmpty = true;
        } else {
            if ((field = field.trim()).length() == 0) {
                bEmpty = true;
            }
        }
        return bEmpty;
    }
    
    public static void checkValidId(int id) throws NotValidIdParameterException {
        // Id must be greater than 0
        if (id <= 0) {
            throw new NotValidIdParameterException();
        }
    }
    
    public static Integer checkValidId(String sId) throws NullIdParameterException, NotValidIdParameterException {
        Integer nId = null;
        
        // Check if id is null
        if (sId == null) {
            throw new NullIdParameterException();
        } else {
            try {
                nId = Integer.parseInt(sId);
                Utilities.checkValidId(nId);
            } catch (NumberFormatException nfe) {
                throw new NotValidIdParameterException();
            }
        }
        
        return nId;
    }
    
    public static int getJsonArraySize(String jsonString) {
        return ((JSONArray) JSONSerializer.toJSON(jsonString)).size();
    }
    
    public static String getRealPathToWebApplicationContext(String dir) {
        String path = null;
        if (ContextLoader.getCurrentWebApplicationContext() != null) {
            path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(dir);
        } else {
            // This line is just for testing purposes because there is no current web application context, so it should be deleted in production
            path = System.getProperty("user.dir") + "\\src\\main\\webapp\\" + dir;
        }
        
        return path;
    }
    
    public static Class<?> getParameterType(String simpleTypeName) {
        Class<?> parameterType = null;
        if (simpleTypeName.equals("Integer")) {
            parameterType = Integer.class;
        } else if (simpleTypeName.equals("Float")) {
            parameterType = Float.class;
        } else if (simpleTypeName.equals("String")) {
            parameterType = String.class;
        } else if (simpleTypeName.equals("Boolean")) {
            parameterType = Boolean.class;
        }

        return parameterType;
    }
    
    public static void updateFieldsByReflection(Class<?> clazz, Object objectToUpdate, Object objectWithUpdatedFields) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, Exception {
        List<MeasureWrapper> measures = MeasureAnnotationParser.parseBaseMeasures(clazz);
        for (MeasureWrapper pw : measures) {
            String prefix = "get";
            if (pw.getType().equals("Boolean")) {
                prefix = "is";
            }
            String getterName = prefix + WordUtils.capitalize(pw.getName());
            Method getterMethod = clazz.getMethod(getterName, null);
            Object measureValue = getterMethod.invoke(objectWithUpdatedFields, null);
            String setterName = "set" + WordUtils.capitalize(pw.getName());
            Method setterMethod = clazz.getMethod(setterName, Utilities.getParameterType(pw.getType()));
            setterMethod.invoke(clazz.cast(objectToUpdate), measureValue);
        }
    }
}
