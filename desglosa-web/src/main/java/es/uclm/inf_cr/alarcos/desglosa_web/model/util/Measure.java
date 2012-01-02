package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Measure {

    boolean base() default true;
    
    String description() default "";

}