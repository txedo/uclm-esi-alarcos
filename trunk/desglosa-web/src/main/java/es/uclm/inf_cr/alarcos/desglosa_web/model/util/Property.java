package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
	public String name() default "";
	public String type() default "";
	public String description() default "No description has been set.";
	public boolean embedded() default false;
}