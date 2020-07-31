package study.factory.auto;

import study.factory.processor.BeanAfterAdderProcessor;
import study.factory.processor.FactoryAfterPriority;
import study.factory.processor.FactoryAfterRegister;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taojinhou on 2020/7/30.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FactoryAfterRegister(value = BeanAfterAdderProcessor.class, priority = FactoryAfterPriority.SYSTEM)
public @interface AutowiredRegister {
}
