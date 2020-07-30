package study.factory.processor;

import study.factory.BeanConfigure;
import study.factory.BeanFactory;
import study.factory.auto.MiniComponent;

/**
 * bean后置处理器添加器
 * <p>
 * Created by taojinhou on 2020/7/29.
 */
@MiniComponent
public class BeanAfterAdderProcessor implements FactoryAfterProcessor {
    @Override
    public void after(BeanFactory factory, BeanConfigure configure) {
        if (factory instanceof BeanAfterConfigurator) {
            ((BeanAfterConfigurator) factory).addBeanAfterProcessor(
                    (BeanAfterProcessor) factory.getBean(configure.getBeanClass()));
        }
    }
}
