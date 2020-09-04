package study.factory.auto;

import lombok.SneakyThrows;
import study.factory.BeanFactory;
import study.factory.configure.BeanConstructor;

import java.lang.reflect.Constructor;

public class MiniBeanConstructor implements BeanConstructor {
    private final Constructor<?> constructor;

    public MiniBeanConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }


    @Override
    @SneakyThrows
    public Object getBeanByConstructor(BeanFactory factory) {
        Object[] parameterBeans = InjectInfo.getParameterBeans(this.constructor, factory);
        return this.constructor.newInstance(parameterBeans);
    }
}
