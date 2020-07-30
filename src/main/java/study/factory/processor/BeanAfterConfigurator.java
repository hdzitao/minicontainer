package study.factory.processor;

/**
 * Created by taojinhou on 2020/7/29.
 */
public interface BeanAfterConfigurator {
    void addBeanAfterProcessor(BeanAfterProcessor processor);
}
