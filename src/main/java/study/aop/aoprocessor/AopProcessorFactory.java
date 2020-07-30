package study.aop.aoprocessor;

import study.aop.After;
import study.aop.Around;
import study.aop.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * aop处理器工厂
 */
public class AopProcessorFactory {
    public static AopProcessor getProcessor(Method method) {
        Annotation aop;
        if ((aop = method.getAnnotation(Before.class)) != null) {
            return new AopBeforeProcessor((Before) aop);
        } else if ((aop = method.getAnnotation(Around.class)) != null) {
            return new AopAroundProcessor((Around) aop);
        } else if ((aop = method.getAnnotation(After.class)) != null) {
            return new AopAfterProcessor((After) aop);
        } else {
            return null;
        }
    }
}
