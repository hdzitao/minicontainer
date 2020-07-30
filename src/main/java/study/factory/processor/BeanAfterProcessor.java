package study.factory.processor;


import study.factory.BeanConfigure;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;

/**
 * Created by taojinhou on 2019/4/16.
 */
public interface BeanAfterProcessor {
    Object after(BeanFactory factory, BeanConfigure beanConfigure, Object bean) throws BeanCreatingException;
}
