package study.factory.processor;


import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.configure.BeanConfigure;

/**
 * Created by taojinhou on 2019/4/16.
 */
public interface BeanAfterProcessor {
    Object after(BeanFactory factory, BeanConfigure beanConfigure, Object bean) throws BeanCreatingException;
}
