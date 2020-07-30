package study.factory.auto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taojinhou on 2019/4/16.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    Class<?> AUTOWIRED_DEFAULT_CLASS = Object.class;

    Class<?> value() default Object.class;
}
