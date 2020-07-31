package study.factory.configure;

import lombok.Getter;
import study.factory.auto.Autowired;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * Created by taojinhou on 2019/4/16.
 */
public class BeanConfigure {
    @Getter
    private Object bean;

    @Getter
    private boolean singleton;

    @Getter
    private Object singletonLock;

    @Getter
    private Class<?> beanClass;

    @Getter
    private Constructor<?> constructor;

    public static BeanConfigure forClass(Class<?> baseClass, boolean singleton) {
        BeanConfigure configure = new BeanConfigure();
        configure.singleton = singleton;
        configure.beanClass = baseClass;
        for (Constructor<?> constructor : baseClass.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                configure.constructor = constructor;
                break;
            }
        }

        configure.singletonLock = configure;

        return configure;
    }

    public static BeanConfigure forBean(Class<?> beanClass, Object bean) {
        BeanConfigure configure = new BeanConfigure();
        configure.bean = bean;
        configure.beanClass = beanClass;

        configure.singleton = true;
        configure.singletonLock = configure;

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
