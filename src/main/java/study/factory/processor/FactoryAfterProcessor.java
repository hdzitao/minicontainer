package study.factory.processor;

import study.factory.BeanConfigure;
import study.factory.BeanFactory;

/**
 * Created by taojinhou on 2020/7/29.
 */
public interface FactoryAfterProcessor {
    void after(BeanFactory factory, BeanConfigure configure);
}
