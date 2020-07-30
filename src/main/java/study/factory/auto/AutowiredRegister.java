package study.factory.auto;

import study.factory.processor.BeanAfterAdderProcessor;
import study.factory.processor.FactoryAfterRegister;
import study.factory.processor.RegisterPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taojinhou on 2020/7/30.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FactoryAfterRegister(value = BeanAfterAdderProcessor.class, priority = RegisterPriority.SYSTEM)
public @interface AutowiredRegister {
}
