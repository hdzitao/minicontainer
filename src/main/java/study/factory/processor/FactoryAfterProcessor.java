package study.factory.processor;

import study.factory.BeanFactory;
import study.factory.configure.BeanConfigure;

/**
 * Created by taojinhou on 2020/7/29.
 */
public interface FactoryAfterProcessor {
    void after(BeanFactory factory, BeanConfigure configure);
}
