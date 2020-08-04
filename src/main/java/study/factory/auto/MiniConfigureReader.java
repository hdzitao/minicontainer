package study.factory.auto;

import study.factory.configure.BeanConfigure;
import study.factory.configure.BeanConfigureReader;

/**
 * Created by taojinhou on 2020/8/4.
 */
public class MiniConfigureReader implements BeanConfigureReader<Class<?>> {
    @Override
    public BeanConfigure read(Class<?> clazz) {
        MiniComponent component = clazz.getAnnotation(MiniComponent.class);
        return component != null ? BeanConfigure.forClass(clazz, component.singleton()) : null;
    }
}
