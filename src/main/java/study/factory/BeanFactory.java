package study.factory;

/**
 * 根据class获取bean
 * Created by taojinhou on 2020/3/16.
 */
public interface BeanFactory {
    <T> T getBean(Class<T> requiredClass) throws BeanCreatingException;
}
