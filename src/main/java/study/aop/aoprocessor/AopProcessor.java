package study.aop.aoprocessor;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.InvocationHandler;
import study.aop.CutPoint;
import study.aop.parser.MethodParser;
import study.aop.parser.ParserFactory;
import study.aop.parser.PathParser;
import study.factory.processor.BeanAfterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 所有aop处理器的父类
 */
public abstract class AopProcessor {
    protected final PathParser pathParser;
    protected final MethodParser methodParser;

    @SneakyThrows
    public AopProcessor(Annotation annotation) {
        this.methodParser = ParserFactory.getMethodParser((String) annotation.annotationType().getMethod("method")
                .invoke(annotation));
        this.pathParser = ParserFactory.getPathParser((String) annotation.annotationType().getMethod("path")
                .invoke(annotation), this.methodParser);
    }

    /**
     * 生成aop bean后置处理器
     *
     * @param aopClass
     * @param aopMethod
     * @return
     */
    public BeanAfterProcessor getProcessor(Class<?> aopClass, Method aopMethod) {
        aopMethod.setAccessible(true);
        return (factory, configure, bean) -> {
            // 判断class是否满足条件
            if (pathParser.parse(configure)) {
                // 类中有命中aop的方法,利用CGLib代理方法
                InvocationHandler handler = (proxy, method, args) -> {
                    method.setAccessible(true);
                    if (methodParser.parse(method)) {
                        // 命中方法走aop
                        CutPoint cutPoint = new CutPoint(configure.getBeanClass(), method, args, null, bean);
                        return invokeAop(factory.getBean(aopClass), aopMethod, cutPoint);
                    } else {
                        // 未命中方法调用原方法
                        return method.invoke(bean, args);
                    }
                };

                return bean instanceof Factory
                        ? ((Factory) bean).newInstance(handler)
                        : Enhancer.create(configure.getBeanClass(), handler);
            }

            return bean;
        };
    }

    protected abstract Object invokeAop(Object aop, Method aopMethod, CutPoint point);
}
