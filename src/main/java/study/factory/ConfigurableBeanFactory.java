package study.factory;

/**
 * Created by taojinhou on 2020/7/29.
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void addBeanConfigure(BeanConfigure configure);
}
