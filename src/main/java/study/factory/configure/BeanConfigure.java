package study.factory.configure;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Created by taojinhou on 2019/4/16.
 */
public class BeanConfigure {
    @Getter
    private Class<?> beanClass;

    @Getter
    @Setter
    private Object bean;

    @Getter
    @Setter
    private boolean singleton;

    @Getter
    @Setter
    private BeanConstructor beanConstructor;

    public static BeanConfigure forClass(Class<?> baseClass) {
        BeanConfigure configure = new BeanConfigure();
        configure.beanClass = baseClass;

        return configure;
    }

    public static BeanConfigure forBean(Class<?> beanClass, Object bean) {
        BeanConfigure configure = new BeanConfigure();
        configure.bean = bean;
        configure.beanClass = beanClass;
        configure.singleton = true;

        return configure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanConfigure that = (BeanConfigure) o;
        return beanClass.equals(that.beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass);
    }
}
