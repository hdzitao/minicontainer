package study.factory.configure;

import study.factory.BeanFactory;

/**
 * Created by taojinhou on 2020/7/29.
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void addBeanConfigure(BeanConfigure configure);
}
