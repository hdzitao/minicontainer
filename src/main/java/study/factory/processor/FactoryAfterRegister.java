package study.factory.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taojinhou on 2020/7/30.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FactoryAfterRegister {
    Class<? extends FactoryAfterProcessor> value();

    RegisterPriority priority() default RegisterPriority.LOW;
}
