package study.aop;

import study.aop.aoprocessor.AopFactoryAfterProcessor;
import study.factory.processor.FactoryAfterRegister;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FactoryAfterRegister(value = AopFactoryAfterProcessor.class)
public @interface Aop {
}
