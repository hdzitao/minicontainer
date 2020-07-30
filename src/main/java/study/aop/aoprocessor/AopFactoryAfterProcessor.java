package study.aop.aoprocessor;

import study.factory.BeanConfigure;
import study.factory.BeanFactory;
import study.factory.auto.MiniComponent;
import study.factory.processor.BeanAfterConfigurator;
import study.factory.processor.BeanAfterProcessor;
import study.factory.processor.FactoryAfterProcessor;

import java.lang.reflect.Method;

/**
 * Aop BeanFactory后置处理，用于注册aop bean处理器
 */
@MiniComponent
public class AopFactoryAfterProcessor implements FactoryAfterProcessor {

    @Override
    public void after(BeanFactory factory, BeanConfigure configure) {
        if (factory instanceof BeanAfterConfigurator) {
            BeanAfterConfigurator configurator = (BeanAfterConfigurator) factory;
            Class<?> aopBeanClass = configure.getBeanClass();
            for (Method method : aopBeanClass.getDeclaredMethods()) {
                AopProcessor processor = AopProcessorFactory.getProcessor(method);
                if (processor != null) {
                    BeanAfterProcessor afterProcessor = processor.getProcessor(aopBeanClass, method);
                    configurator.addBeanAfterProcessor(afterProcessor);
                }
            }
        }
    }
}
