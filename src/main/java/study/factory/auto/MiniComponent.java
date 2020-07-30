package study.factory.auto;

import java.lang.annotation.*;

/**
 * Created by taojinhou on 2019/4/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MiniComponent {
    boolean singleton() default true;

//    String value() default ""; // 名字
}
