package study.factory.auto;

import study.factory.configure.BeanConfigure;
import study.factory.configure.BeanConfigureReader;

import java.lang.reflect.Constructor;

/**
 * Created by taojinhou on 2020/8/4.
 */
public class MiniConfigureReader implements BeanConfigureReader<Class<?>> {
    @Override
    public BeanConfigure read(Class<?> clazz) {
        MiniComponent component = clazz.getAnnotation(MiniComponent.class);
        if (component != null) {
            BeanConfigure configure = BeanConfigure.forClass(clazz);
            configure.setSingleton(component.singleton());
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    configure.setConstructor(constructor);
                    break;
                }
            }

            return configure;
        } else {
            return null;
        }
    }
}
